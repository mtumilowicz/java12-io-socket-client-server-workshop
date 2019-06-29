import java.io.*;
import java.net.Socket;

/**
 * Created by mtumilowicz on 2019-06-23.
 */
public class TestServerThreadPRequestSocket extends Server {

    public static void main(String[] args) throws IOException {
        new TestServerThreadPRequestSocket().start();
    }
    
    @Override
    void handle(Socket client) {
        new Thread(() -> {
            try (client) {
                OutputStream os = client.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                pw.println("What's you name?");

                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String str = br.readLine();

                pw.println("Hello, " + str);
                pw.close();
                client.close();

                System.out.println("Just said hello to:" + str);
            } catch (IOException ex) {
                // workshops
            }
        }).start();
    }
}
