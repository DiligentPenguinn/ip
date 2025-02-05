package diligentpenguin.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import diligentpenguin.ChatBotException;

/**
 * Represents a list of <code>Task</code> objects.
 */
public class TaskList {
    private ArrayList<Task> list;

    public TaskList() {
        this.list = new ArrayList<>();
    }

    /**
     * Add a task to the list
     * @param task Task to add
     */
    public void add(Task task) {
        this.list.add(task);
    }

    /**
     * Add a task to the list based on its text description
     * @param item Text description of the task
     * @param type Type of the tas
     * @throws ChatBotException If the input format is incorrect
     * @throws DateTimeParseException If the datetime format is incorrect
     */
    public void add(String item, TaskType type) throws ChatBotException, DateTimeParseException {
        Task task = null;
        if (type.equals(TaskType.DEADLINE)) {
            if (!item.contains("/by")) {
                throw new ChatBotException("""
                        For deadline command, format your command like this:\s
                        event <name> /by <deadline>\s
                        """);
            }
            // The piece of code in between is inspired by a conversation with chatGPT
            int index = item.indexOf("/by");

            // Extract the part before "/by"
            String name = item.substring(0, index).trim();

            // Extract the part after "/by"
            String deadline = item.substring(index + 3).trim();
            // The piece of code in between is inspired by a conversation with chatGPT

            if (name.isEmpty() || deadline.isEmpty()) {
                throw new ChatBotException("Did you forget to specify name or deadline of the task?");
            }

            LocalDate formattedDeadline = LocalDate.parse(deadline, Task.getInputFormatter());
            task = new Deadline(name, formattedDeadline);
        } else if (type.equals(TaskType.EVENT)) {
            if (!item.contains("/from") || !item.contains("/to")) {
                throw new ChatBotException("For event command, format your command like this: \n"
                        + "event <name> /from <startTime> /to <endTime> \n");
            }
            int indexFrom = item.indexOf("/from");
            int indexTo = item.indexOf("/to");

            String name = item.substring(0, indexFrom).trim();
            String startTime = item.substring(indexFrom + 5, indexTo).trim();
            String endTime = item.substring(indexTo + 3).trim();

            if (name.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                throw new ChatBotException("Did you forget to specify name "
                        + "or start time / end time of the task?");
            }
            LocalDate formattedStartTime = LocalDate.parse(startTime, Task.getInputFormatter());
            LocalDate formattedEndTime = LocalDate.parse(endTime, Task.getInputFormatter());

            task = new Event(name, formattedStartTime, formattedEndTime);
        } else if (type.equals(TaskType.TODO)) {
            if (item.trim().isEmpty()) {
                throw new ChatBotException("Did you forget to specify name of the task?");
            }
            task = new ToDo(item);
        }
        this.list.add(task);
    }

    /**
     * Find the matching tasks given the keyword
     * @param keyword The keyword to match
     * @return The list of matching tasks
     */
    public TaskList find(String keyword) {
        TaskList filteredTasks = new TaskList();
        for (Task task: this.list) {
            if (task.getName().contains(keyword)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    /**
     * Remove a task from the list
     * @param i Index of task to remove
     */
    public void remove(int i) {
        this.list.remove(i);
    }

    /**
     * Check if the list is empty
     * @return True if the list is empty, otherwise False
     */
    public Boolean isEmpty() {
        return this.list.isEmpty();
    }

    /**
     * Get a task from the list by index
     * @param i Index of the task
     * @return The task from the specified index
     */
    public Task get(int i) {
        return this.list.get(i);
    }

    /**
     * Mark a task in the list as completed
     * @param i Index of the task to mark
     */
    public void finish(int i) {
        this.list.get(i).setDone();
    }

    /**
     * Mark a task in the list as uncompleted
     * @param i Index of the task to mark
     */
    public void unfinish(int i) {
        this.list.get(i).setUnDone();
    }

    /**
     * Get the number of tasks in the list
     * @return Number of tasks in the list
     */
    public int getSize() {
        return this.list.size();
    }

    /**
     * Get all tasks in the list
     * @return An <code>ArrayList</code> of tasks
     */
    public ArrayList<Task> getAllTasks() {
        return this.list;
    }

    /**
     * Returns a string representation of the task list.
     * @return A formatted string representing all tasks in the list.
     */
    @Override
    public String toString() {
        StringBuilder listString = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            listString.append(i + 1).append(". ").append(list.get(i)).append("\n");
        }
        return listString.toString();
    }

    /**
     * Represents different types of task that can be created
     */
    public enum TaskType {
        TODO, DEADLINE, EVENT
    }
}
