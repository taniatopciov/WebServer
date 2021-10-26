package response;

import org.apache.commons.httpclient.HttpStatus;

public class ResponseStatusLineBuilder {

    public String buildResponseStatusLine(ResponseStatusLine responseStatusLine) {
        StringBuilder response = new StringBuilder();

        response.append(responseStatusLine.version);
        response.append(" ");

        response.append(responseStatusLine.statusCode);
        response.append(" ");

        response.append(HttpStatus.getStatusText(responseStatusLine.statusCode));
        response.append("\r\n");

        return response.toString();
    }
}
