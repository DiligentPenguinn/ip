package diligentpenguin.exception;

import diligentpenguin.command.DetailedUpdateCommand;

/**
 * Represents exception related to detailed update command.
 */
public class DetailedUpdateException extends ChatBotException {
    private static final String MESSAGE = "Something is wrong with your deadline input! \n"
            + "Make sure it follows this format: \n"
            + DetailedUpdateCommand.getCommandInfo();

    public DetailedUpdateException() {
        super(MESSAGE);
    }
}
