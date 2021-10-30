package response;

import java.util.HashMap;

public interface HeaderBuilder {
    public String buildHeaders(HashMap<String, String> headers);
}
