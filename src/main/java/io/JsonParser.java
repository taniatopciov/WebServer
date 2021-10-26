package io;

import exceptions.EmptyFileException;
import exceptions.InvalidFileTypeException;

import java.io.FileNotFoundException;

public interface JsonParser {

    <T> T parseFile(String filePath, Class<T> type) throws InvalidFileTypeException, EmptyFileException, FileNotFoundException;
}

