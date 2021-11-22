package main;

import config.ConfigFileReader;
import io.FileContentReaderImpl;
import io.JsonParserImpl;

public class Main {

    public static void main(String args[]) {
        try {
            new Server(new ConfigFileReader(new JsonParserImpl(new FileContentReaderImpl())).readConfigFile()).startServer();
        } catch (Exception e) {
            System.out.println("Server closed");
        }
    }
}