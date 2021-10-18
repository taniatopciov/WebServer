package config;

public class ConfigFile {

    private final static String defaultPortNumber = "8080";
    private final static String defaultRootFolder = "public_html";

    private String portNumber;
    private String rootFolder;

    public ConfigFile() {
        this(defaultPortNumber, defaultRootFolder);
    }

    public ConfigFile(String portNumber, String rootFolder) {
        this.portNumber = portNumber;
        this.rootFolder = rootFolder;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public String getRootFolder() {
        return rootFolder;
    }
}
