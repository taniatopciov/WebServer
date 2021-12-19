package resource_manager;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class ResourceAbsolutePathProviderImpl implements ResourceAbsolutePathProvider {

    public String getResourceAbsolutePath(String simplePath) {
        URL resourceURL = getClass().getClassLoader().getResource(simplePath);
        if (resourceURL != null) {
            try {
                if (resourceURL.toURI().toString().contains("!")) {
                    return simplePath;
                } else {
                    return Paths.get(resourceURL.toURI()).toFile().getAbsolutePath();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
