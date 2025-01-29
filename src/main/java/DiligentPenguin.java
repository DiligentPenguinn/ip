import javax.imageio.IIOException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DiligentPenguin {
    public Storage storage;
    static TaskList tasks = new TaskList();
    static String tasksDirectoryPath = "src/main/data/";
    static String tasksFilePath = tasksDirectoryPath + "tasks.txt";
    static String horizontalLines = "-----------------------------------------------";
    static String name = "DiligentPenguin";

    public DiligentPenguin(String directoryPath, String fileName) {
        String filePath = directoryPath + fileName;
        this.storage = new Storage(directoryPath, filePath);
    }

    public void greet() {
        System.out.println(horizontalLines);
        System.out.printf("Hello there! My name is %s \nTell me what you want to do! \n", name);
        System.out.println(horizontalLines);
    }

    public void exit() {
        System.out.println(horizontalLines);
        System.out.println("Bye bye. Come back to me soon!");
        System.out.println(horizontalLines);
    }

    public void echo(String userInput) {
        System.out.println(horizontalLines);
        System.out.println(userInput);
        System.out.println(horizontalLines);
    }

    public void store(String userInput, TaskList.TaskType type) throws ChatBotException, DateTimeParseException {
        System.out.println(horizontalLines);
        System.out.println("Noted. I will write this down for you!");
        tasks.add(userInput, type);
        System.out.printf("I have noted down a total of %d tasks for you \n", tasks.getSize());
        System.out.println(horizontalLines);
    }

    public void list() {
        System.out.println(horizontalLines);
        System.out.println("Here is the list of items I noted down");
        System.out.println(tasks.toString());
        System.out.println(horizontalLines);
    }



    public void createSavedDirectoryAndFile() {
        File directory = new File(tasksDirectoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(tasksFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Oops! Something went wrong!");
            }
        }
    }

    public void mark(int i) throws ChatBotException {
        try {
            tasks.finish(i);
            System.out.println("Noted! I'll mark task " + (i + 1) + " as done: ");
            System.out.println(tasks.get(i).toString());
        } catch (IndexOutOfBoundsException e) {
            throw new ChatBotException("That's not a valid item index!");
        }

    }

    public void unmark(int i) throws ChatBotException {
        try {
            System.out.println("Noted! I'll unmark task " + (i + 1) + " as undone: ");
            tasks.unfinish(i);
            System.out.println(tasks.get(i).toString());
        } catch (IndexOutOfBoundsException e) {
            throw new ChatBotException("That's not a valid item index!");
        }

    }

    public void delete(int i) throws ChatBotException {
        try {
            System.out.println("Noted! I'll delete task " + (i + 1) + " from the list: ");
            Task task = tasks.get(i);
            tasks.remove(i);
            System.out.println(task.toString());
        } catch (IndexOutOfBoundsException e) {
            throw new ChatBotException("That's not a valid item index!");
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        this.greet();
        try {
            this.storage.loadTaskList(tasks);
            this.storage.save(tasks);
            System.out.println("I have successfully loaded previous task list for you!");
            this.list();
        } catch (ChatBotException e) {
            System.out.println("It seems that you have no prior task list stored.");
            System.out.println("I will start with a blank new task list!");
            System.out.println(horizontalLines);
        } catch (FileNotFoundException e) {
            System.out.println("Oops! I couldn't find the data file of previous tasks");
            System.out.println("I will start with a blank new task list!");
            this.createSavedDirectoryAndFile();
            System.out.println(horizontalLines);
        }

        while (true) {
            String userInput = scanner.nextLine();
            try {
                if (Objects.equals(userInput, "bye")) {
                    this.exit();
                    break;
                } else if (Objects.equals(userInput, "list")) {
                    this.list();
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
                    System.out.println("Uuh, I don't know what you mean");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Oops! There seems to be an error");
                System.out.println("The date time format for input is: dd/MM/yyyy. Please try again!");
            } catch (Exception e) {
                System.out.println("Oops! There seems to be an error");
                System.out.println(e.getMessage());
                System.out.println("You can try again!");
                System.out.println(horizontalLines);
            }
        }
    }

    public static void main(String[] args) {
        DiligentPenguin chatbot = new DiligentPenguin("src/main/data/","tasks.txt");
        chatbot.run();
    }
}
