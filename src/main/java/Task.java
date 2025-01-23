public class Task {
    String name = "";
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

    @Override
    public String toString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("[%s] %s", mark, this.name);
    }
}
