package config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ConfigFileTest {

    private ConfigFile configFile;

    @Test
    public void getConfiguration_Default() {
        configFile = new ConfigFile();

        String portNumber = configFile.getPortNumber();
        String defaultRootFolder = configFile.getRootFolder();
        String defaultMaintenanceFilePath = configFile.getMaintenanceFilePath();
        ServerState defaultState = configFile.getState();

        assertEquals("8080", portNumber);
        assertEquals("public_html", defaultRootFolder);
        assertEquals("maintenance.html", defaultMaintenanceFilePath);
        assertEquals(ServerState.STOPPED, defaultState);
    }

    @Test
    public void getConfiguration_Given() {
        configFile = new ConfigFile("8090", "rootFolder", "mfp.html", ServerState.RUNNING);

        String portNumber = configFile.getPortNumber();
        String defaultRootFolder = configFile.getRootFolder();
        String defaultMaintenanceFilePath = configFile.getMaintenanceFilePath();
        ServerState defaultState = configFile.getState();

        assertEquals("8090", portNumber);
        assertEquals("rootFolder", defaultRootFolder);
        assertEquals("mfp.html", defaultMaintenanceFilePath);
        assertEquals(ServerState.RUNNING, defaultState);
    }

    @Test
    public void setConfiguration() {
        configFile = new ConfigFile();

        configFile.setPortNumber("4200");
        configFile.setRootFolder("anotherRoot");
        configFile.setMaintenanceFilePath("thisMaintenance.html");
        configFile.setState(ServerState.MAINTENANCE);

        assertEquals("4200", configFile.getPortNumber());
        assertEquals("anotherRoot", configFile.getRootFolder());
        assertEquals("thisMaintenance.html", configFile.getMaintenanceFilePath());
        assertEquals(ServerState.MAINTENANCE, configFile.getState());
    }

    @Test
    public void equals_ReturnsTrue_DifferentObjects() {
        configFile = new ConfigFile();
        ConfigFile configFile2 = new ConfigFile();

        assertEquals(configFile, configFile2);
    }

    @Test
    public void equals_ReturnsTrue_SameObject() {
        configFile = new ConfigFile();

        assertEquals(configFile, configFile);
    }

    @Test
    public void equals_ReturnsFalse_NotInstanceofConfigFile() {
        configFile = new ConfigFile();
        Object o = new Object();

        assertNotEquals(configFile, o);
    }

    @Test
    public void equals_ReturnsFalse_DifferentPortNumber() {
        configFile = new ConfigFile("8090", "rootFolder", "mfp.html", ServerState.RUNNING);
        ConfigFile configFile2 = new ConfigFile("4200", "rootFolder", "mfp.html", ServerState.RUNNING);

        assertNotEquals(configFile, configFile2);
    }

    @Test
    public void equals_ReturnsFalse_DifferentRootFolder() {
        configFile = new ConfigFile("8090", "rootFolder", "mfp.html", ServerState.RUNNING);
        ConfigFile configFile2 = new ConfigFile("8090", "public_html", "mfp.html", ServerState.RUNNING);

        assertNotEquals(configFile, configFile2);
    }

    @Test
    public void equals_ReturnsFalse_DifferentMaintenanceFilePath() {
        configFile = new ConfigFile("8090", "rootFolder", "mfp.html", ServerState.RUNNING);
        ConfigFile configFile2 = new ConfigFile("8090", "rootFolder", "maintenance.html", ServerState.RUNNING);

        assertNotEquals(configFile, configFile2);
    }

    @Test
    public void equals_ReturnsFalse_DifferentServerState() {
        configFile = new ConfigFile("8090", "rootFolder", "maintenance.html", ServerState.STOPPED);
        ConfigFile configFile2 = new ConfigFile("8090", "rootFolder", "maintenance.html", ServerState.RUNNING);

        assertNotEquals(configFile, configFile2);
    }
}