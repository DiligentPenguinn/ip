package diligentpenguin.task;

import java.time.format.DateTimeFormatter;

/**
 * Represents a task given by the user.
 * A <code>Task</code> object has a name, a completion status and a type
 */
public class Task {
    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
    private String name = "";
    private String type = "";
    private Boolean isDone = false;

    /**
     * Constructs a new <code>Task</code> object with the specified name and type.
     * @param name The name of the task
     * @param type The type of the task
     */
    public Task(String name, String type) {
        this.name = name;
        this.type = type;
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

    public boolean isDone() {
        return this.isDone;
    }

    public static DateTimeFormatter getInputFormatter() {
        return inputFormatter;
    }

    public static DateTimeFormatter getOutputFormatter() {
        return outputFormatter;
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
