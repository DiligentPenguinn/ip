package diligentpenguin.task;

import java.time.format.DateTimeFormatter;

/**
 * Represents a task given by the user.
 * A <code>Task</code> object has a name, a completion status and a type.
 */
public class Task {
    private static final String INPUT_DATE_TIME_STRING = "dd/MM/yyyy";
    private static final String OUTPUT_DATE_TIME_STRING = "EEEE, MMMM d, yyyy";
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern(INPUT_DATE_TIME_STRING);
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern(OUTPUT_DATE_TIME_STRING);
    private String name = "";
    private String type = "";
    private Boolean isDone = false;

    /**
     * Constructs a new <code>Task</code> object with the specified name and type.
     *
     * @param name The name of the task.
     * @param type The type of the task.
     */
    public Task(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Sets the task as completed.
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Sets the task as uncompleted.
     */
    public void setUnDone() {
        this.isDone = false;
    }

    /**
     * @return a string that represents the type of the task.
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
        return INPUT_FORMATTER;
    }

    public static DateTimeFormatter getOutputFormatter() {
        return OUTPUT_FORMATTER;
    }

    public static String getInputDateTimeString() {
        return INPUT_DATE_TIME_STRING;
    }

    public static String getOutputDateTimeString() {
        return OUTPUT_DATE_TIME_STRING;
    }

    /**
     * @return a formatted string for printing purposes.
     */
    @Override
    public String toString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("[%s] %s", mark, this.name);
    }

    /**
     * @return a string formatted for saving purposes.
     */
    public String toSavedString() {
        return "";
    }

    /**
     * @return a string formatted for editing purposes.
     */
    public String toEditString(int index) {
        return "";
    }
}
