package diligentpenguin.command;

import java.time.LocalDate;

public class DeadlineCommand extends Command {
    private final String description;
    private final LocalDate deadline;

    public DeadlineCommand(String description, LocalDate deadline) {
        this.description = description;
        this.deadline = deadline;
    }
    public static String getCommandInfo() {
        return "This command adds a new Deadline task onto the list"
                + "\nFormat: deadline <description> /by <deadline>";
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }
}
