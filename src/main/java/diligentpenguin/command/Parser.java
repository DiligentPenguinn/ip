package diligentpenguin.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import diligentpenguin.DiligentPenguin;
import diligentpenguin.Storage;
import diligentpenguin.Ui;
import diligentpenguin.exception.BadDateTimeException;
import diligentpenguin.exception.ChatBotException;
import diligentpenguin.exception.DeadlineException;
import diligentpenguin.exception.DeleteException;
import diligentpenguin.exception.DetailedUpdateException;
import diligentpenguin.exception.EventException;
import diligentpenguin.exception.FindException;
import diligentpenguin.exception.InvalidDateTimeFormatException;
import diligentpenguin.exception.InvalidIndexException;
import diligentpenguin.exception.MarkException;
import diligentpenguin.exception.ToDoException;
import diligentpenguin.exception.UnknownCommandException;
import diligentpenguin.exception.UnmarkException;
import diligentpenguin.exception.UpdateException;
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
    private final DiligentPenguin diligentPenguin;
    private final Ui ui;
    private final Storage storage;

    /**
     * Constructs a parser object
     * @param diligentPenguin Chatbot to use
     * @param ui Ui to use
     * @param storage Storage to use
     */
    public Parser(DiligentPenguin diligentPenguin, Ui ui, Storage storage) {
        this.diligentPenguin = diligentPenguin;
        this.ui = ui;
        this.storage = storage;
    }

    /**
     * Processes the description string into a ToDo object
     * @param item string to process
     * @return Processed ToDo object
     * @throws ChatBotException If error occurs during parsing
     */
    public ToDo processToDoTask(String item) throws ChatBotException {
        if (item.trim().isEmpty()) {
            throw new ToDoException();
        }
        return new ToDo(item);
    }

    /**
     * Processes the description string into a Deadline object
     * @param item string to process
     * @return Processed Deadline object
     * @throws ChatBotException If error occurs during parsing
     */
    public Deadline processDeadlineTask(String item) throws ChatBotException {
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
            return new Deadline(description, formattedDeadline);

        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeFormatException();
        }
    }

    /**
     * Processes the description string into a Event object
     * @param item string to process
     * @return Processed Event object
     * @throws ChatBotException If error occurs during parsing
     */
    public Event processEventTask(String item) throws ChatBotException {
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

        LocalDate formattedStartTime;
        LocalDate formattedEndTime;
        try {
            formattedStartTime = LocalDate.parse(startTime, Task.getInputFormatter());
            formattedEndTime = LocalDate.parse(endTime, Task.getInputFormatter());
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeFormatException();
        }

        if (formattedEndTime.isBefore(formattedStartTime)) {
            throw new BadDateTimeException();
        }
        return new Event(description, formattedStartTime, formattedEndTime);
    }

    /**
     * Process a string task into a Task object given its type
     * @param type Type of the task
     * @param item String to process
     * @return Processed Task object
     * @throws ChatBotException If error occurs during parsing
     */
    public Task processTaskByType(String type, String item) throws ChatBotException {
        // Refactor based on IntelliJ's suggestion
        return switch (type) {
        case "T" -> processToDoTask(item);
        case "D" -> processDeadlineTask(item);
        case "E" -> processEventTask(item);
        default -> throw new ChatBotException("Task cannot be processed!");
        };
    }

    /**
     * Process a bye command from the user
     * @return Response from the chatbot
     */
    public String processByeCommand() {
        diligentPenguin.setOver();
        return ui.generateExitMessage();
    }

    /**
     * Process a list command from the user
     * @return Response from the chatbot
     */
    public String processListCommand() {
        return ui.generateListMessage(diligentPenguin.getTasks().toString());
    }

    /**
     * Process a mark command from the user
     * @param command Command to process
     * @return Response from the chatbot
     * @throws ChatBotException Exception occurs during the process
     */
    public String processMarkCommand(String command) throws ChatBotException {
        // Use of Regex below is adapted from a conversation with chatGPT
        if (!command.matches("mark \\d+")) {
            throw new MarkException();
        }
        int lengthOfMark = 4;
        int index = Integer.parseInt(command.substring(lengthOfMark + 1)) - 1;
        try {
            diligentPenguin.getTasks().finish(index);
            storage.save(diligentPenguin.getTasks());
            return ui.generateMarkMessage(diligentPenguin.getTasks().get(index).toString(), index);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException();
        }
    }

    /**
     * Process an unmark command from the user
     * @param command Command to process
     * @return Response from the chatbot
     * @throws ChatBotException Exception occurs during the process
     */
    public String processUnmarkCommand(String command) throws ChatBotException {
        if (!command.matches("unmark \\d+")) {
            throw new UnmarkException();
        }
        int lengthOfUnmark = 6;
        int index = Integer.parseInt(command.substring(lengthOfUnmark + 1)) - 1;
        try {
            diligentPenguin.getTasks().unfinish(index);
            storage.save(diligentPenguin.getTasks());
            return ui.generateUnmarkMessage(diligentPenguin.getTasks().get(index).toString(), index);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException();
        }
    }

    /**
     * Process a delete command from the user
     * @param command Command to process
     * @return Response from the chatbot
     * @throws ChatBotException Exception occurs during the process
     */
    public String processDeleteCommand(String command) throws ChatBotException {
        if (!command.matches("delete \\d+")) {
            throw new DeleteException();
        }
        int lengthOfDelete = 6;
        int index = Integer.parseInt(command.substring(lengthOfDelete + 1)) - 1;
        try {
            Task task = diligentPenguin.getTasks().get(index);
            diligentPenguin.getTasks().remove(index);
            storage.save(diligentPenguin.getTasks());
            return ui.generateDeleteMessage(task.toString(), index);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException();
        }
    }

    /**
     * Process an update command from the user of the form update {index}
     * @param command Command to process
     * @return Response from the chatbot as well as pre-typed output for the user
     * @throws ChatBotException Exception occurs during the process
     */
    public String[] processShortUpdateCommand(String command) throws ChatBotException {
        if (!command.matches("update \\d+")) {
            throw new UpdateException();
        }
        int lengthOfUpdate = 6;
        int index = Integer.parseInt(command.substring(lengthOfUpdate + 1)) - 1;
        try {
            Task task = diligentPenguin.getTasks().get(index);
            String response = ui.generateUpdateMessage();
            String output = task.toEditString(index + 1);
            return new String[]{response, output};
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException();
        }
    }

    /**
     * Process a detailed command from the user
     * @param command Command to process
     * @return Response from the chatbot
     * @throws ChatBotException Exception occurs during the process
     */
    public String processDetailedUpdateCommand(String command) throws ChatBotException {
        String regex = "^update-(\\d+) (.+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);

        if (!matcher.matches()) {
            throw new DetailedUpdateException();
        }

        try {
            int index = Integer.parseInt(matcher.group(1)) - 1;
            System.out.println(index);
            String taskDescription = matcher.group(2);
            Task task = diligentPenguin.getTasks().get(index);
            String type = task.getType();
            Task newTask = processTaskByType(type, taskDescription);
            diligentPenguin.getTasks().set(index, newTask);
            return ui.generateUpdateSuccessMessage(diligentPenguin.getTasks().toString());
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException();
        }
    }

    /**
     * Process general update commands (either short or detailed)
     * @param command Command to process
     * @return strings of response and pre-typed output for the user
     * @throws ChatBotException Exceptions occur durring the process
     */
    public String[] processUpdateCommand(String command) throws ChatBotException {
        if (command.length() == 4) {
            throw new UpdateException();
        }
        if (command.matches("update \\d+")) {
            return processShortUpdateCommand(command);
        } else if (command.matches("^update-(\\d+) (.+)")) {
            return new String[]{processDetailedUpdateCommand(command), ""};
        } else {
            throw new UpdateException();
        }
    }

    /**
     * Process a find command from the user
     * @param command Command to process
     * @return Response from the chatbot
     */
    public String processFindCommand(String command) throws ChatBotException {
        int lengthOfFind = 4;
        if (command.length() == lengthOfFind) {
            throw new FindException();
        }
        String keyword = command.substring(lengthOfFind + 1);
        TaskList filteredTasks = diligentPenguin.getTasks().find(keyword);
        if (filteredTasks.isEmpty()) {
            return ui.generateNoTasksFoundMessage();
        } else {
            return ui.generateMatchingTasks(filteredTasks.toString());
        }
    }

    /**
     * Process a todo command from the user
     * @param command Command to process
     * @return Response from the chatbot
     */
    public String processToDoCommand(String command) throws ChatBotException {
        int todoLength = "todo".length();
        if (command.length() == todoLength) {
            throw new ToDoException();
        }
        String description = command.substring(todoLength + 1);
        ToDo todoTask = processToDoTask(description);
        diligentPenguin.getTasks().add(todoTask);
        storage.save(diligentPenguin.getTasks());
        return ui.generateStoreMessage(diligentPenguin.getTasks().getSize());
    }

    /**
     * Process a deadline command from the user
     * @param command Command to process
     * @return Response from the chatbot
     */
    public String processDeadlineCommand(String command) throws ChatBotException {
        int deadlineLength = "deadline".length();
        if (command.length() == deadlineLength) {
            throw new DeadlineException();
        }
        String item = command.substring(deadlineLength + 1);
        Deadline deadlineTask = processDeadlineTask(item);
        diligentPenguin.getTasks().add(deadlineTask);
        storage.save(diligentPenguin.getTasks());
        return ui.generateStoreMessage(diligentPenguin.getTasks().getSize());
    }

    /**
     * Process an event command from the user
     * @param command Command to process
     * @return Response from the chatbot
     */
    public String processEventCommand(String command) throws ChatBotException {
        int eventLength = "event".length();
        if (command.length() == eventLength) {
            throw new EventException();
        }
        String item = command.substring(eventLength + 1);
        Event eventTask = processEventTask(item);
        diligentPenguin.getTasks().add(eventTask);
        storage.save(diligentPenguin.getTasks());
        return ui.generateStoreMessage(diligentPenguin.getTasks().getSize());
    }


    /**
     * Parse and execute the user command
     * @param command Command to parse and execute
     * @return The response from Ui
     * @throws ChatBotException If error occurs while parsing and executing
     */
    public String[] parse(String command) throws ChatBotException {
        String response = "";
        String output = "";
        if (Objects.equals(command, "bye")) {
            response = processByeCommand();
        } else if (Objects.equals(command, "list")) {
            response = processListCommand();
        } else if (command.startsWith("mark")) {
            response = processMarkCommand(command);
        } else if (command.startsWith("unmark")) {
            response = processUnmarkCommand(command);
        } else if (command.startsWith("delete")) {
            response = processDeleteCommand(command);
        } else if (command.startsWith("update")) {
            String[] results = processUpdateCommand(command);
            response = results[0];
            output = results[1];
        } else if (command.startsWith("find")) {
            response = processFindCommand(command);
            // Three cases below can be combined
        } else if (command.startsWith("todo")) {
            response = processToDoCommand(command);
        } else if (command.startsWith("deadline")) {
            response = processDeadlineCommand(command);
        } else if (command.startsWith("event")) {
            response = processEventCommand(command);
        } else {
            throw new UnknownCommandException();
        }
        return new String[]{response, output};
    }
}
