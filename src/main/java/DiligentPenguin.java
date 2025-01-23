import java.util.Objects;
import java.util.Scanner;

public class DiligentPenguin {
    static TaskList tasks = new TaskList();
    static String horizontalLines = "-----------------------------------------------";
    static String name = "DiligentPenguin";
    public static void greet() {
        System.out.println(horizontalLines);
        System.out.printf("Hello there! My name is %s \nTell me what you want to do! \n", name);
        System.out.println(horizontalLines);
    }

    public static void exit() {
        System.out.println(horizontalLines);
        System.out.println("Bye bye. Come back to me soon!");
        System.out.println(horizontalLines);
    }

    public static void echo(String userInput) {
        System.out.println(horizontalLines);
        System.out.println(userInput);
        System.out.println(horizontalLines);
    }

    public static void store(String userInput, String type) throws ChatBotException {
        System.out.println(horizontalLines);
        System.out.println("Noted. I will write this down for you!");
        tasks.add(userInput, type);
        System.out.printf("I have noted down a total of %d tasks for you \n", tasks.getSize());
        System.out.println(horizontalLines);
    }

    public static void list() {
        System.out.println(horizontalLines);
        System.out.println("Here is the list of items I noted down");
        System.out.println(tasks.toString());
        System.out.println(horizontalLines);
    }

    public static void mark(int i) throws ChatBotException {
        try {
            tasks.finish(i);
            System.out.println("Noted! I'll mark task " + (i + 1) + " as done: ");
            System.out.println(tasks.get(i).toString());
        } catch (IndexOutOfBoundsException e) {
            throw new ChatBotException("That's not a valid item index!");
        }

    }

    public static void unmark(int i) throws ChatBotException {
        try {
            System.out.println("Noted! I'll unmark task " + (i + 1) + " as undone: ");
            tasks.unfinish(i);
            System.out.println(tasks.get(i).toString());
        } catch (IndexOutOfBoundsException e) {
            throw new ChatBotException("That's not a valid item index!");
        }

    }

    public static void delete(int i) throws ChatBotException {
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
        DiligentPenguin.greet();

        while (true) {
            String userInput = scanner.nextLine();
            try {
                if (Objects.equals(userInput, "bye")) {
                    DiligentPenguin.exit();
                    break;
                } else if (Objects.equals(userInput, "list")) {
                    DiligentPenguin.list();
                    // Use of Regex below is adapted from a conversation with chatGPT
                } else if (userInput.matches("mark \\d+")) {
                    int index = Integer.parseInt(userInput.substring(5)) - 1;
                    DiligentPenguin.mark(index);
                } else if (userInput.matches("unmark \\d+")) {
                    int index = Integer.parseInt(userInput.substring(7)) - 1;
                    DiligentPenguin.unmark(index);
                } else if (userInput.matches("delete \\d+")) {
                    int index = Integer.parseInt(userInput.substring(7)) - 1;
                    DiligentPenguin.delete(index);
                } else if (userInput.startsWith("todo ")) {
                    String description = userInput.substring(5);
                    DiligentPenguin.store(description, "todo");
                } else if (userInput.startsWith("deadline ")) {
                    String description = userInput.substring(9);
                    DiligentPenguin.store(description, "deadline");
                } else if (userInput.startsWith("event ")) {
                    String description = userInput.substring(6);
                    DiligentPenguin.store(description, "event");
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
