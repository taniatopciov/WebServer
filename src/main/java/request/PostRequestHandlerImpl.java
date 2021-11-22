package request;

import config.ConfigFile;
import config.ServerState;

import java.util.HashMap;

public class PostRequestHandlerImpl implements PostRequestHandler {
    @Override
    public ConfigFile getNewConfigFile(ConfigFile configFile, HashMap<String, String> requestHeaders) {
        if (requestHeaders.containsKey("ServerState")) {
            ServerState serverState = ServerState.valueOf(requestHeaders.get("ServerState"));
            configFile.setState(serverState);
        }
        if (requestHeaders.containsKey("MaintenanceFilePath")) {
            String maintenanceFilePath = requestHeaders.get("MaintenanceFilePath");
            configFile.setMaintenanceFilePath(maintenanceFilePath);
        }
        if (requestHeaders.containsKey("RootFolder")) {
            String rootFolder = requestHeaders.get("RootFolder");
            configFile.setRootFolder(rootFolder);
        }

        return configFile;
    }
}
