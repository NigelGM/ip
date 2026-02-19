package nimbus.task;

/**
 * Represents a generic task in Nimbus.
 * <p>
 * Each task has a description, completion state, and a {@link TaskType}.
 * Subclasses may add extra fields (e.g. deadline date/time, event duration).
 */
public abstract class Task {
    private final String description;
    private boolean isDone;
    private final TaskType type;

    /**
     * Constructs a Task with the given type and description.
     *
     * @param type        Task type.
     * @param description Task description.
     */
    protected Task(TaskType type, String description) {
        this.type = type;
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Returns the status icon used in UI display.
     *
     * @return {@code "X"} if done, otherwise {@code " "}.
     */
    public String getStatusIcon() {
        return isDone() ? "X" : " ";
    }

    /**
     * Returns the icon of this task's type.
     *
     * @return Type icon (e.g. {@code "T"}, {@code "D"}, {@code "E"}).
     */
    public String getTypeIcon() {
        return getType().getIcon();
    }

    /**
     * Returns whether this task is completed.
     *
     * @return True if done, otherwise false.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the task description.
     *
     * @return Description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the task type.
     *
     * @return Task type.
     */
    public TaskType getType() {
        return type;
    }

    /**
     * Converts this task into a string suitable for storing in the save file.
     * <p>
     * Base format: {@code TYPE | DONE_STATUS | DESCRIPTION}
     * (e.g., "T | 1 | read book")
     * </p>
     *
     * @return Storage string format.
     */
    public String toStorageString() {
        return getType().getIcon() + " | " + (isDone() ? "1" : "0") + " | " + getDescription();
    }

    /**
     * Returns the UI display string of the task.
     *
     * @return Formatted task string.
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + getDescription();
    }
}





