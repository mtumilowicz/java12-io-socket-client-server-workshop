import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by mtumilowicz on 2019-06-28.
 */
public class ClientConnection {

    Socket client;

    public ClientConnection(Socket client) {
        this.client = client;
    }

    public void run() throws IOException {
        String hostname = "localhost";
        int port = 81;
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        System.out.println(br.readLine());

        OutputStream os = client.getOutputStream();
        PrintWriter pw = new PrintWriter(os, true);
        System.out.println("Type name...");
        pw.println(new Scanner(System.in).nextLine());

        System.out.println(br.readLine());
    }
}
