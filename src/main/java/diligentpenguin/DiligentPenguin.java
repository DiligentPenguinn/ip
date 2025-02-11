package diligentpenguin;

import java.io.FileNotFoundException;

import diligentpenguin.command.Parser;
import diligentpenguin.exception.ChatBotException;
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
            return e.getMessage();
        }
    }

    /**
     * Return the tasks noted by the chatbot
     * @return <code>TaskList</code> of tasks
     */
    public TaskList getTasks() {
        return this.tasks;
    }

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
    }
}
