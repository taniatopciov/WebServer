package resource_manager;

import java.io.File;

public class ResourceFileManagerImpl implements ResourceFileManager {
    @Override
    public boolean checkFileExists(String filePath) {
        File file = new File(filePath);

        return file.exists();
    }
}
