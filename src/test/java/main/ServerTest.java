package main;

import config.ConfigFile;
import exceptions.ServerCloseException;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ServerTest {

    private Server server;

    @Before
    public void setUp() {
        server = new Server(new ConfigFile());
    }

    @Test(expected = ServerCloseException.class)
    public void serverStop_AfterReceivingServerStatusStopped() throws IOException {
        Thread thread = new Thread(() -> {
            try {
                sleep(1000);

                Socket socket = new Socket(InetAddress.getByName("localhost"), 8080);

                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                out.println("POST /configserver HTTP/1.1");
                out.println("ServerState: Stopped");
                out.println();

                String line = in.readLine();
                while (line != null) {
                    line = in.readLine();
                }

                in.close();
                out.close();
                socket.close();

            } catch (IOException | InterruptedException ignored) {
            }
        });

        try {
            thread.start();
            server.startServer();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}