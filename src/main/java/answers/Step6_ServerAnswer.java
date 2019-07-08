package answers;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by mtumilowicz on 2019-06-29.
 */
abstract class Step6_ServerAnswer {

    private final int portNumber;

    Step6_ServerAnswer(int portNumber) {
        this.portNumber = portNumber;
    }

    Step6_ServerAnswer() {
        this.portNumber = 81;
    }

    void start() throws IOException {
        log("Creating server socket on port " + portNumber);
        var serverSocket = new ServerSocket(portNumber);
        log("Created server socket on port " + portNumber);

        while (!Thread.currentThread().isInterrupted()) {
            final var client = serverSocket.accept();
            log("Accepted connection from " + client);
            
            handle(new Step3_ClientConnectionAnswer(client));
        }
    }

    private void log(String message) {
        System.out.println(message);
    }

    abstract void handle(Runnable client);
}