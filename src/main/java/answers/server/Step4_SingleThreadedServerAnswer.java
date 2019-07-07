package answers.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class Step4_SingleThreadedServerAnswer {

    final int portNumber = 81;

    public static void main(String[] args) throws IOException {
        new Step4_SingleThreadedServerAnswer().start();
    }

    void start() throws IOException {
        log("Creating server socket on port " + portNumber);
        var serverSocket = new ServerSocket(portNumber);
        log("Created server socket on port " + portNumber);

        while (true) {
            try (final var client = serverSocket.accept()) {
                log("Accepted connection from " + client);

                new Step3_ClientConnectionAnswer(client);
            } catch (IOException exception) {
                // workshops
            }
        }
    }

    private void log(String message) {
        System.out.println(message);
    }
}
