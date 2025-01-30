package diligentpenguin;
import diligentpenguin.task.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles data storage operations for chatbot.
 * A <code>Storage</code> object creates directory and file for task data,
 * handles loading and saving of task data.
 */
public class Storage {
    String filePath = "";
    String directoryPath = "";

    /**
     * Construct the <code>Storage</code> object with directory and file path
     * @param directoryPath Directory where data is stored
     * @param filePath File path where data is stored
     */
    public Storage(String directoryPath, String filePath) {
        this.filePath = filePath;
        this.directoryPath = directoryPath;
    }

    /**
     * Save tasks into data task file
     * @param tasks Tasks to save
     * @throws ChatBotException If error occurs while saving
     */
    public void save(TaskList tasks) throws ChatBotException {
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
     * Read a task from its description
     * @param line Description of task to read
     * @return The <code>Task</code> object
     * @throws ChatBotException If description format is incorrect
     * @throws DateTimeParseException If description datetime format is incorrect
     */
    private static Task readTask(String line) throws ChatBotException, DateTimeParseException {
        Task task;
        String[] parts = line.split("\\|");
        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("X");
        String description = parts[2].trim();
        String deadline = (parts.length == 4) ? parts[3].trim() : "";
        String startTime = (parts.length == 5) ? parts[3].trim() : "";
        String endTime = (parts.length == 5) ? parts[4].trim() : "";
        // The above code is inspired by a conversation with chatGPT
        switch (type) {
            case "T":
                task = new ToDo(description);
                if (isDone) {
                    task.setDone();
                }
                return task;
            case "D":
                LocalDate formattedDeadline = LocalDate.parse(deadline, Task.inputFormatter);
                task = new Deadline(description, formattedDeadline);
                if (isDone) {
                    task.setDone();
                }
                return task;
            case "E":
                LocalDate formattedStartTime = LocalDate.parse(startTime, Task.inputFormatter);
                LocalDate formattedEndTime = LocalDate.parse(endTime, Task.inputFormatter);
                task = new Event(description, formattedStartTime, formattedEndTime);
                if (isDone) {
                    task.setDone();
                }
                return task;
            default:
                throw new ChatBotException("Oops! It seems that the format of the saved file is wrong/corrupted!");
        }
    }

    /**
     * Load tasks from saved file
     * @param tasks Task to load
     * @throws ChatBotException If no data is found
     * @throws FileNotFoundException If saved file is not found
     */
    public void loadTaskList(TaskList tasks) throws ChatBotException, FileNotFoundException {
        File file = new File(this.filePath);
        Scanner scanner = new Scanner(file);
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
     * Create a directory and file for saving task data if they don't exist
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
