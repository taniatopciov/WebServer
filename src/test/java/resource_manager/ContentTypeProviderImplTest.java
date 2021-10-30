package resource_manager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContentTypeProviderImplTest {

    private ContentTypeProvider contentTypeProvider;

    @Before
    public void setUp() {
        contentTypeProvider = new ContentTypeProviderImpl();
    }

    @Test
    public void getContentType_GifExtension() {
        String contentType = contentTypeProvider.getContentType("random.gif");

        assertEquals("image/gif", contentType);
    }

    @Test
    public void getContentType_PngExtension() {
        String contentType = contentTypeProvider.getContentType("picture.png");

        assertEquals("image/png", contentType);
    }

    @Test
    public void getContentType_TiffExtension() {
        String contentType = contentTypeProvider.getContentType("other.tiff");

        assertEquals("image/tiff", contentType);
    }

    @Test
    public void getContentType_JpegExtension() {
        String contentType = contentTypeProvider.getContentType("random.jpeg");

        assertEquals("image/jpeg", contentType);
    }

    @Test
    public void getContentType_JpgExtension() {
        String contentType = contentTypeProvider.getContentType("random.jpg");

        assertEquals("image/jpeg", contentType);
    }

    @Test
    public void getContentType_CSVExtension() {
        String contentType = contentTypeProvider.getContentType("random.csv");

        assertEquals("text/csv; charset=utf-8", contentType);
    }

    @Test
    public void getContentType_CSSExtension() {
        String contentType = contentTypeProvider.getContentType("random.css");

        assertEquals("text/css; charset=utf-8", contentType);
    }

    @Test
    public void getContentType_XMLExtension() {
        String contentType = contentTypeProvider.getContentType("random.xml");

        assertEquals("text/xml; charset=utf-8", contentType);
    }

    @Test
    public void getContentType_HtmlExtension() {
        String contentType = contentTypeProvider.getContentType("random.html");

        assertEquals("text/html; charset=utf-8", contentType);
    }

    @Test
    public void getContentType_HtmExtension() {
        String contentType = contentTypeProvider.getContentType("random.htm");

        assertEquals("text/html; charset=utf-8", contentType);
    }

    @Test
    public void getContentType_TxtExtension() {
        String contentType = contentTypeProvider.getContentType("random.txt");

        assertEquals("text/plain; charset=utf-8", contentType);
    }

    @Test
    public void getContentType_RandomExtension() {
        String contentType = contentTypeProvider.getContentType("random.abc");

        assertEquals("text/plain; charset=utf-8", contentType);
    }

    @Test
    public void getContentType_EmptyFileName() {
        String contentType = contentTypeProvider.getContentType("");

        assertEquals("text/plain; charset=utf-8", contentType);
    }

    @Test
    public void getContentType_NullFileName() {
        String contentType = contentTypeProvider.getContentType(null);

        assertEquals("text/plain; charset=utf-8", contentType);
    }

    @Test
    public void getContentType_NoDot() {
        String contentType = contentTypeProvider.getContentType("randomtxt");

        assertEquals("text/plain; charset=utf-8", contentType);
    }

    @Test
    public void getContentType_MultipleDot() {
        String contentType = contentTypeProvider.getContentType("random.text.html");

        assertEquals("text/html; charset=utf-8", contentType);
    }
}