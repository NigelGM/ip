package nimbus.task;

public abstract class Task {
    private final String description;
    private boolean isDone;
    private final TaskType type;

    protected Task(TaskType type, String description) {
        this.type = type;
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    // ✅ use isDone()
    public String getStatusIcon() {
        return isDone() ? "X" : " ";
    }

    // ✅ use getType()
    public String getTypeIcon() {
        return getType().getIcon();
    }

    // (this is the one IntelliJ warns about as "never used")
    public boolean isDone() {
        return isDone;
    }

    // (warns "never used")
    public String getDescription() {
        return description;
    }

    // (warns "never used")
    public TaskType getType() {
        return type;
    }

    // ✅ use getType(), isDone(), getDescription()
    public String toStorageString() {
        return getType().getIcon() + " | " + (isDone() ? "1" : "0") + " | " + getDescription();
    }

    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + getDescription();
    }
}





