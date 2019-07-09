package workshop;

import java.io.IOException;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
class Step1_SingleThreadedServerWorkshop {

    private final int portNumber;

    Step1_SingleThreadedServerWorkshop(int portNumber) {
        this.portNumber = portNumber;
    }

    private Step1_SingleThreadedServerWorkshop() {
        this.portNumber = 81;
    }

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
     * accept client, hint: serverSocket.accept()
     *
     * log "Accepted connection from " + client, hint: log
     *
     * try-with-resources for client
     * create autoflushable PrintWriter from client outputStream
     * hint: client.getOutputStream()
     *
     * create BufferedReader from client inputStream
     * hint: InputStreamReader, client.getInputStream()
     *
     * push message to the client: "What's your name?", hint: writer.println
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
