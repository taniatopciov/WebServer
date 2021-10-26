package request;

public class Request {

    public String method;
    public String target;
    public String version;

    public Request(String method, String target, String version) {
        this.method = method;
        this.target = target;
        this.version = version;
    }
}
