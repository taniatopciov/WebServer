package response;

import java.io.IOException;
import java.util.HashMap;

public interface ResponseByteArrayProvider {

    public byte[] getResponseBytes(ResponseStatusLine responseStatusLine, HashMap<String, String> headers, String pathToBody) throws IOException;
}
