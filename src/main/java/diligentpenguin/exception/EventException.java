package diligentpenguin.exception;

import diligentpenguin.command.EventCommand;

/**
 * Represents exceptions related to event tasks.
 */
public class EventException extends ChatBotException {
    private static final String MESSAGE = "Something is wrong with your event input! \n"
            + "Make sure it follows this format: \n"
            + EventCommand.getCommandInfo();
    public EventException() {
        super(MESSAGE);
    }
}
