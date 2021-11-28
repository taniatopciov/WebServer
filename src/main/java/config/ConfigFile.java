package config;

import java.util.Objects;

public class ConfigFile {

    private static final String DEFAULT_PORT_NUMBER = "8080";
    private static final String DEFAULT_ROOT_FOLDER = "public_html";
    private static final ServerState DEFAULT_STATE = ServerState.STOPPED;
    private static final String DEFAULT_MAINTENANCE_FILE_PATH = "maintenance.html";

    private String portNumber;
    private String rootFolder;
    private String maintenanceFilePath;
    private ServerState state;

    public ConfigFile() {
        this(DEFAULT_PORT_NUMBER, DEFAULT_ROOT_FOLDER, DEFAULT_MAINTENANCE_FILE_PATH, DEFAULT_STATE);
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

    public int hashCode() {
        return Objects.hash(portNumber, rootFolder, maintenanceFilePath, state);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof ConfigFile)) {
            return false;
        }

        ConfigFile c = (ConfigFile) o;

        return portNumber.equals(c.getPortNumber()) && rootFolder.equals(c.getRootFolder()) && maintenanceFilePath.equals(c.getMaintenanceFilePath()) && state == c.getState();
    }
}
