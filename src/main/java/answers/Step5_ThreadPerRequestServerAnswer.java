package answers;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
class Step5_ThreadPerRequestServerAnswer {

    private final int portNumber;

    Step5_ThreadPerRequestServerAnswer(int portNumber) {
        this.portNumber = portNumber;
    }

    private Step5_ThreadPerRequestServerAnswer() {
        this.portNumber = 81;
    }

    public static void main(String[] args) throws IOException {
        new Step5_ThreadPerRequestServerAnswer().start();
    }

    void start() throws IOException {
        log("Creating server socket on port " + portNumber);
        var serverSocket = new ServerSocket(portNumber);
        log("Created server socket on port " + portNumber);

        while (!Thread.currentThread().isInterrupted()) {
            final var client = serverSocket.accept();
            log("Accepted connection from " + client);
            
            new Thread(new Step3_ClientConnectionAnswer(client)).start();
        }

    }

    private void log(String message) {
        System.out.println(message);
    }
}
