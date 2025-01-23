public class Deadline extends Task {
    String deadline = "";
    public Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("[D][%s] %s (by: %s)", mark, this.name, this.deadline);
    }
}
