package diligentpenguin.exception;

import diligentpenguin.command.FindCommand;

/**
 * Represents exceptions related to find command
 */
public class FindException extends ChatBotException {
    private static final String MESSAGE = "Something is wrong with your find command! \n"
            + "Make sure it follows this format: \n"
            + FindCommand.getCommandInfo();

    public FindException() {
        super(MESSAGE);
    }
}
