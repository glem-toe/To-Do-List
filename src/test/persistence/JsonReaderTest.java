package persistence;

import model.ToDoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ToDoList tdl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyToDoList.json");
        try {
            ToDoList tdl = reader.read();
            assertEquals(0, tdl.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralToDoList.json");
        try {
            ToDoList tdl = reader.read();
            assertEquals(2, tdl.getSize());
            checkTask("brush teeth", true, tdl.getTask(0));
            checkTask("sleep", false, tdl.getTask(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
