package diligentpenguin.task;

import java.time.LocalDate;

/**
 * Represents a task with a specific deadline.
 * A <code>Deadline</code> object has a due date in adition to <code>Task</code> object
 */
public class Deadline extends Task {
    private LocalDate deadline;

    /**
     * Constructs a new <code>Deadline</code> task with the specified name and due date.
     *
     * @param name The name or description of the task.
     * @param deadline The due date of the task as a <code>LocalDate</code> object.
     */
    public Deadline(String name, LocalDate deadline) {
        super(name, "D");
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        String mark = this.isDone() ? "X" : " ";
        return String.format("[%s][%s] %s (by: %s)",
                this.getType(), mark, this.getName(), this.deadline.format(Task.getOutputFormatter()));
    }

    @Override
    public String toSavedString() {
        String mark = this.isDone() ? "X" : " ";
        return String.format("%s | %s | %s | %s",
                this.getType(), mark, this.getName(), this.deadline.format(Task.getInputFormatter()));
    }

    @Override
    public String toEditString(int index) {
        return String.format("update-%d deadline %s /by %s",
                index,
                this.getName(),
                this.deadline.format(Task.getInputFormatter()));
    }
}
