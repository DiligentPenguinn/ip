public class ToDo extends Task {
    public ToDo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("[T][%s] %s", mark, this.name);
    }
}
