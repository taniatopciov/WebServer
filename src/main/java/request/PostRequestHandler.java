package request;

import config.ConfigFile;

import java.util.Map;

public interface PostRequestHandler {

    public ConfigFile getNewConfigFile(ConfigFile configFile, Map<String, String> requestHeaders);
}
