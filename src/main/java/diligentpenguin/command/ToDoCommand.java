package diligentpenguin.command;

public class ToDoCommand extends Command {
    private final String description;
    public ToDoCommand(String description) {
        this.description = description;
    }
    public static String getCommandInfo() {
        return "This command adds a new To Do task onto the list"
                + "\nFormat: todo <description>";
    }

    public String getDescription() {
        return description;
    }
}
