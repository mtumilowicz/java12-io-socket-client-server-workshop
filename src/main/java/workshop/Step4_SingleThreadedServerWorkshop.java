package workshop;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
class Step4_SingleThreadedServerWorkshop {

    private final int portNumber;

    Step4_SingleThreadedServerWorkshop(int portNumber) {
        this.portNumber = portNumber;
    }

    private Step4_SingleThreadedServerWorkshop() {
        this.portNumber = 81;
    }

    public static void main(String[] args) throws IOException {
        new Step4_SingleThreadedServerWorkshop().start();
    }

    void start() throws IOException {
        log("Creating server socket on port " + portNumber);
        var serverSocket = new ServerSocket(portNumber);
        log("Created server socket on port " + portNumber);

        while (true) {
            final var client = serverSocket.accept();
            log("Accepted connection from " + client);

            // use Step3_ClientConnectionWorkshop
        }
    }

    private void log(String message) {
        System.out.println(message);
    }
}
