package server;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class ThreadPoolServer extends Server {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            100,
            100,
            0L,
            MILLISECONDS,
            new ArrayBlockingQueue<>(1000),
            (r, ex) -> {
            }
    );

    public static void main(String[] args) throws IOException {
        new ThreadPoolServer().start();
    }

    @Override
    void handle(ClientConnection clientConnection) {
        executor.execute(clientConnection);
    }

}