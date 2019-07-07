package workshop;

import java.io.IOException;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class Step8_ThreadPerRequestServerWorkshop extends Step6_ServerWorkshop {

    public static void main(String[] args) throws IOException {
        new Step8_ThreadPerRequestServerWorkshop().start();
    }

    @Override
    void handle(Runnable clientConnection) {
        new Thread(clientConnection).start();
    }
}
