package server;

import io.vavr.control.Try;

import java.io.*;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-06-29.
 */
class ClientConnection {
    final Socket client;

    ClientConnection(Socket client) {
        this.client = client;
    }

    void run() {
        sendLine("What's you name?");

        Try<String> str = readLine();
        str.onSuccess(message -> sendLine("Hello, " + message));

        System.out.println("Just said hello to:" + str);
    }

    private void sendLine(String message) {
        Try<PrintWriter> pw = autoFlushablePrintWriter();
        pw.onSuccess(writer -> writer.println(message));
    }

    private Try<String> readLine() {
        return Try.withResources(() -> new BufferedReader(new InputStreamReader(client.getInputStream()))).of(
                BufferedReader::readLine
        );
    }

    private Try<PrintWriter> autoFlushablePrintWriter() {
        return Try.withResources(() -> client).of(ignore -> {
            OutputStream os = client.getOutputStream();
            return new PrintWriter(os, true);
        });
    }
}
