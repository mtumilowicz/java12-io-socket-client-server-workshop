package server;

import io.vavr.control.Try;

import java.io.*;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-06-29.
 */
class ClientConnection {
    static void run(Socket client) {
        Try.withResources(() -> client).of(ignore -> {
            Try<PrintWriter> pw = autoFlushablePrintWriter(client);
            pw.onSuccess(writer -> writer.println("What's you name?"));

            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String str = br.readLine();

            pw.onSuccess(writer -> writer.println("Hello, " + str));
            pw.onSuccess(PrintWriter::close);

            System.out.println("Just said hello to:" + str);
            return Void.class;
        });
    }

    static Try<PrintWriter> autoFlushablePrintWriter(Socket client) {
        return Try.withResources(() -> client).of(ignore -> {
            OutputStream os = client.getOutputStream();
            return new PrintWriter(os, true);
        });
    }
    
    static Try<BufferedReader> readLine(Socket socket) {
        return null;
    }
}
