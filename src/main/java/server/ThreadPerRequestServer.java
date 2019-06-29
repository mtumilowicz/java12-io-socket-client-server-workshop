package server;

import java.io.IOException;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class ThreadPerRequestServer extends Server {

    public static void main(String[] args) throws IOException {
        new ThreadPerRequestServer().start();
    }
    
    @Override
    void handle(ClientConnection clientConnection) {
        new Thread(clientConnection).start();
    }
}
