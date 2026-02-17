package nimbus.task;

import java.time.LocalDateTime;
import nimbus.parser.DateTimeUtil;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates an event task with a specific completion status.
     * Used by UpdateCommand to preserve the 'done' state.
     *
     * @param description Event description.
     * @param from        Start time.
     * @param to          End time.
     * @param isDone      The completion status of the task.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to, boolean isDone) {
        super(TaskType.EVENT, description);
        this.from = from;
        this.to = to;
        if (isDone) {
            markAsDone();
        }
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns the storage string representation of the Event.
     * Format: {@code E | 0 | description | start_time | end_time}
     */
    @Override
    public String toStorageString() {
        return super.toStorageString() + " | " +
                DateTimeUtil.formatForStorage(from) + " | " +
                DateTimeUtil.formatForStorage(to);
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + DateTimeUtil.formatForDisplay(from) +
                " to: " + DateTimeUtil.formatForDisplay(to) + ")";
    }
}




