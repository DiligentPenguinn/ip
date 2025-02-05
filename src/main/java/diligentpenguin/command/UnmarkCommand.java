package diligentpenguin.command;

public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    public static String getCommandInfo() {
        return "This command marks a given task as uncompleted"
                + "\nFormat: unmark <task index>";
    }

    public int getIndex() {
        return index;
    }
}
