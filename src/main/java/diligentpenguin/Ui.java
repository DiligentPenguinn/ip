package diligentpenguin;

/**
 * Handle UI operations for chatbot.
 * A <code>Ui</code> object shows responses and error messages to the user.
 */
public class Ui {
    private String horizontalLines = "-----------------------------------------------";

    /**
     * Generates a greeting message when the chatbot starts
     * @param name Name of the chatbot
     * @return Greeting message
     */
    public String generateGreetMessage(String name) {
        return String.format("Hello there! My name is %s \nTell me what you want to do! \n", name);
    }

    /**
     * Generates an exit message when the chatbot closes
     * @return Exit message
     */
    public String generateExitMessage() {
        return "Bye bye. Come back to me soon!\n";
    }

    /**
     * Generates a message confirming the task has been stored
     * @param size Number of tasks after storing
     * @return Store confirmation message
     */
    public String generateStoreMessage(int size) {
        return "Noted. I will write this down for you!\n"
                + String.format("I have noted down a total of %d tasks for you \n", size);
    }

    /**
     * Generates a message listing all tasks stored
     * @param tasks String representation of all tasks stored
     * @return List of tasks message
     */
    public String generateListMessage(String tasks) {
        return "Here is the list of items I noted down\n" + tasks + "\n";
    }

    /**
     * Generates a message confirming the task has been marked
     * @param task Task to mark
     * @param i Index of task to mark
     * @return Mark confirmation message
     */
    public String generateMarkMessage(String task, int i) {
        return "Noted! I'll mark task " + (i + 1) + " as done: \n" + task;
    }

    /**
     * Generates a message confirming the task has been unmarked
     * @param task Task to unmark
     * @param i Index of task to unmark
     * @return Unmark confirmation message
     */
    public String generateUnmarkMessage(String task, int i) {
        return "Noted! I'll unmark task " + (i + 1) + " as undone: \n" + task;
    }

    /**
     * Generates a message confirming the task has been deleted
     * @param task Task to delete
     * @param i Index of task to delete
     * @return Delete confirmation message
     */
    public String generateDeleteMessage(String task, int i) {
        return "Noted! I'll delete task " + (i + 1) + " from the list: \n" + task;
    }

    public String generateUpdateMessage() {
        return "Noted! I'll update this task, please edit the task and send it to me.";
    }

    /**
     * Generates a message confirming the task list has been successfully loaded
     * @param tasks Loaded tasks string
     * @return Load success message
     */
    public String generateLoadSuccessMessage(String tasks) {
        return "I have successfully loaded the previous task list for you!\n" + generateListMessage(tasks);
    }

    public String generateUpdateSuccessMessage(String tasks) {
        return "I have updated successfully! Here's the tasks again: \n" + tasks;
    }

    /**
     * Generates a message showing matching tasks given keywords
     * @param tasks Tasks string that matches keywords
     * @return Matching tasks message
     */
    public String generateMatchingTasks(String tasks) {
        return "I have found tasks matching your description!\n" + tasks;
    }

    /**
     * Generates a message showing no stored task list data found
     * @return No data message
     */
    public String generateNoDataMessage() {
        return "It seems that you have no prior task list stored.\nI will start with a blank new task list!\n";
    }

    /**
     * Generates a message indicating no matching tasks were found
     * @return No tasks found message
     */
    public String generateNoTasksFoundMessage() {
        return "It seems that there's no tasks that match your description!\n";
    }

    /**
     * Generates a message indicating no file was found
     * @return File not found message
     */
    public String generateFileNotFoundError() {
        return "Oops! I couldn't find the data file of previous tasks\nI will start with a blank new task list!\n";
    }

    /**
     * Generates a general error message along with the exception
     * @param e exception to display
     * @return Error message
     */
    public String generateChatbotErrorMessage(Exception e) {
        return "Oops! Something went wrong\n" + e.getMessage();
    }

    /**
     * Generates an error message related to date time
     * @return Date time error message
     */
    public String generateDatetimeError() {
        return "Oops! There seems to be an error\nThe date time format for input is: dd/MM/yyyy. Please try again!";
    }

    /**
     * Generates a message showing an unknown command
     * @return Unknown command message
     */
    public String generateUnknownCommandMessage() {
        return "Uuh, I don't know what you mean";
    }

}
