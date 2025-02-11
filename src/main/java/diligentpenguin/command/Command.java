package diligentpenguin.command;

/**
 * Represents a generic command, contains information about the command
 */

public abstract class Command {
    private String type;
    public static String getCommandInfo() {
        return "This is a generic command";
    }

    public String getType() {
        return type;
    }
}
