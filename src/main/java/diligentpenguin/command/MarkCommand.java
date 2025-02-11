package diligentpenguin.command;

/**
 * Represents mark command, contains information about the command
 */
public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    public static String getCommandInfo() {
        return "This command marks a given task as completed"
                + "\nFormat: mark <task index>";
    }

    public int getIndex() {
        return index;
    }
}
