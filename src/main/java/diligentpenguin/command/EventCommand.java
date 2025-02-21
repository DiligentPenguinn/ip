package diligentpenguin.command;

import java.time.LocalDate;

/**
 * Represents event command, contains information about the command.
 */
public class EventCommand extends Command {
    private final String description;
    private final LocalDate startTime;
    private final LocalDate endTime;

    /**
     * Constructs an event object.
     *
     * @param description Name/Description of the event.
     * @param startTime Starting time.
     * @param endTime Ending time.
     */
    public EventCommand(String description, LocalDate startTime, LocalDate endTime) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public static String getCommandInfo() {
        return "This command adds a new Event task onto the list"
                + "\nFormat: event <description> /from <start time> /to <end time>";
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }
}
