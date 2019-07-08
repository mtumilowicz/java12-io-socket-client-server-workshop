package answers;

import java.io.IOException;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
class Step8_ThreadPerRequestServerAnswer extends Step6_ServerAnswer {

    public Step8_ThreadPerRequestServerAnswer(int portNumber) {
        super(portNumber);
    }

    private Step8_ThreadPerRequestServerAnswer() {
    }

    public static void main(String[] args) throws IOException {
        new Step8_ThreadPerRequestServerAnswer().start();
    }

    @Override
    void handle(Runnable clientConnection) {
        new Thread(clientConnection).start();
    }
}
