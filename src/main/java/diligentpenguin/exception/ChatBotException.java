package diligentpenguin.exception;

/**
 * Customized exception for the chatbot.
 * This exception is thrown when the chatbot encounters an invalid command or input.
 */

public class ChatBotException extends Exception {
    public ChatBotException(String message) {
        super(message);
    }
}
