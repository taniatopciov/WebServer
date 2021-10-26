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
    public void parseRequest_emptyRequest() {
        RequestStartLine requestStartLine = requestStartLineParser.parseRequestStartLine("");
        assertNull(requestStartLine);
    }

    @Test
    public void parseRequest_nullRequest() {
        RequestStartLine requestStartLine = requestStartLineParser.parseRequestStartLine(null);
        assertNull(requestStartLine);
    }

    @Test
    public void parseRequest_missingAllArguments() {
        RequestStartLine requestStartLine = requestStartLineParser.parseRequestStartLine(" ");
        assertNull(requestStartLine);
    }

    @Test
    public void parseRequest_missingTwoArguments() {
        RequestStartLine requestStartLine = requestStartLineParser.parseRequestStartLine("GET");
        assertNull(requestStartLine);
    }

    @Test
    public void parseRequest_missingOneArgument() {
        RequestStartLine requestStartLine = requestStartLineParser.parseRequestStartLine("GET /this/resource");
        assertNull(requestStartLine);
    }

    @Test
    public void parseRequest_hasFourArguments() {
        RequestStartLine requestStartLine = requestStartLineParser.parseRequestStartLine("GET /this/resource HTTP1.1 extraArgument");
        assertNull(requestStartLine);
    }

    @Test
    public void parseRequest_correct() {
        RequestStartLine requestStartLine = requestStartLineParser.parseRequestStartLine("GET /this/resource HTTP/1.1");
        assertEquals("GET", requestStartLine.method);
        assertEquals("/this/resource", requestStartLine.target);
        assertEquals("HTTP/1.1", requestStartLine.version);
    }
}