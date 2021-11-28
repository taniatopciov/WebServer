package response;

import org.apache.commons.httpclient.HttpStatus;

public class ResponseStatusLineBuilderImpl implements ResponseStatusLineBuilder {

    public String buildResponseStatusLine(ResponseStatusLine responseStatusLine) {
        StringBuilder response = new StringBuilder();

        response.append(responseStatusLine.getVersion());
        response.append(" ");

        response.append(responseStatusLine.getStatusCode());
        response.append(" ");

        response.append(HttpStatus.getStatusText(responseStatusLine.getStatusCode()));
        response.append("\r\n");

        return response.toString();
    }
}
