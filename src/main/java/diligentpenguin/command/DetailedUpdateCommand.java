package diligentpenguin.command;

public class DetailedUpdateCommand extends Command {
    public static String getCommandInfo() {
        return "This command updates a task given its index, as well as the new task description \n"
                + "Format: update-<task index> <new task description> \n"
                + "Check out the specific task command for detailed task description format";
    }
}
