package request;

import config.ConfigFile;
import config.ServerState;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class PostRequestHandlerImplTest {

    private PostRequestHandler postRequestHandler;

    @Before
    public void setUp() {
        postRequestHandler = new PostRequestHandlerImpl();
    }

    @Test
    public void getNewConfigFile_ServerState() {
        ConfigFile configFile = new ConfigFile();

        HashMap<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Location", "http://www.google.com/");
        requestHeaders.put("Expires", "Fri, 24 Apr 2020 18:53:12 GMT");
        requestHeaders.put("ServerState", "Running");
        requestHeaders.put("Server", "gws");

        ConfigFile newConfigFile = postRequestHandler.getNewConfigFile(configFile, requestHeaders);

        assertEquals(ServerState.Running, newConfigFile.getState());
    }

    @Test
    public void getNewConfigFile_MaintenancePath() {
        ConfigFile configFile = new ConfigFile();

        HashMap<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Location", "http://www.google.com/");
        requestHeaders.put("Expires", "Fri, 24 Apr 2020 18:53:12 GMT");
        requestHeaders.put("Server", "gws");
        requestHeaders.put("MaintenanceFilePath", "another_mfp");

        ConfigFile newConfigFile = postRequestHandler.getNewConfigFile(configFile, requestHeaders);

        assertEquals("another_mfp", newConfigFile.getMaintenanceFilePath());
    }

    @Test
    public void getNewConfigFile_RootFolder() {
        ConfigFile configFile = new ConfigFile();

        HashMap<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Location", "http://www.google.com/");
        requestHeaders.put("RootFolder", "another_folder");
        requestHeaders.put("Expires", "Fri, 24 Apr 2020 18:53:12 GMT");
        requestHeaders.put("Server", "gws");

        ConfigFile newConfigFile = postRequestHandler.getNewConfigFile(configFile, requestHeaders);

        assertEquals("another_folder", newConfigFile.getRootFolder());
    }

    @Test
    public void getNewConfigFile_ChangeAll() {
        ConfigFile configFile = new ConfigFile();

        HashMap<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Location", "http://www.google.com/");
        requestHeaders.put("MaintenanceFilePath", "myPath.html");
        requestHeaders.put("Expires", "Fri, 24 Apr 2020 18:53:12 GMT");
        requestHeaders.put("ServerState", "Maintenance");
        requestHeaders.put("Server", "gws");
        requestHeaders.put("RootFolder", "my_root_folder");

        ConfigFile newConfigFile = postRequestHandler.getNewConfigFile(configFile, requestHeaders);

        assertEquals("my_root_folder", newConfigFile.getRootFolder());
        assertEquals("myPath.html", newConfigFile.getMaintenanceFilePath());
        assertEquals(ServerState.Maintenance, newConfigFile.getState());
    }

    @Test
    public void getNewConfigFile_NoChange() {
        ConfigFile configFile = new ConfigFile();

        HashMap<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Location", "http://www.google.com/");
        requestHeaders.put("Expires", "Fri, 24 Apr 2020 18:53:12 GMT");
        requestHeaders.put("Server", "gws");

        ConfigFile newConfigFile = postRequestHandler.getNewConfigFile(configFile, requestHeaders);

        assertTrue(configFile.equals(newConfigFile));
    }
}