package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-06-29.
 */
class ClientConnection implements Runnable {
    private final Socket client;
    private final PrintWriter writer;
    private final BufferedReader reader;

    ClientConnection(Socket client) throws IOException {
        this.client = client;
        this.writer = new PrintWriter(client.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    @Override
    public void run() {
        try (client; writer; reader) {
            sendLine("What's you name?");

            var name = readLine();
            sendLine("Hello, " + name);

            System.out.println("Just said hello to:" + name);
        } catch (IOException exception) {
            // workshops
        }
    }

    private void sendLine(String message) {
        writer.println(message);
    }

    private String readLine() throws IOException {
        return reader.readLine();
    }
}
