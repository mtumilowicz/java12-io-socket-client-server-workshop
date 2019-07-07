package workshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class Step1_SingleThreadedServerWorkshop {

    final int portNumber = 81;

    public static void main(String[] args) throws IOException {
        new Step1_SingleThreadedServerWorkshop().start();
    }

    void start() throws IOException {
        log("Creating server socket on port " + portNumber);
        var serverSocket = new ServerSocket(portNumber);
        log("Created server socket on port " + portNumber);

        while (true) {
            try (final var client = serverSocket.accept()) {
                log("Accepted connection from " + client);

                try (final var writer = new PrintWriter(client.getOutputStream(), true);
                     final var reader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                    writer.println("What's you name?");

                    var name = reader.readLine();
                    writer.println("Hello, " + name);

                    log("Just said hello to:" + name);
                } catch (IOException exception) {
                    // workshops
                }
            } catch (IOException exception) {
                // workshops
            }
        }
    }

    private void log(String message) {
        System.out.println(message);
    }
}
