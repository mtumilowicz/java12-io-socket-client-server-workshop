package server;

import java.io.*;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class TestServerSocket extends Server {

    public static void main(String[] args) throws IOException {
        new TestServerSocket().start();
    }

    @Override
    void handle(Socket client) {
        ClientConnection.run(client);
    }
}
