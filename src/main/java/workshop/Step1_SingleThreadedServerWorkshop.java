package workshop;

import java.io.IOException;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
class Step1_SingleThreadedServerWorkshop {

    final int portNumber = 81;

    public static void main(String[] args) throws IOException {
        new Step1_SingleThreadedServerWorkshop().start();
    }

    /**
     * log message: "Creating server socket on port " + portNumber, hint: log
     *
     * create serverSocket, hint: new ServerSocket(portNumber)
     *
     * log message: "Created server socket on port " + portNumber, hint: log
     *
     * create never ending loop inside which we will accept connections
     * hint: while(true), for(;;)
     *
     * accept client, hint: serverSocket.accept(), try-with-resources
     *
     * log "Accepted connection from " + client, hint: log
     *
     * create autoflushable PrintWriter from client outputStream
     * hint: client.getOutputStream(), try-with-resources
     *
     * create BufferedReader from client inputStream
     * hint: InputStreamReader, client.getInputStream(), try-with-resources
     *
     * push message to the client: "What's you name?", hint: writer.println
     *
     * receive message from the client with the name, hint: reader.readLine()
     *
     * push message to the client: "Hello, " + name, hint: writer.println
     *
     * log message: "Just said hello to:" + name, hint: log
     */
    void start() throws IOException {
    }

    private void log(String message) {
        System.out.println(message);
    }
}
