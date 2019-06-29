package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-06-29.
 */
abstract class Server {
    final int portNumber = 81;

    void start() throws IOException {
        System.out.println("Creating server socket on port " + portNumber);
        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("Created server socket on port " + portNumber);

        while (true) {
            try(final Socket client = serverSocket.accept()) {
                System.out.println("Accepted connection from " + client);
                handle(new ClientConnection(client));
            }
        }
    }

    abstract void handle(ClientConnection client);
}