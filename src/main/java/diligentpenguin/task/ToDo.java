package diligentpenguin.task;

/**
 * Represent a todo task. A <code>ToDo</code> object has all attributes of a <code>Task</code> object.
 */
public class ToDo extends Task {
    public ToDo(String name) {
        super(name);
        this.type = "T";
    }

    @Override
    public String toString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("[%s][%s] %s", this.type, mark, this.name);
    }

    @Override
    public String toSavedString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("%s | %s | %s", this.type, mark, this.name);
    }
}
