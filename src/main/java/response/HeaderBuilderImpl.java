package response;

import java.util.Map;

public class HeaderBuilderImpl implements HeaderBuilder {

    public String buildHeaders(Map<String, String> headers) {
        if (headers == null || headers.isEmpty()) {
            return "\r\n";
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (var entry : headers.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(": ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\r\n");
        }

        stringBuilder.append("\r\n");

        return stringBuilder.toString();
    }
}
