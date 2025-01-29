import java.time.format.DateTimeFormatter;

public class Task {
    static DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
    String name = "";
    String type = "";
    Boolean isDone = false;
    public Task(String name) {
        this.name = name;
    }

    public void setDone() {
        this.isDone = true;
    }

    public void setUnDone() {
        this.isDone = false;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("[%s] %s", mark, this.name);
    }

    public String toSavedString() {
        return "";
    }
}
