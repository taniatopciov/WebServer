package resource_manager;

import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResourceFileManagerImplTest {
    private ResourceFileManager resourceFileManager;

    @Before
    public void setUp() {
        resourceFileManager = new ResourceFileManagerImpl();
    }

    @Test
    public void checkFileExists() throws URISyntaxException {
        String path = String.valueOf(Paths.get(getClass().getClassLoader().getResource("DefaultResponses/404.html").toURI()).toAbsolutePath());
        assertTrue(resourceFileManager.checkFileExists(path));
    }

    @Test
    public void checkFileNotExists() {
        assertFalse(resourceFileManager.checkFileExists("someInvalidFile.txt"));
    }
}