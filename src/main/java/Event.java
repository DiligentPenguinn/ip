public class Event extends Task {
    String startTime = "";
    String endTime = "";

    public Event(String name, String startTime, String endTime) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        String mark = this.isDone ? "X" : " ";
        return String.format("[E][%s] %s (from: %s to: %s)",
                mark, this.name, this.startTime, this.endTime);
    }
}
