package response;

public class ResponseStatusLine {

    private String version;
    private int statusCode;

    public ResponseStatusLine(String version, int statusCode) {
        this.version = version;
        this.statusCode = statusCode;
    }

    public String getVersion() {
        return version;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
