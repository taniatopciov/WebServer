package response;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class HeaderBuilderImplTest {

    private HeaderBuilderImpl headerBuilder;

    @Before
    public void setUp() {
        headerBuilder = new HeaderBuilderImpl();
    }

    @Test
    public void buildHeaders_NullHeadersMap() {
        String headers = headerBuilder.buildHeaders(null);

        assertEquals("\r\n", headers);
    }

    @Test
    public void buildHeaders_EmptyHeadersMap() {
        String headers = headerBuilder.buildHeaders(new HashMap<>());

        assertEquals("\r\n", headers);
    }

    @Test
    public void buildHeaders_WithHeaders_ThreeElements() {
        HashMap<String, String> map = new HashMap<>();

        map.put("Location", "http://www.google.com/");
        map.put("Expires", "Fri, 24 Apr 2020 18:53:12 GMT");
        map.put("Server", "gws");

        String headers = headerBuilder.buildHeaders(map);

        assertEquals("Server: gws\r\nExpires: Fri, 24 Apr 2020 18:53:12 GMT\r\nLocation: http://www.google.com/\r\n\r\n", headers);
    }

    @Test
    public void buildHeaders_WithHeaders_SameElementMultipleTimes() {
        HashMap<String, String> map = new HashMap<>();

        map.put("Location", "http://www.google.com/");
        map.put("Location", "http://www.google.com/");
        map.put("Expires", "Fri, 24 Apr 2020 18:53:12 GMT");

        String headers = headerBuilder.buildHeaders(map);

        assertEquals("Expires: Fri, 24 Apr 2020 18:53:12 GMT\r\nLocation: http://www.google.com/\r\n\r\n", headers);
    }
}