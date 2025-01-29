public class Event extends Task {
    String startTime = "";
    String endTime = "";

    public Event(String name, String startTime, String endTime) {
        super(name);
        this.type = "E";
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("[%s][%s] %s (from: %s to: %s)",
                this.type, mark, this.name, this.startTime, this.endTime);
    }

    @Override
    public String toSavedString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("%s | %s | %s | %s | %s",
                this.type, mark, this.name, this.startTime, this.endTime);
    }
}
