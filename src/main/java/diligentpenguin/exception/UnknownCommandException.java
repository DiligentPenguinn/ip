package diligentpenguin.exception;

public class UnknownCommandException extends ChatBotException {
    private static final String MESSAGE = "Uuh, I don't know what you mean";
    public UnknownCommandException() {
        super(MESSAGE);
    }
}
