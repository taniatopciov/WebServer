package request;

import config.ConfigFile;

import java.util.HashMap;

public interface PostRequestHandler {

    public ConfigFile getNewConfigFile(ConfigFile configFile, HashMap<String, String> requestHeaders);
}
