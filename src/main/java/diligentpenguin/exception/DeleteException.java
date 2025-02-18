package diligentpenguin.exception;

import diligentpenguin.command.DeleteCommand;

/**
 * Represents exceptions related to delete command
 */
public class DeleteException extends ChatBotException {
    private static final String MESSAGE = "Something is wrong with your delete command! \n"
            + "Make sure it follows this format: \n"
            + DeleteCommand.getCommandInfo();

    public DeleteException() {
        super(MESSAGE);
    }
}
