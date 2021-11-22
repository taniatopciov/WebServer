package response;

import org.apache.commons.httpclient.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import resource_manager.ResourceAbsolutePathProviderImpl;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ResponseByteArrayProviderImplTest {

    private ResponseByteArrayProviderImpl responseByteArrayProvider;

    @Before
    public void setUp() {
        responseByteArrayProvider = new ResponseByteArrayProviderImpl();
    }

    @Test
    public void getResponseBytes_CorrectParameters() throws IOException {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine("HTTP/1.1", 200);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Location", "http://www.google.com/");
        headers.put("Expires", "Fri, 24 Apr 2020 18:53:12 GMT");
        headers.put("Server", "gws");

        String pathToBody = new ResourceAbsolutePathProviderImpl().getResourceAbsolutePath("DefaultResponses/200.html");

        byte[] responseBytes = responseByteArrayProvider.getResponseBytes(responseStatusLine, headers, pathToBody);

        StringBuilder stringBuilder = new StringBuilder();
        ResponseStatusLineBuilder responseStatusLineBuilder = new ResponseStatusLineBuilderImpl();
        HeaderBuilder headerBuilder = new HeaderBuilderImpl();

        stringBuilder.append(responseStatusLineBuilder.buildResponseStatusLine(responseStatusLine));
        stringBuilder.append(headerBuilder.buildHeaders(headers));

        String content = Files.readString(Path.of(pathToBody), StandardCharsets.US_ASCII);
        stringBuilder.append(content);

        byte[] expected = stringBuilder.toString().getBytes();

        assertArrayEquals(expected, responseBytes);
    }

    @Test(expected = IOException.class)
    public void getResponseBytes_ThrowsException() throws IOException {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine("HTTP/1.1", 200);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Server", "gws");

        String pathToBody = "randomPath";

        byte[] responseBytes = responseByteArrayProvider.getResponseBytes(responseStatusLine, headers, pathToBody);
    }

    @Test
    public void getResponseBytes_NullResponseStatusLine() throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Location", "http://www.google.com/");
        headers.put("Server", "gws");

        String pathToBody = new ResourceAbsolutePathProviderImpl().getResourceAbsolutePath("DefaultResponses/200.html");

        byte[] responseBytes = responseByteArrayProvider.getResponseBytes(null, headers, pathToBody);

        StringBuilder stringBuilder = new StringBuilder();
        ResponseStatusLineBuilder responseStatusLineBuilder = new ResponseStatusLineBuilderImpl();
        HeaderBuilder headerBuilder = new HeaderBuilderImpl();

        HashMap<String, String> headers2 = new HashMap<>();
        headers2.put("Content-Type", "text/html; charset=UTF-8");

        stringBuilder.append(responseStatusLineBuilder.buildResponseStatusLine(new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_INTERNAL_SERVER_ERROR)));
        stringBuilder.append(headerBuilder.buildHeaders(headers2));

        String content = Files.readString(Path.of(new ResourceAbsolutePathProviderImpl().getResourceAbsolutePath("DefaultResponses/500.html")), StandardCharsets.US_ASCII);
        stringBuilder.append(content);

        byte[] expected = stringBuilder.toString().getBytes();

        assertArrayEquals(expected, responseBytes);
    }

    @Test
    public void getResponseBytes_EmptyHeaders() throws IOException {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine("HTTP/1.1", 200);
        HashMap<String, String> headers = new HashMap<>();
        String pathToBody = new ResourceAbsolutePathProviderImpl().getResourceAbsolutePath("DefaultResponses/200.html");

        byte[] responseBytes = responseByteArrayProvider.getResponseBytes(responseStatusLine, headers, pathToBody);

        StringBuilder stringBuilder = new StringBuilder();
        ResponseStatusLineBuilder responseStatusLineBuilder = new ResponseStatusLineBuilderImpl();
        HeaderBuilder headerBuilder = new HeaderBuilderImpl();

        HashMap<String, String> headers2 = new HashMap<>();
        headers2.put("Content-Type", "text/html; charset=UTF-8");

        stringBuilder.append(responseStatusLineBuilder.buildResponseStatusLine(new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_INTERNAL_SERVER_ERROR)));
        stringBuilder.append(headerBuilder.buildHeaders(headers2));

        String content = Files.readString(Path.of(new ResourceAbsolutePathProviderImpl().getResourceAbsolutePath("DefaultResponses/500.html")), StandardCharsets.US_ASCII);
        stringBuilder.append(content);

        byte[] expected = stringBuilder.toString().getBytes();

        assertArrayEquals(expected, responseBytes);
    }

    @Test
    public void getResponseBytes_NullHeaders() throws IOException {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine("HTTP/1.1", 200);
        String pathToBody = new ResourceAbsolutePathProviderImpl().getResourceAbsolutePath("DefaultResponses/200.html");

        byte[] responseBytes = responseByteArrayProvider.getResponseBytes(responseStatusLine, null, pathToBody);

        StringBuilder stringBuilder = new StringBuilder();
        ResponseStatusLineBuilder responseStatusLineBuilder = new ResponseStatusLineBuilderImpl();
        HeaderBuilder headerBuilder = new HeaderBuilderImpl();

        HashMap<String, String> headers2 = new HashMap<>();
        headers2.put("Content-Type", "text/html; charset=UTF-8");

        stringBuilder.append(responseStatusLineBuilder.buildResponseStatusLine(new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_INTERNAL_SERVER_ERROR)));
        stringBuilder.append(headerBuilder.buildHeaders(headers2));

        String content = Files.readString(Path.of(new ResourceAbsolutePathProviderImpl().getResourceAbsolutePath("DefaultResponses/500.html")), StandardCharsets.US_ASCII);
        stringBuilder.append(content);

        byte[] expected = stringBuilder.toString().getBytes();

        assertArrayEquals(expected, responseBytes);
    }

    @Test
    public void getResponseBytes_PathToBodyNull() throws IOException {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine("HTTP/1.1", 200);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Server", "gws");
        headers.put("Expires", "Fri, 24 Apr 2020 18:53:12 GMT");

        String pathToBody = new ResourceAbsolutePathProviderImpl().getResourceAbsolutePath("DefaultResponses/404.html");

        byte[] responseBytes = responseByteArrayProvider.getResponseBytes(responseStatusLine, headers, null);

        StringBuilder stringBuilder = new StringBuilder();
        ResponseStatusLineBuilder responseStatusLineBuilder = new ResponseStatusLineBuilderImpl();
        HeaderBuilder headerBuilder = new HeaderBuilderImpl();

        HashMap<String, String> headers2 = new HashMap<>();
        headers2.put("Content-Type", "text/html; charset=UTF-8");

        stringBuilder.append(responseStatusLineBuilder.buildResponseStatusLine(new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_INTERNAL_SERVER_ERROR)));
        stringBuilder.append(headerBuilder.buildHeaders(headers2));

        String content = Files.readString(Path.of(new ResourceAbsolutePathProviderImpl().getResourceAbsolutePath("DefaultResponses/500.html")), StandardCharsets.US_ASCII);
        stringBuilder.append(content);

        byte[] expected = stringBuilder.toString().getBytes();

        assertArrayEquals(expected, responseBytes);
    }

    @Test
    public void getResponseBytes_PathToBodyEmpty() throws IOException {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine("HTTP/1.1", 200);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Server", "gws");

        String pathToBody = new ResourceAbsolutePathProviderImpl().getResourceAbsolutePath("DefaultResponses/400.html");

        byte[] responseBytes = responseByteArrayProvider.getResponseBytes(responseStatusLine, headers, "");

        StringBuilder stringBuilder = new StringBuilder();
        ResponseStatusLineBuilder responseStatusLineBuilder = new ResponseStatusLineBuilderImpl();
        HeaderBuilder headerBuilder = new HeaderBuilderImpl();

        HashMap<String, String> headers2 = new HashMap<>();
        headers2.put("Content-Type", "text/html; charset=UTF-8");

        stringBuilder.append(responseStatusLineBuilder.buildResponseStatusLine(new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_INTERNAL_SERVER_ERROR)));
        stringBuilder.append(headerBuilder.buildHeaders(headers2));

        String content = Files.readString(Path.of(new ResourceAbsolutePathProviderImpl().getResourceAbsolutePath("DefaultResponses/500.html")), StandardCharsets.US_ASCII);
        stringBuilder.append(content);

        byte[] expected = stringBuilder.toString().getBytes();

        assertArrayEquals(expected, responseBytes);
    }

    @Test
    public void getResponseBytes_NullOrEmptyParameters() throws IOException {
        byte[] responseBytes = responseByteArrayProvider.getResponseBytes(null, new HashMap<String, String>(), "");

        StringBuilder stringBuilder = new StringBuilder();
        ResponseStatusLineBuilder responseStatusLineBuilder = new ResponseStatusLineBuilderImpl();
        HeaderBuilder headerBuilder = new HeaderBuilderImpl();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html; charset=UTF-8");

        stringBuilder.append(responseStatusLineBuilder.buildResponseStatusLine(new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_INTERNAL_SERVER_ERROR)));
        stringBuilder.append(headerBuilder.buildHeaders(headers));

        String content = Files.readString(Path.of(new ResourceAbsolutePathProviderImpl().getResourceAbsolutePath("DefaultResponses/500.html")), StandardCharsets.US_ASCII);
        stringBuilder.append(content);

        byte[] expected = stringBuilder.toString().getBytes();

        assertArrayEquals(expected, responseBytes);
    }
}