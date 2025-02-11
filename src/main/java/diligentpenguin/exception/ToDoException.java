package diligentpenguin.exception;

import diligentpenguin.command.ToDoCommand;

public class ToDoException extends ChatBotException {
    private static final String MESSAGE = "Something is wrong with your todo input! \n"
            + "Make sure it follows this format: \n"
            + ToDoCommand.getCommandInfo();
    public ToDoException() {
        super(MESSAGE);
    }
}
