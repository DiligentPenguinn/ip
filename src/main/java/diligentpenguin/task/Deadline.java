package diligentpenguin.task;

import java.time.LocalDate;

public class Deadline extends Task {
    LocalDate deadline;

    public Deadline(String name, LocalDate deadline) {
        super(name);
        this.type = "D";
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("[%s][%s] %s (by: %s)",
                this.type, mark, this.name, this.deadline.format(Deadline.outputFormatter));
    }

    @Override
    public String toSavedString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("%s | %s | %s | %s",
                this.type, mark, this.name, this.deadline.format(Deadline.inputFormatter));
    }
}
