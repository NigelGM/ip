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

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public String getTypeIcon() {
        return type.getIcon();
    }
    public boolean isDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    public TaskType getType() {
        return type;
    }

    public String toStorageString() {
        return type.getIcon() + " | " + (isDone ? "1" : "0") + " | " + description;
    }


    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description;
    }
}




