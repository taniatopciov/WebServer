package config;

public class ConfigFile {

    private final static String defaultPortNumber = "8080";
    private final static String defaultRootFolder = "public_html";
    private final static ServerState defaultState = ServerState.Stopped;

    private String portNumber;
    private String rootFolder;
    private ServerState state;

    public ConfigFile() {
        this(defaultPortNumber, defaultRootFolder, defaultState);
    }

    public ConfigFile(String portNumber, String rootFolder, ServerState state) {
        this.portNumber = portNumber;
        this.rootFolder = rootFolder;
        this.state = state;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public String getRootFolder() {
        return rootFolder;
    }

    public ServerState getState() {
        return state;
    }
}
