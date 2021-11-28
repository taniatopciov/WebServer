package io;

import java.io.FileWriter;
import java.io.IOException;

public class FileContentWriterImpl implements FileContentWriter {

    @Override
    public void writeContentToFile(String filePath, String fileContent) {
        try (
                FileWriter fileWriter = new FileWriter(filePath);
        ) {
            fileWriter.write(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
