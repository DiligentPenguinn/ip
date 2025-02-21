package diligentpenguin.command;

/**
 * Represents update command, contains command format information.
 */
public class UpdateCommand extends Command {
    public static String getCommandInfo() {
        return "This command updates a given task by index"
                + "\nFormat: update <task index>";
    }
}
