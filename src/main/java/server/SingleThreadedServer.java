package server;

import java.io.IOException;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class SingleThreadedServer extends Server {

    public static void main(String[] args) throws IOException {
        new SingleThreadedServer().start();
    }

    @Override
    void handle(Runnable clientConnection) {
        clientConnection.run();
    }
}
