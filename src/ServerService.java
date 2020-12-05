import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.*;
import java.io.*;

public class ServerService {

    ServerSocket server;
    Socket socket = null;
    int port;
    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;

    public ServerService(int port) throws IOException {
        this.port = port;
        this.server = new ServerSocket(this.port);
        System.out.println("server listening in port: " + port);
    }

    public void estabilishConnection() throws IOException {
        this.socket = this.server.accept();
        System.out.println("client connected");
        this.dataInputStream = new DataInputStream(this.socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
    }

    @SuppressWarnings("deprecation")
    public String calculator(String expr) throws IOException {
        return EntityUtils.toString(((new DefaultHttpClient())
                .execute( new HttpGet("http://api.mathjs.org/v4/?expr=" + URLEncoder.encode(expr))))
                .getEntity(), "UTF-8");
    }

    public static void main(String... args) throws IOException {

        ServerService serverService = new ServerService(55555);
        serverService.estabilishConnection();

        while (true) {
            String request = serverService.dataInputStream.readUTF();
            String[] lines = request.split("\\n+");
            if (lines[0].equals("1")) {
                serverService.dataOutputStream.writeUTF(lines[1].toUpperCase());
            } else if (lines[0].equals("2")) {
                serverService.dataOutputStream.writeUTF(serverService.calculator(lines[1]));
            } else {
                serverService.dataOutputStream.writeUTF("bye bye!!");
                serverService.socket.close();
                break;
            }
        }
    }
}
