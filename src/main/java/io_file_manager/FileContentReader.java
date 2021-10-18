package io_file_manager;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileContentReader {

    public String readFileContent(String filePath) throws IOException;
}
