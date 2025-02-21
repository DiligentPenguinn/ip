package diligentpenguin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import diligentpenguin.exception.ChatBotException;
import diligentpenguin.task.Deadline;
import diligentpenguin.task.Event;
import diligentpenguin.task.Task;
import diligentpenguin.task.TaskList;
import diligentpenguin.task.ToDo;

/**
 * Handles data storage operations for chatbot.
 * A <code>Storage</code> object creates directory and file for task data,
 * handles loading and saving of task data.
 */
public class Storage {
    private String filePath;
    private String directoryPath;

    /**
     * Constructs a new <code>Storage</code> object with the specified paths to directory and file.
     *
     * @param directoryPath Path to where the data file is stored.
     * @param filePath Path to the data file.
     */
    public Storage(String directoryPath, String filePath) {
        assert directoryPath != null : "Directory path should not be null!";
        assert filePath != null : "File path should not be null";
        this.filePath = filePath;
        this.directoryPath = directoryPath;
    }

    /**
     * Saves tasks into data task file.
     *
     * @param tasks Tasks to save.
     * @throws ChatBotException If error occurs while saving.
     */
    public void save(TaskList tasks) throws ChatBotException {
        assert tasks != null : "Task list should not be null!";
        try {
            FileWriter fw = new FileWriter(this.filePath);
            ArrayList<Task> arrayTask = tasks.getAllTasks();
            for (Task task: arrayTask) {
                fw.write(task.toSavedString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new ChatBotException("Oops! Something went wrong, I cannot save the tasks list! "
                    + e.getMessage());
        }
    }

    /**
     * Reads a todo task from its description.
     *
     * @param line Description of task to read.
     * @return The <code>ToDo</code> object.
     */
    static ToDo readTodoTask(String line) {
        String[] parts = line.split("\\|");
        boolean isDone = parts[1].trim().equals("X");
        String description = parts[2].trim();
        ToDo task = new ToDo(description);
        if (isDone) {
            task.setDone();
        }
        return task;
    }

    /**
     * Reads a deadline task from its description.
     *
     * @param line Description of task to read.
     * @return The <code>Deadline</code> object.
     */
    static Deadline readDeadlineTask(String line) {
        assert line != null : "Line to read should not be null!";
        int deadlineTaskLength = 4;
        int deadlineIndex = 3;
        String[] parts = line.split("\\|");
        boolean isDone = parts[1].trim().equals("X");
        String description = parts[2].trim();
        String deadline = (parts.length == deadlineTaskLength) ? parts[deadlineIndex].trim() : "";

        LocalDate formattedDeadline = LocalDate.parse(deadline, Task.getInputFormatter());
        Deadline task = new Deadline(description, formattedDeadline);
        if (isDone) {
            task.setDone();
        }
        return task;
    }

    /**
     * Reads an event task from its description.
     *
     * @param line Description of task to read.
     * @return The <code>Event</code> object.
     * @throws DateTimeParseException Exception occurs during parsing.
     */
    static Event readEventTask(String line) throws DateTimeParseException {
        assert line != null : "Line to read should not be null!";
        int eventTaskLength = 5;
        int eventStartTimeIndex = 3;
        int eventEndTimeIndex = 4;
        String[] parts = line.split("\\|");
        boolean isDone = parts[1].trim().equals("X");
        String description = parts[2].trim();
        String startTime = (parts.length == eventTaskLength) ? parts[eventStartTimeIndex].trim() : "";
        String endTime = (parts.length == eventTaskLength) ? parts[eventEndTimeIndex].trim() : "";
        LocalDate formattedStartTime = LocalDate.parse(startTime, Task.getInputFormatter());
        LocalDate formattedEndTime = LocalDate.parse(endTime, Task.getInputFormatter());
        Event task = new Event(description, formattedStartTime, formattedEndTime);
        if (isDone) {
            task.setDone();
        }
        return task;
    }

    /**
     * Reads a task from its description.
     *
     * @param line Description of task to read.
     * @return The <code>Task</code> object.
     * @throws ChatBotException If description format is incorrect.
     * @throws DateTimeParseException If description datetime format is incorrect.
     */
    static Task readTask(String line) throws ChatBotException, DateTimeParseException {
        assert line != null : "Line to read should not be null!";
        String[] parts = line.split("\\|");
        String type = parts[0].trim();
        // The code below is refactored with IntelliJ
        return switch (type) {
        case "T" -> readTodoTask(line);
        case "D" -> readDeadlineTask(line);
        case "E" -> readEventTask(line);
        default -> throw new ChatBotException("Oops! It seems that the format of the saved file is wrong/corrupted!");
        };
    }

    /**
     * Loads tasks from saved file.
     *
     * @param tasks Task to load.
     * @throws ChatBotException If no data is found.
     * @throws FileNotFoundException If saved file is not found.
     */
    public void loadTaskList(TaskList tasks) throws ChatBotException {
        assert tasks != null : "Task list to load should not be null!";
        File file = new File(this.filePath);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            createSavedDirectoryAndFile();
            throw new ChatBotException("File not found!");
        }
        String taskDescription = "";
        while (scanner.hasNext()) {
            try {
                taskDescription = scanner.nextLine();
                tasks.add(Storage.readTask(taskDescription));
            } catch (DateTimeParseException e) {
                System.out.println("Oops! There's something wrong with the datetime format of this line: ");
                System.out.println(taskDescription);
            }
        }
        if (tasks.isEmpty()) {
            throw new ChatBotException("No data found!");
        }
    }

    /**
     * Creates a directory and file for saving task data if they don't exist.
     */
    public void createSavedDirectoryAndFile() {
        File directory = new File(this.directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(this.filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Oops! Something went wrong!");
            }
        }
    }
}
