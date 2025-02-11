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
