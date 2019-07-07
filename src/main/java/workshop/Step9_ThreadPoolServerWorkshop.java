package workshop;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
class Step9_ThreadPoolServerWorkshop extends Step6_ServerWorkshop {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            100,
            100,
            0L,
            MILLISECONDS,
            new ArrayBlockingQueue<>(1000),
            new ThreadPoolExecutor.DiscardPolicy()
    );

    public static void main(String[] args) throws IOException {
        new Step9_ThreadPoolServerWorkshop().start();
    }

    @Override
    void handle(Runnable clientConnection) {
        executor.execute(clientConnection);
    }

}