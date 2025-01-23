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

    public static void store(String userInput) {
        System.out.println(horizontalLines);
        System.out.println("Noted. I will write this down for you!");
        System.out.println(horizontalLines);
        tasks.add(userInput);
    }

    public static void list() {
        System.out.println(horizontalLines);
        System.out.println("Here is the list of items I noted down");
        System.out.println(tasks.toString());
        System.out.println(horizontalLines);
    }

    public static void mark(int i) {
        System.out.println("Noted! I'll mark task " + (i + 1) + " as done: ");
        tasks.finish(i);
        System.out.println(tasks.get(i).toString());
    }

    public static void unmark(int i) {
        System.out.println("Noted! I'll unmark task " + (i + 1) + " as undone: ");
        tasks.unfinish(i);
        System.out.println(tasks.get(i).toString());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DiligentPenguin.greet();

        while (true) {
            String userInput = scanner.nextLine();

            if (Objects.equals(userInput, "bye")) {
                DiligentPenguin.exit();
                break;
            } else if (Objects.equals(userInput, "list")) {
                DiligentPenguin.list();
                continue;
                // Use of Regex below is adapted from a conversation with chatGPT
            } else if (userInput.matches("mark \\d+")) {
                int index = Integer.parseInt(userInput.substring(5)) - 1;
                DiligentPenguin.mark(index);
                continue;
            } else if (userInput.matches("unmark \\d+")) {
                int index = Integer.parseInt(userInput.substring(7)) - 1;
                DiligentPenguin.unmark(index);
                continue;
            }

            DiligentPenguin.store(userInput);
        }

    }
}
