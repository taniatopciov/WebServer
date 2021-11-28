package response;

import java.io.IOException;
import java.util.Map;

public interface ResponseByteArrayProvider {

    public byte[] getResponseBytes(ResponseStatusLine responseStatusLine, Map<String, String> headers, String pathToBody) throws IOException;
}
