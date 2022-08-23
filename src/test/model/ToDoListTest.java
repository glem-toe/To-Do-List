package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Tests the ToDoList class
public class ToDoListTest {
    private ToDoList testToDoList;

    @BeforeEach
    void runBefore() {
        testToDoList = new ToDoList();
    }

    @Test
    void addNewTaskTest() {
        testToDoList.addNewTask("Sleep");
        Task p = testToDoList.getTask(0);
        assertEquals("Sleep", p.getName());
        assertEquals(1, testToDoList.getSize());
        assertFalse(p.getStatus());
    }

    @Test
    void addTaskTest() {
        Task testTask = new Task("testing");
        testToDoList.addTask(testTask);
        Task p = testToDoList.getTask(0);
        assertEquals("testing", p.getName());
        assertEquals(1, testToDoList.getSize());
        assertFalse(p.getStatus());
    }

    @Test
    void removeTaskTest() {
        testToDoList.addNewTask("Brush Teeth");
        testToDoList.addNewTask("Sleep");
        testToDoList.removeTask(0);
        Task p = testToDoList.getTask(0);
        assertEquals("Sleep", p.getName());
        assertEquals(1, testToDoList.getSize());
        assertFalse(p.getStatus());
    }

    @Test
    void markAsCompleteTest() {
        testToDoList.addNewTask("Brush Teeth");
        testToDoList.addNewTask("Sleep");
        testToDoList.markAsComplete(0);
        Task p = testToDoList.getTask(0);
        Task q = testToDoList.getTask(1);
        assertEquals(2, testToDoList.getSize());
        assertTrue(p.getStatus());
        assertFalse(q.getStatus());
    }

    @Test
    void tasksToStringList() {
        testToDoList.addNewTask("Brush Teeth");
        testToDoList.addNewTask("Sleep");
        testToDoList.markAsComplete(1);
        ArrayList<String> testTaskList = testToDoList.tasksToStringList();
        String p = testTaskList.get(0);
        String q = testTaskList.get(1);
        assertEquals("0. Brush Teeth, N", p);
        assertEquals("1. Sleep, Y", q);
    }

    @Test
    void clearListTest() {
        assertEquals(0, testToDoList.clearList().size());
        testToDoList.addNewTask("Brush Teeth");
        testToDoList.addNewTask("Sleep");
        assertEquals(0, testToDoList.clearList().size());
    }
}