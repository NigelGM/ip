public abstract class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    protected abstract String getTypeIcon();

    protected String getExtraInfo() {
        return "";
    }

    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description + getExtraInfo();
    }
}



