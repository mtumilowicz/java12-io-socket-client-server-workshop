import java.io.*;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class TestServerThreadPoolSocket extends Server {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            100,
            100,
            0L,
            MILLISECONDS,
            new ArrayBlockingQueue<>(1000),
            (r, ex) -> {
            });

    public static void main(String[] args) throws IOException {
        new TestServerThreadPoolSocket().start();
    }

    @Override
    void handle(Socket client) {
        executor.execute(() -> ClientConnection.run(client));
    }

}