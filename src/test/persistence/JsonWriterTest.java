package persistence;

import model.Task;
import model.ToDoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ToDoList tdl = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyToDoList() {
        try {
            ToDoList tdl = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyToDoList.json");
            writer.open();
            writer.write(tdl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyToDoList.json");
            tdl = reader.read();
            assertEquals(0, tdl.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralToDoList() {
        try {
            ToDoList tdl = new ToDoList();
            Task testTask = new Task("brush teeth");
            testTask.markAsComplete();
            tdl.addTask(testTask);
            tdl.addNewTask("sleep");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralToDoList.json");
            writer.open();
            writer.write(tdl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralToDoList.json");
            tdl = reader.read();
            assertEquals(2, tdl.getSize());
            checkTask("brush teeth", true, tdl.getTask(0));
            checkTask("sleep", false, tdl.getTask(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
