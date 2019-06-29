package server;

import java.io.*;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class TestServerThreadPRequestSocket extends Server {

    public static void main(String[] args) throws IOException {
        new TestServerThreadPRequestSocket().start();
    }
    
    @Override
    void handle(Socket client) {
        new Thread(() -> ClientConnection.run(client)).start();
    }
}
