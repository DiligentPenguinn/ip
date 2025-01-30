package diligentpenguin;

import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import diligentpenguin.command.Parser;
import diligentpenguin.task.Task;
import diligentpenguin.task.TaskList;


public class DiligentPenguin {
    static String name = "DiligentPenguin";
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks = new TaskList();

    public DiligentPenguin(String directoryPath, String fileName) {
        String filePath = directoryPath + fileName;
        this.storage = new Storage(directoryPath, filePath);
        this.ui = new Ui();
    }

    public static void main(String[] args) {
        DiligentPenguin chatBot = new DiligentPenguin("src/main/data/", "tasks.txt");
        chatBot.run();
    }

    public TaskList getTasks() {
        return this.tasks;
    }

    public void store(String userInput, TaskList.TaskType type) throws ChatBotException, DateTimeParseException {
        tasks.add(userInput, type);
        ui.showStoreMessage(tasks.getSize());
    }

    public void mark(int i) throws ChatBotException {
        try {
            tasks.finish(i);
            ui.showMarkMessage(tasks.get(i).toString(), i);
        } catch (IndexOutOfBoundsException e) {
            throw new ChatBotException("That's not a valid item index!");
        }
    }

    public void unmark(int i) throws ChatBotException {
        try {
            tasks.unfinish(i);
            ui.showUnmarkMessage(tasks.get(i).toString(), i);
        } catch (IndexOutOfBoundsException e) {
            throw new ChatBotException("That's not a valid item index!");
        }
    }

    public void delete(int i) throws ChatBotException {
        try {
            Task task = tasks.get(i);
            tasks.remove(i);
            ui.showDeleteMessage(task.toString(), i);
        } catch (IndexOutOfBoundsException e) {
            throw new ChatBotException("That's not a valid item index!");
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        this.ui.showGreetMessage(name);
        try {
            this.storage.loadTaskList(tasks);
            this.storage.save(tasks);
            ui.showLoadSuccessMessage(tasks.toString());
        } catch (ChatBotException e) {
            ui.showNoDataMessage();
        } catch (FileNotFoundException e) {
            ui.showFileNotFoundError();
            this.storage.createSavedDirectoryAndFile();
        }

        Parser parser = new Parser();

        while (!parser.isFinish) {
            String userInput = scanner.nextLine();
            try {
                parser.parse(userInput, this, this.ui, this.storage);
            } catch (DateTimeParseException e) {
                ui.showDatetimeError();
            } catch (Exception e) {
                ui.showChatbotErrorMessage(e);
            }
        }
    }
}
