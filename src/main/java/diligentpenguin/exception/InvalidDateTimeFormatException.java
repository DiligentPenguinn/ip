package diligentpenguin.exception;

import diligentpenguin.task.Task;

public class InvalidDateTimeFormatException extends ChatBotException {
    private static final String MESSAGE = "Something is wrong with your datetime format! \n"
            + "It should have this form: " + Task.getInputDateTimeString();
    public InvalidDateTimeFormatException() {
        super(MESSAGE);
    }
}
