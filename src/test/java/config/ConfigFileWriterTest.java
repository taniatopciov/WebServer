package config;

import io.FileContentWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConfigFileWriterTest {
    @Mock
    private FileContentWriter contentWriter;

    private ConfigFileWriter configFileWriter;

    @Before
    public void setUp() {
        configFileWriter = new ConfigFileWriter(contentWriter);
    }

    @Test
    public void writeConfigFile_NullConfigFile() throws IOException {
        configFileWriter.writeConfigFile(null);

        String expectedJsonOutput = "{\"portNumber\":\"8080\",\"rootFolder\":\"public_html\",\"maintenanceFilePath\":\"maintenance.html\",\"state\":\"STOPPED\"}";

        verify(contentWriter, times(1)).writeContentToFile("config.json", expectedJsonOutput);
    }

    @Test
    public void writeConfigFile_GivenConfigFile() throws IOException {
        configFileWriter.writeConfigFile(new ConfigFile("1234", "www", "path.html", ServerState.MAINTENANCE));

        String expectedJsonOutput = "{\"portNumber\":\"1234\",\"rootFolder\":\"www\",\"maintenanceFilePath\":\"path.html\",\"state\":\"MAINTENANCE\"}";

        verify(contentWriter, times(1)).writeContentToFile("config.json", expectedJsonOutput);
    }
}