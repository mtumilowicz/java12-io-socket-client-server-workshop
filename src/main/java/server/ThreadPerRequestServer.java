package server;

import java.io.*;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class ThreadPerRequestServer extends Server {

    public static void main(String[] args) throws IOException {
        new ThreadPerRequestServer().start();
    }
    
    @Override
    void handle(Socket client) {
        new Thread(() -> new ClientConnection(client).run()).start();
    }
}
