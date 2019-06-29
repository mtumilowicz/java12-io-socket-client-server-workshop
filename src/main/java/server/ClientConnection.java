package server;

import io.vavr.control.Try;

import java.io.*;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-06-29.
 */
class ClientConnection {
    private final Socket client;
    private final PrintWriter writer;
    private final BufferedReader reader;

    ClientConnection(Socket client) throws IOException {
        this.client = client;
        this.writer = new PrintWriter(client.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    void run() {
        sendLine("What's you name?");

        Try<String> str = readLine();
        str.onSuccess(message -> sendLine("Hello, " + message));

        System.out.println("Just said hello to:" + str);
    }

    private void sendLine(String message) {
        writer.println(message);
    }

    private Try<String> readLine() {
        return Try.of(reader::readLine);
    }
}
