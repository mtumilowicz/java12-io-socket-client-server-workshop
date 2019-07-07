package client;

import java.io.*;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-06-28.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        new Client().run();
    }

    public void run() throws IOException {
        try (var client = new Socket("localhost", 81)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println(br.readLine());

            OutputStream os = client.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            System.out.println("Type name...");
            pw.println("XXX");

            System.out.println(br.readLine());
        }
    }
}
