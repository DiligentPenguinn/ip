package diligentpenguin.command;

/**
 * Represents deadline command, contains information about the command.
 */
public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    public static String getCommandInfo() {
        return "This command deletes a given task by index"
                + "\nFormat: delete <task index>";
    }

    public int getIndex() {
        return index;
    }
}
