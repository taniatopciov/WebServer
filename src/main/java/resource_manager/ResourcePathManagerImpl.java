package resource_manager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class ResourcePathManagerImpl implements ResourcePathManager {

    private final ResourceAbsolutePathProvider resourceAbsolutePathProvider;
    private final ResourceFileManager resourceFileManager;

    private static final String PATH_DELIMITER = "/";
    private static final String DEFAULT_PATH = "index.html";

    public ResourcePathManagerImpl(ResourceAbsolutePathProvider resourceAbsolutePathProvider, ResourceFileManager resourceFileManager) {
        this.resourceAbsolutePathProvider = resourceAbsolutePathProvider;
        this.resourceFileManager = resourceFileManager;
    }

    public String getResourcePath(String rootFolder, String path) {
        if (path == null || path.isEmpty() || path.equals("/")) {
            path = PATH_DELIMITER + DEFAULT_PATH;
        }

        char lastCharOfRootFolder = rootFolder.charAt(rootFolder.length() - 1);
        char firstCharOfPath = path.charAt(0);

        String resourcePath;

        if (lastCharOfRootFolder == '/' && firstCharOfPath == '/') {
            resourcePath = rootFolder.substring(0, rootFolder.length() - 1) + path;
        } else if (lastCharOfRootFolder != '/' && firstCharOfPath != '/') {
            resourcePath = rootFolder + PATH_DELIMITER + path;
        } else {
            resourcePath = rootFolder + path;
        }

        try {
            resourcePath = URLDecoder.decode(resourcePath, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!resourceFileManager.checkFileExists(resourcePath)) {

            return resourceAbsolutePathProvider.getResourceAbsolutePath("DefaultResponses/404.html");
        }

        return resourcePath;
    }
}
