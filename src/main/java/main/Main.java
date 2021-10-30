package main;

import config.ConfigFile;
import config.ConfigFileReader;
import io.FileContentReaderImpl;
import io.JsonParserImpl;

public class Main {

    public static void main(String args[]) {
        ConfigFileReader configFileReader = new ConfigFileReader(new JsonParserImpl(new FileContentReaderImpl()));

        ConfigFile configFile = configFileReader.readConfigFile();

        Server server = new Server(configFile);

        server.startServer();
    }
}