package diligentpenguin.exception;

public class BadDateTimeException extends ChatBotException {
    private static final String MESSAGE = "Your end date cannot be before your start date!";

    public BadDateTimeException() {
        super(MESSAGE);
    }
}
