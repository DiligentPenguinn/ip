package diligentpenguin.exception;

import diligentpenguin.command.MarkCommand;

/**
 * Represents exceptions related to mark command
 */
public class MarkException extends ChatBotException {
    private static final String MESSAGE = "Something is wrong with your mark command! \n"
            + "Make sure it follows this format: \n"
            + MarkCommand.getCommandInfo();

    public MarkException() {
        super(MESSAGE);
    }
}
