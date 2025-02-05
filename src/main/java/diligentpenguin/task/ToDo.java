package diligentpenguin.task;


/**
 * Represent a todo task. A <code>ToDo</code> object has all attributes of a <code>Task</code> object.
 */
public class ToDo extends Task {
    public ToDo(String name) {
        super(name, "T");
    }

    @Override
    public String toString() {
        String mark = this.isDone() ? "X" : " ";
        return String.format("[%s][%s] %s", this.getType(), mark, this.getName());
    }

    @Override
    public String toSavedString() {
        String mark = this.isDone() ? "X" : " ";
        return String.format("%s | %s | %s", this.getType(), mark, this.getName());
    }
}
