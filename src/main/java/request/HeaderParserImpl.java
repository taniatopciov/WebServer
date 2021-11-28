package request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeaderParserImpl implements HeaderParser {

    public Map<String, String> parseHeaders(List<String> headersList) {
        if (headersList == null || headersList.isEmpty()) {
            return new HashMap<>();
        }

        int index = -1;
        HashMap<String, String> headersMap = new HashMap<>();

        for (String header : headersList) {
            if (header == null || header.isEmpty()) {
                continue;
            }

            index = header.indexOf(':');
            if (index == -1) {
                continue;
            }

            headersMap.put(header.substring(0, index).trim(), header.substring(index + 1).trim());
        }

        return headersMap;
    }
}
