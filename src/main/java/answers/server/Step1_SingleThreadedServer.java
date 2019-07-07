package answers.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class Step1_SingleThreadedServer {

    final int portNumber = 81;

    public static void main(String[] args) throws IOException {
        new Step1_SingleThreadedServer().start();
    }

    void start() throws IOException {
        System.out.println("Creating server socket on port " + portNumber);
        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("Created server socket on port " + portNumber);

        while (true) {
            try (final var client = serverSocket.accept();
                 final var writer = new PrintWriter(client.getOutputStream(), true);
                 final var reader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                System.out.println("Accepted connection from " + client);

                writer.println("What's you name?");

                var name = reader.readLine();
                writer.println("Hello, " + name);

                System.out.println("Just said hello to:" + name);
            } catch (IOException exception) {
                // workshops
            }
        }
    }
}
