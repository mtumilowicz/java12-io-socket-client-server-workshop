package answers;

import java.io.IOException;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
class Step7_SingleThreadedServerAnswer extends Step6_ServerAnswer {

    public Step7_SingleThreadedServerAnswer(int portNumber) {
        super(portNumber);
    }

    private Step7_SingleThreadedServerAnswer() {
    }
    
    public static void main(String[] args) throws IOException {
        new Step7_SingleThreadedServerAnswer().start();
    }

    @Override
    void handle(Runnable clientConnection) {
        clientConnection.run();
    }
}
