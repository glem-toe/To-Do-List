package model;

import java.util.Calendar;
import java.util.Date;


/**
 * Represents a to-do list app event.
 */
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    /**
     * Creates an event with the given description
     * * and the current date/time stamp.n
     *
     * @param description a description of the event
     */
    // EFFECTS: creates an event with the given description and the current time
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    /**
     * Gets the date of this event (includes time).
     *
     * @return the date of the event
     */
    // EFFECTS: returns the date
    public Date getDate() {
        return dateLogged;
    }

    /**
     * Gets the description of this event.
     *
     * @return the description of the event
     */
    // EFFECTS: returns the description
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns true if there is another object identical to the object called, returns false otherwise
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    // EFFECTS: returns a hash code of the object
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // EFFECTS: returns a string of what would be displayed when print event log is called
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
