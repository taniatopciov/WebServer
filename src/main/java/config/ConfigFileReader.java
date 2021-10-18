package config;

import io_file_manager.IJsonParser;

public class ConfigFileReader {

    private String configFilePath = "config.json";
    private IJsonParser jsonParser;

    public ConfigFileReader(IJsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public ConfigFile readConfigFile() {
        try {
            return jsonParser.parseFile(configFilePath, ConfigFile.class);
        } catch (Exception e) {
            return new ConfigFile();
        }
    }
}