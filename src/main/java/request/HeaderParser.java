package request;

import java.util.List;
import java.util.Map;

public interface HeaderParser {

    public Map<String, String> parseHeaders(List<String> headersList);
}
