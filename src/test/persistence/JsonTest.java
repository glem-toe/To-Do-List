package persistence;

import model.Task;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkTask(String name, Boolean status, Task task) {
        assertEquals(name, task.getName());
        assertEquals(status, task.getStatus());
    }
}
