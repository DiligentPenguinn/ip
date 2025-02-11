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
        assert directoryPath != null : "Directory path should not be null!";
        assert fileName != null : "File name should not be null";
        String filePath = directoryPath + fileName;
        this.storage = new Storage(directoryPath, filePath);
        this.ui = new Ui();
        parser = new Parser(this, this.ui, this.storage);
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String[] getResponse(String input) {
        assert input != null : "User input should not be null!";
        try {
            return parser.parse(input);
        } catch (Exception e) {
            return new String[]{e.getMessage(), ""};
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
