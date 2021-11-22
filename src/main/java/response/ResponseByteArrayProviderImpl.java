package response;

import io.FileContentReaderImpl;
import org.apache.commons.httpclient.HttpStatus;
import resource_manager.ResourceAbsolutePathProviderImpl;

import java.io.IOException;
import java.util.HashMap;

public class ResponseByteArrayProviderImpl implements ResponseByteArrayProvider {
    @Override
    public byte[] getResponseBytes(ResponseStatusLine responseStatusLine, HashMap<String, String> headers, String pathToBody) throws IOException {
        if (responseStatusLine == null || headers == null || headers.isEmpty() || pathToBody == null || pathToBody.isEmpty()) {
            responseStatusLine = new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_INTERNAL_SERVER_ERROR);

            headers = new HashMap<>();
            headers.put("Content-Type", "text/html; charset=UTF-8");

            pathToBody = new ResourceAbsolutePathProviderImpl().getResourceAbsolutePath("DefaultResponses/500.html");
        }

        ResponseStatusLineBuilder responseStatusLineBuilder = new ResponseStatusLineBuilderImpl();
        HeaderBuilder headerBuilder = new HeaderBuilderImpl();

        String responseLine = responseStatusLineBuilder.buildResponseStatusLine(responseStatusLine);
        String responseHeaders = headerBuilder.buildHeaders(headers);

        byte[] responseLineBytes = responseLine.getBytes();
        byte[] responseHeadersBytes = responseHeaders.getBytes();
        byte[] bodyContentBytes = new FileContentReaderImpl().readFileContent(pathToBody);

        byte[] responseBytes = new byte[responseLineBytes.length + responseHeadersBytes.length + bodyContentBytes.length];

        System.arraycopy(responseLineBytes, 0, responseBytes, 0, responseLineBytes.length);
        System.arraycopy(responseHeadersBytes, 0, responseBytes, responseLineBytes.length, responseHeadersBytes.length);
        System.arraycopy(bodyContentBytes, 0, responseBytes, responseLineBytes.length + responseHeadersBytes.length, bodyContentBytes.length);

        return responseBytes;
    }
}
