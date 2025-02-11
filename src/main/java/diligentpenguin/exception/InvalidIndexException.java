package diligentpenguin.exception;

public class InvalidIndexException extends ChatBotException {
    private static final String MESSAGE = "That's not a valid item index!";
    public InvalidIndexException() {
        super(MESSAGE);
    }
}
