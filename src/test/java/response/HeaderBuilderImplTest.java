package response;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class HeaderBuilderImplTest {

    private HeaderBuilderImpl headerBuilderImpl;

    @Before
    public void setUp() throws Exception {
        headerBuilderImpl = new HeaderBuilderImpl();
    }

    @Test
    public void buildHeaders_NullHeadersMap() {
        String headers = headerBuilderImpl.buildHeaders(null);

        assertEquals("\r\n", headers);
    }

    @Test
    public void buildHeaders_EmptyHeadersMap() {
        String headers = headerBuilderImpl.buildHeaders(new HashMap<>());

        assertEquals("\r\n", headers);
    }

    @Test
    public void buildHeaders_WithHeaders_ThreeElements() {
        HashMap<String, String> map = new HashMap<>();

        map.put("Location", "http://www.google.com/");
        map.put("Expires", "Fri, 24 Apr 2020 18:53:12 GMT");
        map.put("Server", "gws");

        String headers = headerBuilderImpl.buildHeaders(map);

        assertEquals("Server: gws\r\nExpires: Fri, 24 Apr 2020 18:53:12 GMT\r\nLocation: http://www.google.com/\r\n\r\n", headers);
    }

    @Test
    public void buildHeaders_WithHeaders_SameElementMultipleTimes() {
        HashMap<String, String> map = new HashMap<>();

        map.put("Location", "http://www.google.com/");
        map.put("Location", "http://www.google.com/");
        map.put("Expires", "Fri, 24 Apr 2020 18:53:12 GMT");

        String headers = headerBuilderImpl.buildHeaders(map);

        assertEquals("Expires: Fri, 24 Apr 2020 18:53:12 GMT\r\nLocation: http://www.google.com/\r\n\r\n", headers);
    }
}