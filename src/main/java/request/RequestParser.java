package request;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class RequestParser {

    public Request parseRequest(String request) {
        if (request == null || request.isEmpty()) {
            return null;
        }

        StringTokenizer tokenizer = new StringTokenizer(request);

        try {
            String method = tokenizer.nextToken().toUpperCase();
            String resource = tokenizer.nextToken().toLowerCase();
            String version = tokenizer.nextToken();

            if (tokenizer.hasMoreTokens()) {
                return null;
            }

            return new Request(method, resource, version);

        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
