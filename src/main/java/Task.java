public class Task {
    String name = "";
    Boolean isDone = false;
    public Task(String name) {
        this.name = name;
    }

    public void setDone() {
        this.isDone = true;
    }

    @Override
    public String toString() {
        String mark = this.isDone ? "X" : " ";
        return "[" + mark + "] " + this.name;
    }
}
