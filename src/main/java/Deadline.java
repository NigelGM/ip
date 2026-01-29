import java.time.LocalDateTime;

public class Deadline extends Task {
    private final LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(TaskType.DEADLINE, description);
        this.by = by;
    }

    @Override
    public String toStorageString() {
        // Store as ISO so it can be parsed back reliably: 2019-12-02T18:00
        return getType().getIcon() + " | " + (isDone() ? "1" : "0")
                + " | " + getDescription() + " | " + by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + DateTimeUtil.format(by) + ")";
    }
}
