package diligentpenguin.command;

import java.time.LocalDate;

/**
 * Represents deadline command, contains information about the command.
 */
public class DeadlineCommand extends Command {
    private final String description;
    private final LocalDate deadline;

    /**
     * Constructs a deadline command object.
     *
     * @param description Name of the deadline.
     * @param deadline Deadline time.
     */
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
