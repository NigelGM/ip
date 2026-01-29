public class Event extends Task {
    private final String from;
    private final String to;

    public Event(String description, String from, String to) {
        super(TaskType.EVENT, description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toStorageString() {
        return getType().getIcon() + " | " + (isDone() ? "1" : "0")
                + " | " + getDescription() + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}


