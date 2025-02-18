package diligentpenguin.exception;

import diligentpenguin.command.UnmarkCommand;

/**
 * Represents exceptions related to unmark command
 */
public class UnmarkException extends ChatBotException {
    private static final String MESSAGE = "Something is wrong with your unmark command! \n"
            + "Make sure it follows this format: \n"
            + UnmarkCommand.getCommandInfo();

    public UnmarkException() {
        super(MESSAGE);
    }
}
