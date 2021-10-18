package io_file_manager;

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
            String fileContent = fileContentReader.readFileContent(filePath);

            if (fileContent == null || fileContent.isEmpty()) {
                throw new EmptyFileException(filePath);
            }

            T t = gson.fromJson(fileContent, type);
            return t;
        } catch (IOException e) {
            throw new FileNotFoundException();
        } catch (EmptyFileException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidFileTypeException();
        }
    }
}
