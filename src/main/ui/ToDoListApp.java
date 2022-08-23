package ui;

import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// ToDoList application
public class ToDoListApp {
    private static final String JSON_STORE = "./data/todolist.json";
    private Task task;
    private ToDoList tasks;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the to do list application
    public ToDoListApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runToDoList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runToDoList() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddTask();
        } else if (command.equals("v")) {
            doViewTasks();
        } else if (command.equals("r")) {
            doRemoveTask();
        } else if (command.equals("c")) {
            doMarkAsComplete();
        } else if (command.equals("s")) {
            saveToDoList();
        } else if (command.equals("l")) {
            loadToDoList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes to do list
    private void init() {
        tasks = new ToDoList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a task");
        System.out.println("\tv -> view all tasks");
        System.out.println("\tr -> remove a task");
        System.out.println("\tc -> mark a task as complete");
        System.out.println("\ts -> save to do list to file");
        System.out.println("\tl -> load to do list from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds a task to to-do list
    private void doAddTask() {
        System.out.print("Enter the name of task to add: ");
        String taskName = input.next();

        if (taskName instanceof String) {
            tasks.addNewTask(taskName);
        } else {
            System.out.println("Name of task is not a string...\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: lists all tasks in to-do list
    private void doViewTasks() {
        ArrayList<String> taskViewList = tasks.tasksToStringList();
        for (String taskDesc : taskViewList) {
            System.out.println("\n" + taskDesc);
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes a task from to-do list
    private void doRemoveTask() {
        this.doViewTasks();
        System.out.print("Enter the Task number to remove: ");
        int index = input.nextInt();
        int indexPlusOne = index + 1;

        if (tasks.getSize() < indexPlusOne) {
            System.out.println("This task does not exist");
        } else if (index < 0) {
            System.out.println("This task does not exist");
        } else {
            tasks.removeTask(index);
        }
    }

    // MODIFIES: this
    // EFFECTS: marks a task in to-do list as complete
    private void doMarkAsComplete() {
        this.doViewTasks();
        System.out.print("Enter the Task number to mark it as complete: ");
        int index = input.nextInt();
        int indexPlusOne = index + 1;

        if (tasks.getSize() < indexPlusOne) {
            System.out.println("This task does not exist");
        } else if (index < 0) {
            System.out.println("This task does not exist");
        } else {
            tasks.markAsComplete(index);
        }
    }

    // EFFECTS: saves the todolist to file
    private void saveToDoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(tasks);
            jsonWriter.close();
            System.out.println("Saved To-Do List to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads todolist from file
    private void loadToDoList() {
        try {
            tasks = jsonReader.read();
            System.out.println("Loaded To Do List from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
