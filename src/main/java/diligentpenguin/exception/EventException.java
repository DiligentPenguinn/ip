package diligentpenguin.exception;

import diligentpenguin.command.EventCommand;

public class EventException extends ChatBotException {
    private static final String MESSAGE = "Something is wrong with your event input! \n"
            + "Make sure it follows this format: \n"
            + EventCommand.getCommandInfo();
    public EventException() {
        super(MESSAGE);
    }
}
