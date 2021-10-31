package config;

public class ConfigFile {

    private final static String defaultPortNumber = "8080";
    private final static String defaultRootFolder = "public_html";
    private final static ServerState defaultState = ServerState.Stopped;
    private final static String defaultMaintenanceFilePath = "maintenance.html";

    private String portNumber;
    private String rootFolder;
    private String maintenanceFilePath;
    private ServerState state;

    public ConfigFile() {
        this(defaultPortNumber, defaultRootFolder, defaultMaintenanceFilePath, defaultState);
    }

    public ConfigFile(String portNumber, String rootFolder, String maintenanceFilePath, ServerState state) {
        this.portNumber = portNumber;
        this.rootFolder = rootFolder;
        this.maintenanceFilePath = maintenanceFilePath;
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

    public String getMaintenanceFilePath() {
        return maintenanceFilePath;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public void setRootFolder(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public void setState(ServerState state) {
        this.state = state;
    }

    public void setMaintenanceFilePath(String maintenanceFilePath) {
        this.maintenanceFilePath = maintenanceFilePath;
    }
}
