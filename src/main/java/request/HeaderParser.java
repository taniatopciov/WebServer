package request;

import java.util.ArrayList;
import java.util.HashMap;

public interface HeaderParser {

    public HashMap<String, String> parseHeaders(ArrayList<String> headersList);
}
