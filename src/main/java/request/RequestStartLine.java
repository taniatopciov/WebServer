package request;

public class RequestStartLine {

    private String method;
    private String target;
    private String version;

    public RequestStartLine(String method, String target, String version) {
        this.method = method;
        this.target = target;
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public String getTarget() {
        return target;
    }

    public String getVersion() {
        return version;
    }
}
