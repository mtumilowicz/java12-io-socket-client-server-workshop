package answers.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by mtumilowicz on 2019-06-29.
 */
abstract class Step6_ServerAnswer {
    private final int portNumber = 81;

    void start() throws IOException {
        log("Creating server socket on port " + portNumber);
        var serverSocket = new ServerSocket(portNumber);
        log("Created server socket on port " + portNumber);

        while (true) {
            try(final var client = serverSocket.accept()) {
                log("Accepted connection from " + client);
                handle(new Step3_ClientConnectionAnswer(client));
            }
        }
    }
    
    private void log(String message) {
        System.out.println(message);
    }

    abstract void handle(Runnable client);
}