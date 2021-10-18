package io_file_manager;

import exceptions.EmptyFileException;
import exceptions.InvalidFileTypeException;

import java.io.FileNotFoundException;

public interface IJsonParser {

    public <T> T parseFile(String filePath, Class<T> type) throws InvalidFileTypeException, EmptyFileException, FileNotFoundException;
}

