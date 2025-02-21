package diligentpenguin.exception;

import diligentpenguin.command.UpdateCommand;

/**
 * Represents exceptions related to update command.
 */
public class UpdateException extends ChatBotException {
    private static final String MESSAGE = "Something is wrong with your update input! \n"
            + "Make sure it follows this format: \n"
            + UpdateCommand.getCommandInfo();
    public UpdateException() {
        super(MESSAGE);
    }
}
