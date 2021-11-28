package request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RequestStartLineParserTest {

    private RequestStartLineParser requestStartLineParser;

    @Before
    public void setUp() {
        requestStartLineParser = new RequestStartLineParser();
    }

    @Test
    public void parseRequest_invalidRequest() {
        assertNull(requestStartLineParser.parseRequestStartLine(""));
        assertNull(requestStartLineParser.parseRequestStartLine(null));
        assertNull(requestStartLineParser.parseRequestStartLine(" "));
        assertNull(requestStartLineParser.parseRequestStartLine("GET"));
        assertNull(requestStartLineParser.parseRequestStartLine("GET /this/resource"));
        assertNull(requestStartLineParser.parseRequestStartLine("GET /this/resource HTTP1.1 extraArgument"));
    }

    @Test
    public void parseRequest_validRequest() {
        RequestStartLine requestStartLine = requestStartLineParser.parseRequestStartLine("GET /this/resource HTTP/1.1");
        assertEquals("GET", requestStartLine.getMethod());
        assertEquals("/this/resource", requestStartLine.getTarget());
        assertEquals("HTTP/1.1", requestStartLine.getVersion());
    }
}