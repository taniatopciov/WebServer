package config;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

public class ConfigFileWriter {

    private String configFilePath = "config.json";
    private Gson gson = new Gson();

    public void writeConfigFile(ConfigFile configFile) throws IOException {
        FileWriter fileWriter = new FileWriter(configFilePath);
        if (configFile == null) {
            gson.toJson(new ConfigFile(), fileWriter);
        } else {
            gson.toJson(configFile, fileWriter);
        }
        fileWriter.close();
    }
}
