package config;

import io.JsonParser;

public class ConfigFileReader {

    private String configFilePath = "config.json";
    private JsonParser jsonParser;

    public ConfigFileReader(JsonParser jsonParser) {
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