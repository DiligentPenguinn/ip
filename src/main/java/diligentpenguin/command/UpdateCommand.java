package diligentpenguin.command;

public class UpdateCommand extends Command {
    public static String getCommandInfo() {
        return "This command updates a given task by index"
                + "\nFormat: update <task index>";
    }
}