import java.util.ArrayList;

public class TaskList {
    ArrayList<Task> list;
    public TaskList() {
        this.list = new ArrayList<>();
    }

    public void add(String item) {
        Task task = new Task(item);
        this.list.add(task);
    }

    public Task get(int i) {
        return this.list.get(i);
    }

    public void finish(int i) {
        this.list.get(i).setDone();
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
