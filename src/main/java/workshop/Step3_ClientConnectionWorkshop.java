package workshop;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-07-07.
 */
/**
 * encapsulate common code from {@link Step1_SingleThreadedServerWorkshop}, {@link Step2_ThreadPerConnectionServerWorkshop}
 * 
 * 3 fields: Socket client, PrintWriter writer, BufferedReader reader
 * 
 * 2 private util methods: sendLine, readLine
 * hint: sendLine - writer.println(message), readLine - reader.readLine()
 */
class Step3_ClientConnectionWorkshop implements Runnable {

    Step3_ClientConnectionWorkshop(Socket client) throws IOException {
        
    }
    
    @Override
    public void run() {
        // hint: start with try-with-resources for client
    }
    
    private void log(String message) {
        System.out.println(message);
    }
}
