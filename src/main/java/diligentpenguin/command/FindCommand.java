package diligentpenguin.command;

/**
 * Represents find command, contains information about the command.
 */
public class FindCommand extends Command {
    private final String keyword;
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }
    public static String getCommandInfo() {
        return "This command finds tasks matching the given keyword"
                + "\nFormat: find <keyword>";
    }

    public String getKeyword() {
        return keyword;
    }
}
