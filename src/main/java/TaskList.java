import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.time.LocalDate;

public class TaskList {
    public enum TaskType {
            TODO, DEADLINE, EVENT
    }
    ArrayList<Task> list;
    public TaskList() {
        this.list = new ArrayList<>();
    }

    public void add(Task task) {
        this.list.add(task);
    }

    public void add(String item, TaskType type) throws ChatBotException, DateTimeParseException {
        Task task = new Task("");
        if (type.equals(TaskType.DEADLINE)) {
            if (!item.contains("/by")) {
                throw new ChatBotException("For deadline command, format your command like this: \n"
                        + "event <name> /by <deadline> \n");
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

            LocalDate formattedDeadline = LocalDate.parse(deadline, Task.inputFormatter);
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
                throw new ChatBotException("Did you forget to specify name " +
                        "or start time / end time of the task?");
            }
            LocalDate formattedStartTime = LocalDate.parse(startTime, Task.inputFormatter);
            LocalDate formattedEndTime = LocalDate.parse(endTime, Task.inputFormatter);

            task = new Event(name, formattedStartTime, formattedEndTime);
        } else if (type.equals(TaskType.TODO)) {
            if (item.trim().isEmpty()) {
                throw new ChatBotException("Did you forget to specify name of the task?");
            }
            task = new ToDo(item);
        }
        this.list.add(task);
    }

    public void remove(int i) {
        this.list.remove(i);
    }

    public Boolean isEmpty() {
        return this.list.isEmpty();
    }

    public Task get(int i) {
        return this.list.get(i);
    }

    public void finish(int i) {
        this.list.get(i).setDone();
    }

    public void unfinish(int i) {
        this.list.get(i).setUnDone();
    }

    public int getSize() {
        return this.list.size();
    }

    public ArrayList<Task> getAllTasks() {
        return this.list;
    }

    @Override
    public String toString() {
        StringBuilder listString = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            listString.append(i + 1).append(". ").append(list.get(i)).append("\n");
        }
        return listString.toString();
    }
}
