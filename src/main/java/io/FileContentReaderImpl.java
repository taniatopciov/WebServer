package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileContentReaderImpl implements FileContentReader {

    @Override
    public byte[] readFileContent(String filePath) throws IOException {
        return Files.readAllBytes(Path.of(filePath));
    }
}
