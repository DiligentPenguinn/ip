import java.util.ArrayList;

public class TaskList {
    ArrayList<Task> list;
    public TaskList() {
        this.list = new ArrayList<>();
    }

    public void add(String item, String type) {
        Task task = new Task("");
        // Below piece of code is inspired by a conversation with chatGPT
        if (type.equals("deadline")) {
            int index = item.indexOf("/by");

            // Extract the part before "/by"
            String name = item.substring(0, index);

            // Extract the part after "/by"
            String deadline = item.substring(index + 3);

            task = new Deadline(name, deadline);
        } else if (type.equals("event")) {
            int indexFrom = item.indexOf("/from");
            int indexTo = item.indexOf("/to");

            String name = item.substring(0, indexFrom);
            String startTime = item.substring(indexFrom + 5, indexTo);
            String endTime = item.substring(indexTo + 3);
            task = new Event(name, startTime, endTime);
        } else if (type.equals("todo")) {
            task = new ToDo(item);
        }
        this.list.add(task);
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

    @Override
    public String toString() {
        StringBuilder listString = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            listString.append(i + 1).append(". ").append(list.get(i)).append("\n");
        }
        return listString.toString();
    }
}
