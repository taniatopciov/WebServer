package config;

import com.google.gson.Gson;
import io.FileContentWriter;

import java.io.IOException;

public class ConfigFileWriter {

    private static final String CONFIG_FILE_PATH = "config.json";

    private final FileContentWriter contentWriter;
    private final Gson gson = new Gson();

    public ConfigFileWriter(FileContentWriter contentWriter) {
        this.contentWriter = contentWriter;
    }

    public void writeConfigFile(ConfigFile configFile) throws IOException {
        if (configFile == null) {
            configFile = new ConfigFile();
        }

        contentWriter.writeContentToFile(CONFIG_FILE_PATH, gson.toJson(configFile));
    }
}