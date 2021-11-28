package main;

import config.ConfigFileReader;
import io.FileContentReaderImpl;
import io.JsonParserImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        try {
            new Server(new ConfigFileReader(new JsonParserImpl(new FileContentReaderImpl())).readConfigFile()).startServer();
        } catch (Exception e) {
            logger.log(Level.INFO,"Server closed");
        }
    }
}