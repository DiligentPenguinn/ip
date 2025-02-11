package diligentpenguin.command;

/**
 * Represents list command, contains information about the command
 */
public class ListCommand extends Command {
    public static String getCommandInfo() {
        return "This command prints all saved tasks in a list format";
    }
}
