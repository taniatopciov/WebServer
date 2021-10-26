package io;

import exceptions.EmptyFileException;
import exceptions.InvalidFileTypeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JsonParserImplTest {

    @Mock
    private FileContentReader fileContentReader;

    private JsonParser jsonParser;

    @Before
    public void setUp() {
        jsonParser = new JsonParserImpl(fileContentReader);
    }

    @Test(expected = EmptyFileException.class)
    public void parseFile_nullFileContent() throws Exception {
        when(fileContentReader.readFileContent("myfile.json")).thenReturn(null);

        jsonParser.parseFile("myfile.json", Object.class);
    }

    @Test(expected = EmptyFileException.class)
    public void parseFile_emptyFileContent() throws Exception {
        when(fileContentReader.readFileContent("myfile.json")).thenReturn("");

        jsonParser.parseFile("myfile.json", Object.class);
    }

    @Test(expected = InvalidFileTypeException.class)
    public void parseFile_invalidFileType() throws Exception {
        when(fileContentReader.readFileContent("myfile.txt")).thenReturn("niste text" + '\n' + "foarte random");

        jsonParser.parseFile("myfile.txt", Object.class);
    }

    @Test(expected = FileNotFoundException.class)
    public void parseFile_fileNotFound() throws Exception {
        when(fileContentReader.readFileContent("myfile.txt")).thenThrow(new FileNotFoundException());

        jsonParser.parseFile("myfile.txt", Object.class);
    }

    @Test
    public void parseFile_works() throws Exception {
        when(fileContentReader.readFileContent("myfile.json")).thenReturn("{\"myString\": \"John\", \"myInt\": 5}");

        MyClass myClass = jsonParser.parseFile("myfile.json", MyClass.class);

        assertEquals("John", myClass.getMyString());
        assertEquals(5, myClass.getMyInt());
    }

    private class MyClass {
        private String myString;
        private int myInt;

        public String getMyString() {
            return myString;
        }

        public int getMyInt() {
            return myInt;
        }
    }
}