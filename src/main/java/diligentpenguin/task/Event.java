package diligentpenguin.task;

import java.time.LocalDate;

/**
 * Represent a task with specific start time and end time.
 * In addition to <code>Task</code> object's attributes, a <code>Event</code> object has
 * a start time and end time.
 */
public class Event extends Task {
    LocalDate startTime;
    LocalDate endTime;

    public Event(String name, LocalDate startTime, LocalDate endTime) {
        super(name);
        this.type = "E";
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("[%s][%s] %s (from: %s to: %s)",
                this.type, mark, this.name,
                this.startTime.format(Event.outputFormatter),
                this.endTime.format(Event.outputFormatter));
    }

    @Override
    public String toSavedString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("%s | %s | %s | %s | %s",
                this.type, mark, this.name,
                this.startTime.format(Event.inputFormatter),
                this.endTime.format(Event.inputFormatter));
    }
}
