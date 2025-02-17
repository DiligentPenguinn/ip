package diligentpenguin.exception;

import diligentpenguin.command.UpdateCommand;

public class UpdateException extends ChatBotException {
    private static final String MESSAGE = "Something is wrong with your deadline input! \n"
            + "Make sure it follows this format: \n"
            + UpdateCommand.getCommandInfo();
    public UpdateException() {
        super(MESSAGE);
    }
}
