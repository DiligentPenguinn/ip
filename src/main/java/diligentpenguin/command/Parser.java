package diligentpenguin.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import diligentpenguin.DiligentPenguin;
import diligentpenguin.Storage;
import diligentpenguin.Ui;
import diligentpenguin.exception.ChatBotException;
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
        try {
            LocalDate formattedStartTime = LocalDate.parse(startTime, Task.getInputFormatter());
            LocalDate formattedEndTime = LocalDate.parse(endTime, Task.getInputFormatter());

            return new Event(description, formattedStartTime, formattedEndTime);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeFormatException();
        }
    }

    /**
     * Process a string task into a Task object given its type
     * @param type Type of the task
     * @param item String to process
     * @return Processed Task object
     * @throws ChatBotException If error occurs during parsing
     */
    public Task processTaskByType(String type, String item) throws ChatBotException {
        Task task;
        if (type.equals("T")) {
            task = processToDoTask(item);
        } else if (type.equals("D")) {
            task = processDeadlineTask(item);
        } else if (type.equals("E")) {
            task = processEventTask(item);
        } else {
            throw new ChatBotException("Task cannot be processed!");
        }
        return task;
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
            diligentPenguin.setOver();
            response = ui.generateExitMessage();
        } else if (Objects.equals(command, "list")) {
            response = ui.generateListMessage(diligentPenguin.getTasks().toString());
            // Use of Regex below is adapted from a conversation with chatGPT
        } else if (command.matches("mark \\d+")) {
            int lengthOfMark = 4;
            int index = Integer.parseInt(command.substring(lengthOfMark + 1)) - 1;
            try {
                diligentPenguin.getTasks().finish(index);
                storage.save(diligentPenguin.getTasks());
                response = ui.generateMarkMessage(diligentPenguin.getTasks().get(index).toString(), index);
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidIndexException();
            }
        } else if (command.matches("unmark \\d+")) {
            int lengthOfUnmark = 6;
            int index = Integer.parseInt(command.substring(lengthOfUnmark + 1)) - 1;
            try {
                diligentPenguin.getTasks().unfinish(index);
                storage.save(diligentPenguin.getTasks());
                response = ui.generateUnmarkMessage(diligentPenguin.getTasks().get(index).toString(), index);
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidIndexException();
            }
        } else if (command.matches("delete \\d+")) {
            int lengthOfDelete = 6;
            int index = Integer.parseInt(command.substring(lengthOfDelete + 1)) - 1;
            try {
                Task task = diligentPenguin.getTasks().get(index);
                diligentPenguin.getTasks().remove(index);
                storage.save(diligentPenguin.getTasks());
                response = ui.generateDeleteMessage(task.toString(), index);
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidIndexException();
            }
        } else if (command.matches("update \\d+")) {
            int lengthOfUpdate = 6;
            int index = Integer.parseInt(command.substring(lengthOfUpdate + 1)) - 1;
            try {
                Task task = diligentPenguin.getTasks().get(index);
                response = ui.generateUpdateMessage();
                output = task.toEditString(index + 1);
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidIndexException();
            }
        } else if (command.matches("^update-\\d+ .+")) {
            try {
                String regex = "^update-(\\d+) (.+)";

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(command);

                if (matcher.matches()) {
                    int index = Integer.parseInt(matcher.group(1)) - 1;
                    System.out.println(index);
                    String taskDescription = matcher.group(2);
                    Task task = diligentPenguin.getTasks().get(index);
                    String type = task.getType();
                    Task newTask = processTaskByType(type, taskDescription);
                    diligentPenguin.getTasks().set(index, newTask);
                    response = ui.generateUpdateSuccessMessage(diligentPenguin.getTasks().toString());
                }
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidIndexException();
            }
        } else if (command.startsWith("find ")) {
            String keyword = command.substring(5);
            TaskList filteredTasks = diligentPenguin.getTasks().find(keyword);
            if (filteredTasks.isEmpty()) {
                return new String[]{ui.generateNoTasksFoundMessage()};
            } else {
                response = ui.generateMatchingTasks(filteredTasks.toString());
            }
            // Three cases above can be combined
        } else if (command.startsWith("todo ")) {
            String description = command.substring(5);
            ToDo todoTask = processToDoTask(description);
            diligentPenguin.getTasks().add(todoTask);
            storage.save(diligentPenguin.getTasks());
            response = ui.generateStoreMessage(diligentPenguin.getTasks().getSize());
        } else if (command.startsWith("deadline ")) {
            String item = command.substring(9);
            Deadline deadlineTask = processDeadlineTask(item);
            diligentPenguin.getTasks().add(deadlineTask);
            storage.save(diligentPenguin.getTasks());
            response = ui.generateStoreMessage(diligentPenguin.getTasks().getSize());
            // The piece of code in between is inspired by a conversation with chatGPT
        } else if (command.startsWith("event ")) {
            String item = command.substring(6);
            Event eventTask = processEventTask(item);
            diligentPenguin.getTasks().add(eventTask);
            storage.save(diligentPenguin.getTasks());
            response = ui.generateStoreMessage(diligentPenguin.getTasks().getSize());
        } else {
            throw new UnknownCommandException();
        }
        return new String[]{response, output};
    }
}
