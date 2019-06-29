package server;

import io.vavr.control.Try;

import java.io.*;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-06-29.
 */
class ClientConnection {
    static void run(Socket client) {
            sendLine(client, "What's you name?");
            
            Try<String> str = readLine(client);
            str.onSuccess(message -> sendLine(client, "Hello, " + message));

            System.out.println("Just said hello to:" + str);
    }
    
    static void sendLine(Socket client, String message) {
        Try<PrintWriter> pw = autoFlushablePrintWriter(client);
        pw.onSuccess(writer -> writer.println(message));
    }

    static Try<String> readLine(Socket client) {
        return Try.withResources(() -> new BufferedReader(new InputStreamReader(client.getInputStream()))).of(
                BufferedReader::readLine
        );
    }

    static Try<PrintWriter> autoFlushablePrintWriter(Socket client) {
        return Try.withResources(() -> client).of(ignore -> {
            OutputStream os = client.getOutputStream();
            return new PrintWriter(os, true);
        });
    }
}
