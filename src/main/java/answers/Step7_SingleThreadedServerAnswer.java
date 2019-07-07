package answers;

import java.io.IOException;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class Step7_SingleThreadedServerAnswer extends Step6_ServerAnswer {
    
    public static void main(String[] args) throws IOException {
        new Step7_SingleThreadedServerAnswer().start();
    }

    @Override
    void handle(Runnable clientConnection) {
        clientConnection.run();
    }
}
