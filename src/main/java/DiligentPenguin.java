import java.util.Objects;
import java.util.Scanner;

public class DiligentPenguin {
    static String horizontalLines = "-----------------------------------------------";
    static String name = "DiligentPenguin";
    public static void greet() {
        System.out.println(horizontalLines);
        System.out.printf("Hello there! My name is %s \nTell me what you want to do! \n", name);
        System.out.println(horizontalLines);
    }

    public static void exit() {
        System.out.println("Bye bye. Come back to me soon!");
        System.out.println(horizontalLines);
    }

    public static void echo(String userInput) {
        System.out.println(userInput);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DiligentPenguin.greet();

        while (true) {
            String userInput = scanner.nextLine();

            if (Objects.equals(userInput, "bye")) {
                DiligentPenguin.exit();
                break;
            }

            DiligentPenguin.echo(userInput);
        }

    }
}
