package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests the Task class
class TaskTest {
    private Task testTask;

    @BeforeEach
    void runBefore() {
        testTask = new Task("Sleep");
    }

    @Test
    void testConstructor() {
        assertEquals("Sleep", testTask.getName());
        assertFalse(testTask.getStatus());
    }

    @Test
    void testMarkAsComplete() {
        assertTrue(testTask.markAsComplete());
        testTask.markAsComplete();
        assertTrue(testTask.markAsComplete());
    }

    @Test
    void testToString() {
        assertEquals("Sleep", testTask.toString());
        Task p = new Task(" ");
        assertEquals(" ", p.toString());
    }

}