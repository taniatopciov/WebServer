package response;

import org.apache.commons.httpclient.HttpStatus;

public class ResponseStatusLine {

    public String version;
    public int statusCode;

    public ResponseStatusLine(String version, int statusCode) {
        this.version = version;
        this.statusCode = statusCode;
    }
}
