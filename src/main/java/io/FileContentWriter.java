package io;

import java.io.IOException;

public interface FileContentWriter {

    public void writeContentToFile(String filePath, String fileContent) throws IOException;
}
