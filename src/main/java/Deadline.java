public class Deadline extends Task {
    private final String by;

    public Deadline(String description, String by) {
        super(TaskType.DEADLINE, description);
        this.by = by;
    }

    @Override
    public String toStorageString() {
        return getType().getIcon() + " | " + (isDone() ? "1" : "0")
                + " | " + getDescription() + " | " + by;
    }


    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}

