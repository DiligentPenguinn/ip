package diligentpenguin.command;

public abstract class Command {
    private String type;
    public static String getCommandInfo() {
        return "This is a generic command";
    }

    public String getType() {
        return type;
    }
}
