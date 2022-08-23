package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a task having a name, and status (complete or incomplete)
public class Task implements Writable {
    private String name;        // name of task
    private boolean status;     // status of task

    /*
     * REQUIRES: taskName has a non-zero length
     * EFFECTS: name of task is set to taskName;
     *          initial status is set to false.
     */
    public Task(String taskName) {
        name = taskName;
        status = false;
    }

    public String getName() {
        return name;
    }

    public boolean getStatus() {
        return status;
    }

    /*
     * MODIFIES: this
     * EFFECTS: marks status as true (completed)
     *          status is returned
     */
    public boolean markAsComplete() {
        if (!this.getStatus()) {
            status = true;
            EventLog.getInstance().logEvent(new Event("Completed Task '" + this.getName() + "' in to-do list."));
        }
        return status;
    }

    // EFFECTS: constructs a new JSONObject and puts elements of task into it, returns the JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("status", status);
        return json;
    }

    // EFFECTS: returns the name that appears in the list panel of main frame
    @Override
    public String toString() {
        return name;
    }
}

