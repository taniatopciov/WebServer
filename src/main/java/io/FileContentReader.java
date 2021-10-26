package io;

import java.io.IOException;

public interface FileContentReader {

    public String readFileContent(String filePath) throws IOException;
}
