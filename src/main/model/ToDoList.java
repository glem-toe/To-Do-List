package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents an arraylist of tasks
public class ToDoList implements Writable {
    private ArrayList<Task> tasks;

    // EFFECTS: constructs an empty list of tasks
    public ToDoList() {
        tasks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new task to the to-do list
    public void addNewTask(String taskName) {
        Task p = new Task(taskName);
        tasks.add(p);
    }

    // MODIFIES: this
    // EFFECTS: adds task to the to-do list
    public void addTask(Task task) {
        tasks.add(task);
        EventLog.getInstance().logEvent(new Event("Added Task '" + task.getName() + "' to to-do list."));
    }

    // REQUIRES: List is not empty
    // MODIFIES: this
    // EFFECTS: removes a task from the to-do list
    public void removeTask(int position) {
        String taskName = tasks.get(position).getName();
        tasks.remove(position);
        EventLog.getInstance().logEvent(new Event("Removed Task '" + taskName + "' from to-do list."));
    }

    // REQUIRES: List is not empty
    // MODIFIES: this, task
    // EFFECTS: marks the targeted task in to-do list as complete
    public void markAsComplete(int position) {
        Task t = tasks.get(position);
        t.markAsComplete();
    }

    // EFFECTS: gets the task at index i
    public Task getTask(int index) {
        return tasks.get(index);
    }

    // EFFECTS: gets the size of to-do list
    public int getSize() {
        return tasks.size();
    }

    // MODIFIES: this
    // EFFECTS: clears the to-do list
    public ArrayList<Task> clearList() {
        tasks.clear();
        EventLog.getInstance().logEvent(new Event("Cleared to-do list."));
        return tasks;
    }

    // EFFECTS: makes a list of strings corresponding to tasks, with index number, name of task, and status (Y/N)
    //          return the list of task descriptions
    public ArrayList<String> tasksToStringList() {
        ArrayList<String> taskView = new ArrayList<>();
        int index = 0;
        for (Task p : tasks) {
            String taskindex = Integer.toString(index);
            String taskname = p.getName();
            String status;
            if (p.getStatus()) {
                status = "Y";
            } else {
                status = "N";
            }
            String taskdesc = taskindex + ". " + taskname + ", " + status;
            taskView.add(taskdesc);
            index++;
        }
        return taskView;
    }

    // EFFECTS: constructs a new JSONObject and puts elements of tasks into it, returns the JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tasks", tasksToJson());
        return json;
    }

    // EFFECTS: return things in this todolist as a JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : tasks) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }


}
