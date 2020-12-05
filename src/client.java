import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class client {

    public static int port = 55555;
    public static String host = "localhost";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(host, port);
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        int choice;
        do {
            choice = menu();

            Scanner scanner = new Scanner(System.in);

            System.out.println("content: ");
            String content = scanner.nextLine();

            dataOutputStream.writeUTF(choice + "\n" + content);

            String response = dataInputStream.readUTF();

            System.out.println("result: " + response);

        } while (choice != 0);
    }

    public static int menu() {
        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("0. exit");
            System.out.println("1. convert string");
            System.out.println("2. calculator");
            choice = scanner.nextInt();
        } while (choice < 0 || choice > 2);

        return choice;
    }
}
