import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Scanner;


public class DiligentPenguin {
    private final Storage storage;
    private final Ui ui;
    static TaskList tasks = new TaskList();
    static String tasksDirectoryPath = "src/main/data/";
    static String tasksFilePath = tasksDirectoryPath + "tasks.txt";

    static String name = "DiligentPenguin";

    public DiligentPenguin(String directoryPath, String fileName) {
        String filePath = directoryPath + fileName;
        this.storage = new Storage(directoryPath, filePath);
        this.ui = new Ui();
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

        while (true) {
            String userInput = scanner.nextLine();
            try {
                if (Objects.equals(userInput, "bye")) {
                    ui.showExitMessage();
                    break;
                } else if (Objects.equals(userInput, "list")) {
                    ui.showListMessage(tasks.toString());
                    // Use of Regex below is adapted from a conversation with chatGPT
                } else if (userInput.matches("mark \\d+")) {
                    int index = Integer.parseInt(userInput.substring(5)) - 1;
                    this.mark(index);
                } else if (userInput.matches("unmark \\d+")) {
                    int index = Integer.parseInt(userInput.substring(7)) - 1;
                    this.unmark(index);
                } else if (userInput.matches("delete \\d+")) {
                    int index = Integer.parseInt(userInput.substring(7)) - 1;
                    this.delete(index);
//                    Three cases above can be combined
                } else if (userInput.startsWith("todo ")) {
                    String description = userInput.substring(5);
                    this.store(description, TaskList.TaskType.TODO);
                    this.storage.save(tasks);
                } else if (userInput.startsWith("deadline ")) {
                    String description = userInput.substring(9);
                    this.store(description, TaskList.TaskType.DEADLINE);
                    this.storage.save(tasks);
                } else if (userInput.startsWith("event ")) {
                    String description = userInput.substring(6);
                    this.store(description, TaskList.TaskType.EVENT);
                    this.storage.save(tasks);
                } else {
                    ui.showUnknownCommandMessage();
                }
            } catch (DateTimeParseException e) {
                ui.showDatetimeError();
            } catch (Exception e) {
                ui.showChatbotErrorMessage(e);
            }
        }
    }

    public static void main(String[] args) {
        DiligentPenguin chatbot = new DiligentPenguin("src/main/data/","tasks.txt");
        chatbot.run();
    }
}
