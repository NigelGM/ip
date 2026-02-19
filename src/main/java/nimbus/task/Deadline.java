package nimbus.task;

import java.time.LocalDateTime;
import nimbus.parser.DateTimeUtil;

/**
 * Represents a task that must be completed by a specific time.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Creates a deadline task with a specific completion status.
     * Used by UpdateCommand or Storage loading to preserve the 'done' state.
     *
     * @param description Task description.
     * @param by          Deadline time.
     * @param isDone      The completion status of the task.
     */
    public Deadline(String description, LocalDateTime by, boolean isDone) {
        super(TaskType.DEADLINE, description);
        this.by = by;
        if (isDone) {
            markAsDone();
        }
    }

    /**
     * Retrieves the deadline date and time.
     *
     * @return The LocalDateTime of the deadline.
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns the storage string representation of the Deadline.
     * Format: {@code D | 0 | description | yyyy-MM-dd HHmm}
     */
    @Override
    public String toStorageString() {
        // Appends the formatted date to the base storage string
        return super.toStorageString() + " | " + DateTimeUtil.formatForStorage(by);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + DateTimeUtil.formatForDisplay(by) + ")";
    }
}


