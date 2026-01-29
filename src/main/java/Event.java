import java.time.LocalDateTime;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
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
        return super.toString()
                + " (from: " + DateTimeUtil.format(from)
                + " to: " + DateTimeUtil.format(to) + ")";
    }
}



