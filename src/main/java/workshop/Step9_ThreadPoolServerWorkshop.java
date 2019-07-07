package workshop;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
// inherit from Step6_ServerWorkshop, hint: executor.execute(...)
class Step9_ThreadPoolServerWorkshop {

    /**
     * create ThreadPoolExecutor
     * 
     * hint: use constructor ThreadPoolExecutor(int corePoolSize,
     *                                  int maximumPoolSize,
     *                                  long keepAliveTime,
     *                                  TimeUnit unit,
     *                                  BlockingQueue<Runnable> workQueue,
     *                                  RejectedExecutionHandler handler)
     *
     */
    private static final ThreadPoolExecutor executor = null;

    public static void main(String[] args) throws IOException {
//        new Step9_ThreadPoolServerWorkshop().start();
    }

}