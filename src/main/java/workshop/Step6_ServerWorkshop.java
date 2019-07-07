package workshop;

import java.io.IOException;

/**
 * Created by mtumilowicz on 2019-06-29.
 */
abstract class Step6_ServerWorkshop {
    private final int portNumber = 81;

    /**
     * move all common code of start() methods in {@link Step4_SingleThreadedServerWorkshop}, 
     * {@link Step5_ThreadPerRequestServerWorkshop}
     * 
     * abstract single point of difference into abstract method
     */
    void start() throws IOException {

    }
    
    private void log(String message) {
        System.out.println(message);
    }
}