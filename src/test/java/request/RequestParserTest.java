package request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RequestParserTest {

    private RequestParser requestParser;

    @Before
    public void setUp() {
        requestParser = new RequestParser();
    }

    @Test
    public void parseRequest_emptyRequest() {
        Request request = requestParser.parseRequest("");
        assertNull(request);
    }

    @Test
    public void parseRequest_nullRequest() {
        Request request = requestParser.parseRequest(null);
        assertNull(request);
    }

    @Test
    public void parseRequest_missingAllArguments() {
        Request request = requestParser.parseRequest(" ");
        assertNull(request);
    }

    @Test
    public void parseRequest_missingTwoArguments() {
        Request request = requestParser.parseRequest("GET");
        assertNull(request);
    }

    @Test
    public void parseRequest_missingOneArgument() {
        Request request = requestParser.parseRequest("GET /this/resource");
        assertNull(request);
    }

    @Test
    public void parseRequest_hasFourArguments() {
        Request request = requestParser.parseRequest("GET /this/resource HTTP1.1 extraArgument");
        assertNull(request);
    }

    @Test
    public void parseRequest_correct() {
        Request request = requestParser.parseRequest("GET /this/resource HTTP/1.1");
        assertEquals("GET", request.method);
        assertEquals("/this/resource", request.target);
        assertEquals("HTTP/1.1", request.version);
    }
}