import javax.imageio.IIOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DiligentPenguin {
    static TaskList tasks = new TaskList();
    static String tasksDirectoryPath = "src/main/data/";
    static String tasksFilePath = tasksDirectoryPath + "tasks.txt";
    static String horizontalLines = "-----------------------------------------------";
    static String name = "DiligentPenguin";
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

    public void store(String userInput, TaskList.TaskType type) throws ChatBotException {
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

    public void save() throws ChatBotException {
        try {
            FileWriter fw = new FileWriter(tasksFilePath);
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

    private static Task readTask(String line) throws ChatBotException {
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
            task = new Deadline(description, deadline);
            if (isDone) {
                task.setDone();
            }
            return task;
        case "E":
            task = new Event(description, startTime, endTime);
            if (isDone) {
                task.setDone();
            }
            return task;
        default:
            throw new ChatBotException("Oops! It seems that the format of the saved file is wrong/corrupted!");
        }
    }

    public void loadTaskList() throws ChatBotException, FileNotFoundException {
        File file = new File(tasksFilePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            tasks.add(DiligentPenguin.readTask(scanner.nextLine()));
        }
        if (tasks.isEmpty()) {
            throw new ChatBotException("No data found!");
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DiligentPenguin chatBot = new DiligentPenguin();
        chatBot.greet();
        try {
            chatBot.loadTaskList();
            System.out.println("I have successfully loaded previous task list for you!");
            chatBot.list();
        } catch (ChatBotException e) {
            System.out.println("It seems that you have no prior task list stored.");
            System.out.println("I will start with a blank new task list!");
            System.out.println(horizontalLines);
        } catch (FileNotFoundException e) {
            System.out.println("Oops! I couldn't find the data file of previous tasks");
            System.out.println("I will start with a blank new task list!");
            chatBot.createSavedDirectoryAndFile();
            System.out.println(horizontalLines);
        }

        while (true) {
            String userInput = scanner.nextLine();
            try {
                if (Objects.equals(userInput, "bye")) {
                    chatBot.exit();
                    break;
                } else if (Objects.equals(userInput, "list")) {
                    chatBot.list();
                    // Use of Regex below is adapted from a conversation with chatGPT
                } else if (userInput.matches("mark \\d+")) {
                    int index = Integer.parseInt(userInput.substring(5)) - 1;
                    chatBot.mark(index);
                } else if (userInput.matches("unmark \\d+")) {
                    int index = Integer.parseInt(userInput.substring(7)) - 1;
                    chatBot.unmark(index);
                } else if (userInput.matches("delete \\d+")) {
                    int index = Integer.parseInt(userInput.substring(7)) - 1;
                    chatBot.delete(index);
//                    Three cases above can be combined
                } else if (userInput.startsWith("todo ")) {
                    String description = userInput.substring(5);
                    chatBot.store(description, TaskList.TaskType.TODO);
                    chatBot.save();
                } else if (userInput.startsWith("deadline ")) {
                    String description = userInput.substring(9);
                    chatBot.store(description, TaskList.TaskType.DEADLINE);
                    chatBot.save();
                } else if (userInput.startsWith("event ")) {
                    String description = userInput.substring(6);
                    chatBot.store(description, TaskList.TaskType.EVENT);
                    chatBot.save();
                } else {
                    System.out.println("Uuh, I don't know what you mean");
                }
            } catch (Exception e) {
                System.out.println("Error detected!");
                System.out.println(e.getMessage());
                System.out.println("You can try again!");
                System.out.println(horizontalLines);
            }
        }

    }
}
