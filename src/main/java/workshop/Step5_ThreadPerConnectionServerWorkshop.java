package workshop;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
class Step5_ThreadPerConnectionServerWorkshop {

    private final int portNumber;

    Step5_ThreadPerConnectionServerWorkshop(int portNumber) {
        this.portNumber = portNumber;
    }

    private Step5_ThreadPerConnectionServerWorkshop() {
        this.portNumber = 81;
    }

    public static void main(String[] args) throws IOException {
        new Step5_ThreadPerConnectionServerWorkshop().start();
    }

    void start() throws IOException {
        log("Creating server socket on port " + portNumber);
        var serverSocket = new ServerSocket(portNumber);
        log("Created server socket on port " + portNumber);

        while (true) {
            final var client = serverSocket.accept();
            log("Accepted connection from " + client);

            // use Step3_ClientConnectionWorkshop, hint: new Thread(...).start()
        }

    }

    private void log(String message) {
        System.out.println(message);
    }
}
