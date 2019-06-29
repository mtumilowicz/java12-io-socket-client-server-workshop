package server;

import java.io.*;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class SingleThreadedServer extends Server {

    public static void main(String[] args) throws IOException {
        new SingleThreadedServer().start();
    }

    @Override
    void handle(Socket client) {
        new ClientConnection(client).run();
    }
}
