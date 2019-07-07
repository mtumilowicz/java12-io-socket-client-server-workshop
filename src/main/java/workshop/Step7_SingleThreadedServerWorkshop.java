package workshop;

import java.io.IOException;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class Step7_SingleThreadedServerWorkshop extends Step6_ServerWorkshop {
    
    public static void main(String[] args) throws IOException {
        new Step7_SingleThreadedServerWorkshop().start();
    }

    @Override
    void handle(Runnable clientConnection) {
        clientConnection.run();
    }
}
