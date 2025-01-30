package diligentpenguin.task;

import java.time.format.DateTimeFormatter;

/**
 * Represents a task given by the user.
 * A <code>Task</code> object has a name, a completion status and a type
 */
public class Task {
    public static DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
    String name = "";
    String type = "";
    Boolean isDone = false;

    public Task(String name) {
        this.name = name;
    }

    /**
     * Set the task as completed
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Set the task as uncompleted
     */
    public void setUnDone() {
        this.isDone = false;
    }

    /**
     * @return a string represents the type of the task
     */
    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    /**
     * @return formatted string for printing purposes
     */
    @Override
    public String toString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("[%s] %s", mark, this.name);
    }

    /**
     * @return a string formatted for saving purpose
     */
    public String toSavedString() {
        return "";
    }
}
