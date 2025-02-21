package diligentpenguin.task;

import java.time.LocalDate;

/**
 * Represents a task with specific start time and end time.
 * In addition to <code>Task</code> object's attributes, a <code>Event</code> object has
 * a start time and end time.
 */
public class Event extends Task {
    private LocalDate startTime;
    private LocalDate endTime;

    /**
     * Constructs a new <code>Event</code> task with the specified name, starting and ending time.
     *
     * @param name The name of the event.
     * @param startTime The event's starting time.
     * @param endTime The event's ending time.
     */
    public Event(String name, LocalDate startTime, LocalDate endTime) {
        super(name, "E");
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        String mark = this.isDone() ? "X" : " ";
        return String.format("[%s][%s] %s (from: %s to: %s)",
                this.getType(), mark, this.getName(),
                this.startTime.format(Task.getOutputFormatter()),
                this.endTime.format(Task.getOutputFormatter()));
    }

    @Override
    public String toSavedString() {
        String mark = this.isDone() ? "X" : " ";
        return String.format("%s | %s | %s | %s | %s",
                this.getType(), mark, this.getName(),
                this.startTime.format(Task.getInputFormatter()),
                this.endTime.format(Task.getInputFormatter()));
    }

    @Override
    public String toEditString(int index) {
        return String.format("update-%d %s /from %s /to %s",
                index,
                this.getName(),
                this.startTime.format(Task.getInputFormatter()),
                this.endTime.format(Task.getInputFormatter()));
    }
}
