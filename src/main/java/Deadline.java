public class Deadline extends Task {
    String deadline = "";
    public Deadline(String name, String deadline) {
        super(name);
        this.type = "D";
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("[%s][%s] %s (by: %s)", this.type, mark, this.name, this.deadline);
    }

    @Override
    public String toSavedString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("%s | %s | %s | %s", this.type, mark, this.name, this.deadline);
    }
}
