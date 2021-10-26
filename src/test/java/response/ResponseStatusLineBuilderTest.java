package response;

import org.apache.commons.httpclient.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseStatusLineBuilderTest {

    private ResponseStatusLineBuilder responseStatusLineBuilder;

    @Before
    public void setUp() {
        responseStatusLineBuilder = new ResponseStatusLineBuilder();
    }

    @Test
    public void buildResponseStatusLine_SuccessfulResponse_200() {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine("HTTP/1.1", 200);

        String response = responseStatusLineBuilder.buildResponseStatusLine(responseStatusLine);

        assertEquals("HTTP/1.1 200 OK\r\n", response);
    }

    @Test
    public void buildResponseStatusLine_ClientErrorResponse_400() {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine("HTTP/1.1", 400);

        String response = responseStatusLineBuilder.buildResponseStatusLine(responseStatusLine);

        assertEquals("HTTP/1.1 400 Bad Request\r\n", response);
    }

    @Test
    public void buildResponseStatusLine_ClientErrorResponse_404() {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine("HTTP/1.1", 404);

        String response = responseStatusLineBuilder.buildResponseStatusLine(responseStatusLine);

        assertEquals("HTTP/1.1 404 Not Found\r\n", response);
    }

    @Test
    public void buildResponseStatusLine_ClientErrorResponse_405() {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine("HTTP/1.1", 405);

        String response = responseStatusLineBuilder.buildResponseStatusLine(responseStatusLine);

        assertEquals("HTTP/1.1 405 Method Not Allowed\r\n", response);
    }

    @Test
    public void buildResponseStatusLine_ServerErrorResponse_500() {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine("HTTP/1.1", 500);

        String response = responseStatusLineBuilder.buildResponseStatusLine(responseStatusLine);

        assertEquals("HTTP/1.1 500 Internal Server Error\r\n", response);
    }
}