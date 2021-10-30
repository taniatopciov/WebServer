package io;

import com.google.gson.Gson;
import exceptions.EmptyFileException;
import exceptions.InvalidFileTypeException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class JsonParserImpl implements JsonParser {

    private Gson gson = new Gson();
    private FileContentReader fileContentReader;

    public JsonParserImpl(FileContentReader fileContentReader) {
        this.fileContentReader = fileContentReader;
    }

    @Override
    public <T> T parseFile(String filePath, Class<T> type) throws InvalidFileTypeException, EmptyFileException, FileNotFoundException {
        try {

            byte[] fileContentBytes = fileContentReader.readFileContent(filePath);

            if (fileContentBytes == null) {
                throw new EmptyFileException(filePath);
            }

            String fileContent = new String(fileContentBytes);

            if (fileContent.isEmpty()) {
                throw new EmptyFileException(filePath);
            }

            return gson.fromJson(fileContent, type);
        } catch (IOException e) {
            throw new FileNotFoundException();
        } catch (EmptyFileException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidFileTypeException();
        }
    }
}
