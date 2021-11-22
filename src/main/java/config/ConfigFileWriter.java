package config;

import com.google.gson.Gson;
import io.FileContentWriter;

import java.io.IOException;

public class ConfigFileWriter {

    private final FileContentWriter contentWriter;
    private final String configFilePath = "config.json";
    private final Gson gson = new Gson();

    public ConfigFileWriter(FileContentWriter contentWriter) {
        this.contentWriter = contentWriter;
    }

    public void writeConfigFile(ConfigFile configFile) throws IOException {
        if (configFile == null) {
            configFile = new ConfigFile();
        }

        contentWriter.writeContentToFile(configFilePath, gson.toJson(configFile));
    }
}
