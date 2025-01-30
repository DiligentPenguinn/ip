package diligentpenguin;

public class Ui {
    static String horizontalLines = "-----------------------------------------------";

    public void showGreetMessage(String name) {
        System.out.println(horizontalLines);
        System.out.printf("Hello there! My name is %s \nTell me what you want to do! \n", name);
        System.out.println(horizontalLines);
    }

    public void showExitMessage() {
        System.out.println(horizontalLines);
        System.out.println("Bye bye. Come back to me soon!");
        System.out.println(horizontalLines);
    }

    public void showStoreMessage(int size) {
        System.out.println(horizontalLines);
        System.out.println("Noted. I will write this down for you!");
        System.out.printf("I have noted down a total of %d tasks for you \n", size);
        System.out.println(horizontalLines);
    }

    public void showListMessage(String tasks) {
        System.out.println(horizontalLines);
        System.out.println("Here is the list of items I noted down");
        System.out.println(tasks);
        System.out.println(horizontalLines);
    }

    public void showMarkMessage(String task, int i) {
        System.out.println("Noted! I'll mark task " + (i + 1) + " as done: ");
        System.out.println(task);
    }

    public void showUnmarkMessage(String task, int i) {
        System.out.println("Noted! I'll unmark task " + (i + 1) + " as undone: ");
        System.out.println(task);
    }

    public void showDeleteMessage(String task, int i) throws ChatBotException {
        System.out.println("Noted! I'll delete task " + (i + 1) + " from the list: ");
        System.out.println(task);
    }

    public void showLoadSuccessMessage(String tasks) {
        System.out.println("I have successfully loaded previous task list for you!");
        this.showListMessage(tasks);
    }

    public void showNoDataMessage() {
        System.out.println("It seems that you have no prior task list stored.");
        System.out.println("I will start with a blank new task list!");
        System.out.println(horizontalLines);
    }

    public void showFileNotFoundError() {
        System.out.println("Oops! I couldn't find the data file of previous tasks");
        System.out.println("I will start with a blank new task list!");
        System.out.println(horizontalLines);
    }

    public void showChatbotErrorMessage(Exception e) {
        System.out.println("Oops! Something went wrong");
        System.out.println(e.getMessage());
    }

    public void showDatetimeError() {
        System.out.println("Oops! There seems to be an error");
        System.out.println("The date time format for input is: dd/MM/yyyy. Please try again!");
    }

    public void showUnknownCommandMessage() {
        System.out.println("Uuh, I don't know what you mean");
    }
}
