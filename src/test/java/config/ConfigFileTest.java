package config;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigFileTest {

    private ConfigFile configFile;

    @Test
    public void getConfiguration_Default() {
        configFile = new ConfigFile();

        String portNumber = configFile.getPortNumber();
        String defaultRootFolder = configFile.getRootFolder();
        String defaultMaintenanceFilePath  = configFile.getMaintenanceFilePath();
        ServerState defaultState = configFile.getState();

        assertEquals("8080", portNumber);
        assertEquals("public_html", defaultRootFolder);
        assertEquals("maintenance.html", defaultMaintenanceFilePath);
        assertEquals(ServerState.Stopped, defaultState);
    }

    @Test
    public void getConfiguration_Given() {
        configFile = new ConfigFile("8090", "rootFolder", "mfp.html", ServerState.Running);

        String portNumber = configFile.getPortNumber();
        String defaultRootFolder = configFile.getRootFolder();
        String defaultMaintenanceFilePath  = configFile.getMaintenanceFilePath();
        ServerState defaultState = configFile.getState();

        assertEquals("8090", portNumber);
        assertEquals("rootFolder", defaultRootFolder);
        assertEquals("mfp.html", defaultMaintenanceFilePath);
        assertEquals(ServerState.Running, defaultState);
    }

    @Test
    public void setConfiguration() {
        configFile = new ConfigFile();

        configFile.setPortNumber("4200");
        configFile.setRootFolder("anotherRoot");
        configFile.setMaintenanceFilePath("thisMaintenance.html");
        configFile.setState(ServerState.Maintenance);

        assertEquals("4200", configFile.getPortNumber());
        assertEquals("anotherRoot", configFile.getRootFolder());
        assertEquals("thisMaintenance.html", configFile.getMaintenanceFilePath());
        assertEquals(ServerState.Maintenance, configFile.getState());
    }

    @Test
    public void equals_ReturnsTrue_DifferentObjects() {
        configFile = new ConfigFile();
        ConfigFile configFile2 = new ConfigFile();

        assertTrue(configFile.equals(configFile2));
    }

    @Test
    public void equals_ReturnsTrue_SameObject() {
        configFile = new ConfigFile();

        assertTrue(configFile.equals(configFile));
    }

    @Test
    public void equals_ReturnsFalse_NotInstanceofConfigFile() {
        configFile = new ConfigFile();
        Object o = new Object();

        assertFalse(configFile.equals(o));
    }

    @Test
    public void equals_ReturnsFalse_DifferentPortNumber() {
        configFile = new ConfigFile("8090", "rootFolder", "mfp.html", ServerState.Running);
        ConfigFile configFile2 = new ConfigFile("4200", "rootFolder", "mfp.html", ServerState.Running);

        assertFalse(configFile.equals(configFile2));
    }

    @Test
    public void equals_ReturnsFalse_DifferentRootFolder() {
        configFile = new ConfigFile("8090", "rootFolder", "mfp.html", ServerState.Running);
        ConfigFile configFile2 = new ConfigFile("8090", "public_html", "mfp.html", ServerState.Running);

        assertFalse(configFile.equals(configFile2));
    }

    @Test
    public void equals_ReturnsFalse_DifferentMaintenanceFilePath() {
        configFile = new ConfigFile("8090", "rootFolder", "mfp.html", ServerState.Running);
        ConfigFile configFile2 = new ConfigFile("8090", "rootFolder", "maintenance.html", ServerState.Running);

        assertFalse(configFile.equals(configFile2));
    }

    @Test
    public void equals_ReturnsFalse_DifferentServerState() {
        configFile = new ConfigFile("8090", "rootFolder", "maintenance.html", ServerState.Stopped);
        ConfigFile configFile2 = new ConfigFile("8090", "rootFolder", "maintenance.html", ServerState.Running);

        assertFalse(configFile.equals(configFile2));
    }
}