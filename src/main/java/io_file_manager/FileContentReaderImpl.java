package io_file_manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileContentReaderImpl implements FileContentReader {

    @Override
    public String readFileContent(String filePath) throws IOException {

        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();

        while (line != null) {
            stringBuilder.append(line);
            stringBuilder.append(System.lineSeparator());
            line = bufferedReader.readLine();
        }

        return stringBuilder.toString();
    }
}
