package answers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
class Step2_ThreadPerRequestServerAnswer {

    private final int portNumber;

    Step2_ThreadPerRequestServerAnswer(int portNumber) {
        this.portNumber = portNumber;
    }

    Step2_ThreadPerRequestServerAnswer() {
        this.portNumber = 81;
    }

    public static void main(String[] args) throws IOException {
        new Step2_ThreadPerRequestServerAnswer().start();
    }

    void start() throws IOException {
        log("Creating server socket on port " + portNumber);
        var serverSocket = new ServerSocket(portNumber);
        log("Created server socket on port " + portNumber);

        while (!Thread.currentThread().isInterrupted()) {
            final var client = serverSocket.accept();
            log("Accepted connection from " + client);
            new Thread(() -> {
                try (client) {
                    final var writer = new PrintWriter(client.getOutputStream(), true);
                    final var reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    writer.println("What's you name?");

                    var name = reader.readLine();
                    writer.println("Hello, " + name);

                    log("Just said hello to:" + name);
                } catch (Exception ex) {
                    log(ex.getMessage());
                }
            }).start();
        }

    }

    private void log(String message) {
        System.out.println(message);
    }
}
