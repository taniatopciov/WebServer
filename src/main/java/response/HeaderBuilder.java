package response;

import java.util.Map;

public interface HeaderBuilder {
    public String buildHeaders(Map<String, String> headers);
}
