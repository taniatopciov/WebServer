package config;

import exceptions.EmptyFileException;
import exceptions.InvalidFileTypeException;
import io.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfigFileReaderTest {

    @Mock
    private JsonParser jsonParser;

    private ConfigFileReader configFileReader;

    @Before
    public void setUp() {
        configFileReader = new ConfigFileReader(jsonParser);
    }

    @Test
    public void readConfigFile_noFile() throws Exception {
        when(jsonParser.parseFile("config.json", ConfigFile.class)).thenThrow(new FileNotFoundException());

        ConfigFile configFile = configFileReader.readConfigFile();

        assertEquals("8080", configFile.getPortNumber());
        assertEquals("public_html", configFile.getRootFolder());
    }

    @Test
    public void readConfigFile_emptyFile() throws Exception {
        when(jsonParser.parseFile("config.json", ConfigFile.class)).thenThrow(new EmptyFileException("config.json empty"));

        ConfigFile configFile = configFileReader.readConfigFile();

        assertEquals("8080", configFile.getPortNumber());
        assertEquals("public_html", configFile.getRootFolder());
    }

    @Test
    public void readConfigFile_notJson() throws Exception {
        when(jsonParser.parseFile("config.json", ConfigFile.class)).thenThrow(new InvalidFileTypeException());

        ConfigFile configFile = configFileReader.readConfigFile();

        assertEquals("8080", configFile.getPortNumber());
        assertEquals("public_html", configFile.getRootFolder());
    }

    @Test
    public void readConfigFile_jsonWithUnknownFields() throws Exception {
        when(jsonParser.parseFile("config.json", ConfigFile.class)).thenReturn(new ConfigFile());

        ConfigFile configFile = configFileReader.readConfigFile();

        assertEquals("8080", configFile.getPortNumber());
        assertEquals("public_html", configFile.getRootFolder());
    }

    @Test
    public void readConfigFile_works() throws Exception {
        when(jsonParser.parseFile("config.json", ConfigFile.class)).thenReturn(new ConfigFile("8001", "www", ServerState.Running));

        ConfigFile configFile = configFileReader.readConfigFile();

        assertEquals("8001", configFile.getPortNumber());
        assertEquals("www", configFile.getRootFolder());
        assertEquals(ServerState.Running, configFile.getState());
    }
}