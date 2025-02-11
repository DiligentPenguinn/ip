package diligentpenguin.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import diligentpenguin.exception.ChatBotException;
import diligentpenguin.DiligentPenguin;
import diligentpenguin.Storage;
import diligentpenguin.Ui;
import diligentpenguin.exception.DeadlineException;
import diligentpenguin.exception.EventException;
import diligentpenguin.exception.InvalidDateTimeFormatException;
import diligentpenguin.exception.InvalidIndexException;
import diligentpenguin.exception.ToDoException;
import diligentpenguin.exception.UnknownCommandException;
import diligentpenguin.task.Deadline;
import diligentpenguin.task.Event;
import diligentpenguin.task.Task;
import diligentpenguin.task.TaskList;
import diligentpenguin.task.ToDo;

/**
 * Handles user's commands for chatbot.
 * A <code>Parser</code> object takes in user command and executes corresponding chatbot operations
 */
public class Parser {
    private DiligentPenguin diligentPenguin;
    private Ui ui;
    private Storage storage;

    public Parser(DiligentPenguin diligentPenguin, Ui ui, Storage storage) {
        this.diligentPenguin = diligentPenguin;
        this.ui = ui;
        this.storage = storage;
    }

    /**
     * Parse and execute the user command
     * @param command Command to parse and execute
     * @return The response from Ui
     * @throws ChatBotException If error occurs while parsing and executing
     */
    public String parse(String command) throws ChatBotException {
        if (Objects.equals(command, "bye")) {
            diligentPenguin.setOver();
            return ui.generateExitMessage();
        } else if (Objects.equals(command, "list")) {
            return ui.generateListMessage(diligentPenguin.getTasks().toString());
            // Use of Regex below is adapted from a conversation with chatGPT
        } else if (command.matches("mark \\d+")) {
            int index = Integer.parseInt(command.substring(5)) - 1;
            try {
                diligentPenguin.getTasks().finish(index);
                storage.save(diligentPenguin.getTasks());
                return ui.generateMarkMessage(diligentPenguin.getTasks().get(index).toString(), index);
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidIndexException();
            }
        } else if (command.matches("unmark \\d+")) {
            int index = Integer.parseInt(command.substring(7)) - 1;
            try {
                diligentPenguin.getTasks().unfinish(index);
                storage.save(diligentPenguin.getTasks());
                return ui.generateUnmarkMessage(diligentPenguin.getTasks().get(index).toString(), index);
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidIndexException();
            }
        } else if (command.matches("delete \\d+")) {
            int index = Integer.parseInt(command.substring(7)) - 1;
            try {
                Task task = diligentPenguin.getTasks().get(index);
                diligentPenguin.getTasks().remove(index);
                storage.save(diligentPenguin.getTasks());
                return ui.generateDeleteMessage(task.toString(), index);
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidIndexException();
            }
        } else if (command.startsWith("find ")) {
            String keyword = command.substring(5);
            TaskList filteredTasks = diligentPenguin.getTasks().find(keyword);
            if (filteredTasks.isEmpty()) {
                return ui.generateNoTasksFoundMessage();
            } else {
                return ui.generateMatchingTasks(filteredTasks.toString());
            }
            // Three cases above can be combined
        } else if (command.startsWith("todo ")) {
            String description = command.substring(5);
            if (description.trim().isEmpty()) {
                throw new ToDoException();
            }
            ToDo todoTask = new ToDo(description);
            diligentPenguin.getTasks().add(todoTask);
            storage.save(diligentPenguin.getTasks());
            return ui.generateStoreMessage(diligentPenguin.getTasks().getSize());
        } else if (command.startsWith("deadline ")) {
            String item = command.substring(9);
            if (!item.contains("/by")) {
                throw new DeadlineException();
            }
            // The piece of code in between is inspired by a conversation with chatGPT
            int index = item.indexOf("/by");

            // Extract the part before "/by"
            String description = item.substring(0, index).trim();

            // Extract the part after "/by"
            String deadline = item.substring(index + 3).trim();
            try {
                LocalDate formattedDeadline = LocalDate.parse(deadline, Task.getInputFormatter());
                Deadline deadlineTask = new Deadline(description, formattedDeadline);
                diligentPenguin.getTasks().add(deadlineTask);
                storage.save(diligentPenguin.getTasks());
                return ui.generateStoreMessage(diligentPenguin.getTasks().getSize());
            } catch (DateTimeParseException e) {
                throw new InvalidDateTimeFormatException();
            }
            // The piece of code in between is inspired by a conversation with chatGPT
        } else if (command.startsWith("event ")) {
            String item = command.substring(6);

            if (!item.contains("/from") || !item.contains("/to")) {
                throw new EventException();
            }
            int indexFrom = item.indexOf("/from");
            int indexTo = item.indexOf("/to");

            String description = item.substring(0, indexFrom).trim();
            String startTime = item.substring(indexFrom + 5, indexTo).trim();
            String endTime = item.substring(indexTo + 3).trim();

            if (description.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                throw new EventException();
            }
            try {
                LocalDate formattedStartTime = LocalDate.parse(startTime, Task.getInputFormatter());
                LocalDate formattedEndTime = LocalDate.parse(endTime, Task.getInputFormatter());

                Event eventTask = new Event(description, formattedStartTime, formattedEndTime);
                diligentPenguin.getTasks().add(eventTask);
                storage.save(diligentPenguin.getTasks());
                return ui.generateStoreMessage(diligentPenguin.getTasks().getSize());
            } catch (DateTimeParseException e) {
                throw new InvalidDateTimeFormatException();
            }
        } else {
            throw new UnknownCommandException();
        }
    }
}
