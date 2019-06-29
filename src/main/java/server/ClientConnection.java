package server;

import java.io.*;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-06-29.
 */
public class ClientConnection {
    static void run(Socket client) {
        try(client) {
            OutputStream os = client.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("What's you name?");

            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String str = br.readLine();

            pw.println("Hello, " + str);
            pw.close();
            client.close();

            System.out.println("Just said hello to:" + str);
        } catch (IOException ex) {
            // workshops
        }
    }
}