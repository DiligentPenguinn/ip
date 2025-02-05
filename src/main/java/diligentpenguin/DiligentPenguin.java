package diligentpenguin;

import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import diligentpenguin.command.Parser;
import diligentpenguin.task.Task;
import diligentpenguin.task.TaskList;


/**
 * Represents the chatbot.
 * A <code>DiligentPenguin</code> object handles user input, manage tasks, storages and responses
 */

public class DiligentPenguin {
    private String name = "DiligentPenguin";
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks = new TaskList();
    private final Parser parser;
    private boolean isOver = false;

    /**
     * Construct a new <code>DiligentPenguin</code> chatbot.
     * @param directoryPath Directory path where task data is stored
     * @param fileName Name of the file where task data is stored
     */
    public DiligentPenguin(String directoryPath, String fileName) {
        String filePath = directoryPath + fileName;
        this.storage = new Storage(directoryPath, filePath);
        this.ui = new Ui();
        parser = new Parser(this, this.ui, this.storage);
    }

    public static void main(String[] args) {
        DiligentPenguin chatBot = new DiligentPenguin("src/main/data/", "tasks.txt");
        // chatBot.run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            return parser.parse(input);
        } catch (Exception e) {
            String message = "Oops! there seems to be an error:\n"
                    + e.getMessage()
                    + "\nPlease try again!";
            return message;
        }
    }

    /**
     * Return the tasks noted by the chatbot
     * @return <code>TaskList</code> of tasks
     */
    public TaskList getTasks() {
        return this.tasks;
    }

    /**
     * Store the task into task list
     * @param userInput Description of the task to store
     * @param type Type of the task to store
     * @throws ChatBotException If the task format is incorrect
     * @throws DateTimeParseException If the task datetime format is incorrect
     */
//    public void store(String userInput, TaskList.TaskType type) throws ChatBotException, DateTimeParseException {
//        tasks.add(userInput, type);
//        ui.showStoreMessage(tasks.getSize());
//    }

    /**
     * Mark a task as completed by index
     * @param i Index of task to mark
     * @throws ChatBotException If the index is out of bound
     */
//    public void mark(int i) throws ChatBotException {
//        try {
//            tasks.finish(i);
//            ui.showMarkMessage(tasks.get(i).toString(), i);
//        } catch (IndexOutOfBoundsException e) {
//            throw new ChatBotException("That's not a valid item index!");
//        }
//    }

    /**
     * Mark a task as uncompleted by index
     * @param i Index of task to mark
     * @throws ChatBotException If the index is out of bound
     */
//    public void unmark(int i) throws ChatBotException {
//        try {
//            tasks.unfinish(i);
//            ui.showUnmarkMessage(tasks.get(i).toString(), i);
//        } catch (IndexOutOfBoundsException e) {
//            throw new ChatBotException("That's not a valid item index!");
//        }
//    }

    /**
     * Delete a task by index
     * @param i Index of the task to delete
     * @throws ChatBotException If the index is out of bound
     */
//    public void delete(int i) throws ChatBotException {
//        try {
//            Task task = tasks.get(i);
//            tasks.remove(i);
//            ui.showDeleteMessage(task.toString(), i);
//        } catch (IndexOutOfBoundsException e) {
//            throw new ChatBotException("That's not a valid item index!");
//        }
//    }

    /**
     * Find tasks matching the given keyword
     * @param keyword keyword to match
     */
//    public void findByKeyword(String keyword) {
//        TaskList filteredTasks = this.tasks.find(keyword);
//        if (filteredTasks.isEmpty()) {
//            ui.showNoTasksFoundMessage();
//        } else {
//            ui.showMatchingTasks(filteredTasks.toString());
//        }
//    }

    public boolean isOver() {
        return this.isOver;
    }

    public void setOver() {
        this.isOver = true;
    }
    /**
     * Run the chatbot, handle user inputs and responses.
     */
    public String run() {
        String message = "";
        message = message + this.ui.generateGreetMessage(name) + "\n";
        try {
            this.storage.loadTaskList(tasks);
            this.storage.save(tasks);
            message = message + ui.generateLoadSuccessMessage(tasks.toString()) + "\n";
        } catch (ChatBotException e) {
            message = message + ui.generateNoDataMessage() + "\n";
        } catch (FileNotFoundException e) {
            message = message + ui.generateFileNotFoundError() + "\n";
            this.storage.createSavedDirectoryAndFile();
        }

        return message;
//        while (!parser.isFinish()) {
//            String userInput = scanner.nextLine();
//            try {
//                parser.parse(userInput);
//            } catch (DateTimeParseException e) {
//                ui.showDatetimeError();
//            } catch (Exception e) {
//                ui.showChatbotErrorMessage(e);
//            }
//        }
    }
}
