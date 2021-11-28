package resource_manager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ResourceAbsolutePathProviderImplTest {

    private ResourceAbsolutePathProvider resourceAbsolutePathProvider;

    @Before
    public void setUp() {
        resourceAbsolutePathProvider = new ResourceAbsolutePathProviderImpl();
    }

    @Test
    public void getResourceAbsolutePath_CorrectPath() {
        String absolutePath = resourceAbsolutePathProvider.getResourceAbsolutePath("DefaultResponses/404.html");

        assertNotEquals("", absolutePath);
    }

    @Test
    public void getResourceAbsolutePath_IncorrectPath() {
        assertEquals("", resourceAbsolutePathProvider.getResourceAbsolutePath("/DefaultResponses/404.html/"));
        assertEquals("", resourceAbsolutePathProvider.getResourceAbsolutePath("DefaultResponses/123.html"));
        assertEquals("", resourceAbsolutePathProvider.getResourceAbsolutePath("abcd"));
        assertEquals("", resourceAbsolutePathProvider.getResourceAbsolutePath("DefaultResponses/^IXIC"));
    }
}
