public class DiligentPenguin {
    static String horizontalLines = "-----------------------------------------------";
    static String name = "DiligentPenguin";
    public static void greet() {
        System.out.println(horizontalLines);
        System.out.printf("Hello! I'm %s \nWhat can I do for you? \n", name);
        System.out.println(horizontalLines);
    }

    public static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(horizontalLines);
    }

    public static void main(String[] args) {
        DiligentPenguin.greet();
        DiligentPenguin.exit();
    }
}
