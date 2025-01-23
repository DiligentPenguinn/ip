import java.util.Objects;
import java.util.Scanner;

public class DiligentPenguin {
    static MyList list = new MyList();
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
        list.add(userInput);
    }

    public static void list() {
        System.out.println(horizontalLines);
        System.out.println("Here is the list of items I noted down");
        System.out.println(list.toString());
        System.out.println(horizontalLines);
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
            }

            DiligentPenguin.store(userInput);
        }

    }
}
