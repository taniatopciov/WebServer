package resource_manager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourcePathManagerImplTest {
    @Mock
    private ResourceFileManager resourceFileManager;

    @Mock
    private ResourceAbsolutePathProvider resourceAbsolutePathProvider;

    private ResourcePathManager resourcePathManager;

    @Before
    public void setUp() {
        resourcePathManager = new ResourcePathManagerImpl(resourceAbsolutePathProvider, resourceFileManager);
    }

    @Test
    public void getResourcePath_ThroesUnsupportedEncodingException() {
        String resourcePath = resourcePathManager.getResourcePath("testÃ¼", null);

        assertNull(resourcePath);
    }

    @Test
    public void getResourcePath_RootPathNoBS_NullPath_FileExists() {
        when(resourceFileManager.checkFileExists("public_html/index.html")).thenReturn(true);

        String resourcePath = resourcePathManager.getResourcePath("public_html", null);

        assertEquals("public_html/index.html", resourcePath);
    }

    @Test
    public void getResourcePath_RootPathNoBS_EmptyPath_FileExists() {
        when(resourceFileManager.checkFileExists("public_html/index.html")).thenReturn(true);

        String resourcePath = resourcePathManager.getResourcePath("public_html", "");

        assertEquals("public_html/index.html", resourcePath);
    }

    @Test
    public void getResourcePath_RootPathNoBS_PathOnlyBS_FileExists() {
        when(resourceFileManager.checkFileExists("public_html/index.html")).thenReturn(true);

        String resourcePath = resourcePathManager.getResourcePath("public_html", "/");

        assertEquals("public_html/index.html", resourcePath);
    }

    @Test
    public void getResourcePath_RootPathNoBS_PathNoBS_FileExists() {
        when(resourceFileManager.checkFileExists("root/path.html")).thenReturn(true);

        String resourcePath = resourcePathManager.getResourcePath("root", "path.html");

        assertEquals("root/path.html", resourcePath);
    }

    @Test
    public void getResourcePath_RootPathWithBS_PathWithBS_FileExists() {
        when(resourceFileManager.checkFileExists("root/path.html")).thenReturn(true);

        String resourcePath = resourcePathManager.getResourcePath("root/", "/path.html");

        assertEquals("root/path.html", resourcePath);
    }

    @Test
    public void getResourcePath_RootPathWithBS_PathNoBS_FileExists() {
        when(resourceFileManager.checkFileExists("root/path.html")).thenReturn(true);

        String resourcePath = resourcePathManager.getResourcePath("root/", "path.html");

        assertEquals("root/path.html", resourcePath);
    }

    @Test
    public void getResourcePath_RootPathNoBS_PathWithBS_FileExists() {
        when(resourceFileManager.checkFileExists("root/path.html")).thenReturn(true);

        String resourcePath = resourcePathManager.getResourcePath("root/", "path.html");

        assertEquals("root/path.html", resourcePath);
    }

    @Test
    public void getResourcePath_RootPathWithBS_PathNoBS_FileExists_SpacesInFileName() {
        when(resourceFileManager.checkFileExists("root/random path.html")).thenReturn(true);

        String resourcePath = resourcePathManager.getResourcePath("root/", "random path.html");

        assertEquals("root/random path.html", resourcePath);
    }

    @Test
    public void getResourcePath_RootPathWithBS_PathNoBS_FileExists_SpacesInFileName2() {
        when(resourceFileManager.checkFileExists("root/random path.html")).thenReturn(true);

        String resourcePath = resourcePathManager.getResourcePath("root/", "random%20path.html");

        assertEquals("root/random path.html", resourcePath);
    }

    @Test
    public void getResourcePath_RootPathWithBS_PathNoBS_FileExists_SpacesInFileName2_False() {
        when(resourceFileManager.checkFileExists("root/random path.html")).thenReturn(true);

        String resourcePath = resourcePathManager.getResourcePath("root/", "random%20path.html");

        assertNotEquals("root/random%20path.html", resourcePath);
    }

    @Test
    public void getResourcePath_RootPathNoBS_PathNoBS_FileDoesNotExist() {
        when(resourceFileManager.checkFileExists("root/path.html")).thenReturn(false);

        when(resourceAbsolutePathProvider.getResourceAbsolutePath("DefaultResponses/404.html")).thenReturn("receive/some/path");

        String resourcePath = resourcePathManager.getResourcePath("root/", "path.html");

        assertEquals("receive/some/path", resourcePath);
    }
}