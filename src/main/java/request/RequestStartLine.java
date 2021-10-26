package request;

public class RequestStartLine {

    public String method;
    public String target;
    public String version;

    public RequestStartLine(String method, String target, String version) {
        this.method = method;
        this.target = target;
        this.version = version;
    }
}
