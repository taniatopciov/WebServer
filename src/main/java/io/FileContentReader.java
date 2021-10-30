package io;

import java.io.IOException;

public interface FileContentReader {

    public byte[] readFileContent(String filePath) throws IOException;
}
