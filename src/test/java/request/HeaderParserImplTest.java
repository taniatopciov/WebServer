package request;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HeaderParserImplTest {

    private HeaderParserImpl headerParser;

    @Before
    public void setUp() {
        headerParser = new HeaderParserImpl();
    }

    @Test
    public void parseHeaders_nullHeadersArray() {
        Map<String, String> headersMap = headerParser.parseHeaders(null);
        assertEquals(0, headersMap.size());
    }

    @Test
    public void parseHeaders_emptyHeadersArray() {
        Map<String, String> headersMap = headerParser.parseHeaders(new ArrayList<String>());
        assertEquals(0, headersMap.size());
    }

    @Test
    public void parseHeaders_nullHeaderLine() {
        ArrayList<String> headersList = new ArrayList<>();
        headersList.add("Accept-Language: en-US,en;q=0.5");
        headersList.add(null);
        headersList.add("Date: Wed, 25 Mar 2020 18:53:12 GMT");

        Map<String, String> headersMap = headerParser.parseHeaders(headersList);
        assertEquals(2, headersMap.size());
        assertEquals("en-US,en;q=0.5", headersMap.get("Accept-Language"));
        assertEquals("Wed, 25 Mar 2020 18:53:12 GMT", headersMap.get("Date"));
    }

    @Test
    public void parseHeaders_emptyHeaderLine() {
        ArrayList<String> headersList = new ArrayList<>();
        headersList.add("");
        headersList.add("Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        headersList.add("Host: code.tutsplus.com");

        Map<String, String> headersMap = headerParser.parseHeaders(headersList);
        assertEquals(2, headersMap.size());
        assertEquals("ISO-8859-1,utf-8;q=0.7,*;q=0.7", headersMap.get("Accept-Charset"));
        assertEquals("code.tutsplus.com", headersMap.get("Host"));
    }

    @Test
    public void parseHeaders_missingDelimiterInHeaderLine() {
        ArrayList<String> headersList = new ArrayList<>();
        headersList.add("Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        headersList.add("Host code.tutsplus.com");

        Map<String, String> headersMap = headerParser.parseHeaders(headersList);
        assertEquals(1, headersMap.size());
        assertEquals("ISO-8859-1,utf-8;q=0.7,*;q=0.7", headersMap.get("Accept-Charset"));
    }

    @Test
    public void parseHeaders_multipleDelimitersInHeaderLine() {
        ArrayList<String> headersList = new ArrayList<>();
        headersList.add("Date: Wed, 25 Mar 2020 18:53:12 GMT");

        Map<String, String> headersMap = headerParser.parseHeaders(headersList);
        assertEquals(1, headersMap.size());
        assertEquals("Wed, 25 Mar 2020 18:53:12 GMT", headersMap.get("Date"));
    }

    @Test
    public void parseHeaders_oneDelimiterInHeaderLine() {
        ArrayList<String> headersList = new ArrayList<>();
        headersList.add("Host: code.tutsplus.com");

        Map<String, String> headersMap = headerParser.parseHeaders(headersList);
        assertEquals(1, headersMap.size());
        assertEquals("code.tutsplus.com", headersMap.get("Host"));
    }
}