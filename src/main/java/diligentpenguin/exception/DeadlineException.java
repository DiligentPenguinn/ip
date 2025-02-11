package diligentpenguin.exception;

import diligentpenguin.command.DeadlineCommand;

public class DeadlineException extends ChatBotException {
    private static final String MESSAGE = "Something is wrong with your deadline input! \n"
            + "Make sure it follows this format: \n"
            + DeadlineCommand.getCommandInfo();
    public DeadlineException() {
        super(MESSAGE);
    }
}
