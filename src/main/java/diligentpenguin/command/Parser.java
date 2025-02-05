package diligentpenguin.command;

import java.util.Objects;

import diligentpenguin.ChatBotException;
import diligentpenguin.DiligentPenguin;
import diligentpenguin.Storage;
import diligentpenguin.Ui;
import diligentpenguin.task.TaskList;

/**
 * Handles user's commands for chatbot.
 * A <code>Parser</code> object takes in user command and executes corresponding chatbot operations
 */
public class Parser {
    private boolean isFinish = false;

    public boolean isFinish() {
        return this.isFinish;
    }

    /**
     * Parse and execute the user command
     * @param command Command to parse and execute
     * @param chatBot Chatbot to execute command on
     * @param ui <code>Ui</code> object corresponds to the chatbot
     * @param storage <code>Storage</code> object corresponds to the chatbot
     * @throws ChatBotException If error occurs while parsing and executing
     */
    public void parse(String command, DiligentPenguin chatBot, Ui ui, Storage storage)
            throws ChatBotException {
        if (Objects.equals(command, "bye")) {
            ui.showExitMessage();
            this.isFinish = true;
        } else if (Objects.equals(command, "list")) {
            ui.showListMessage(chatBot.getTasks().toString());
            // Use of Regex below is adapted from a conversation with chatGPT
        } else if (command.matches("mark \\d+")) {
            int index = Integer.parseInt(command.substring(5)) - 1;
            chatBot.mark(index);
        } else if (command.matches("unmark \\d+")) {
            int index = Integer.parseInt(command.substring(7)) - 1;
            chatBot.unmark(index);
        } else if (command.matches("delete \\d+")) {
            int index = Integer.parseInt(command.substring(7)) - 1;
            chatBot.delete(index);
        } else if (command.startsWith("find ")) {
            String keyword = command.substring(5);
            chatBot.findByKeyword(keyword);
            // Three cases above can be combined
        } else if (command.startsWith("todo ")) {
            String description = command.substring(5);
            chatBot.store(description, TaskList.TaskType.TODO);
            storage.save(chatBot.getTasks());
        } else if (command.startsWith("deadline ")) {
            String description = command.substring(9);
            chatBot.store(description, TaskList.TaskType.DEADLINE);
            storage.save(chatBot.getTasks());
        } else if (command.startsWith("event ")) {
            String description = command.substring(6);
            chatBot.store(description, TaskList.TaskType.EVENT);
            storage.save(chatBot.getTasks());
        } else {
            ui.showUnknownCommandMessage();
        }
    }
}
