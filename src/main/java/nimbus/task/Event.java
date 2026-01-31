package nimbus.task;

import nimbus.parser.DateTimeUtil;

import java.time.LocalDateTime;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates an event task.
     *
     * @param description Event description.
     * @param from Start time string (display/storage form).
     * @param to End time string (display/storage form).
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(TaskType.EVENT, description); // IMPORTANT: (TaskType, String)
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Converts this event task into a storage string that includes start and end times.
     *
     * @return Storage string format with event duration.
     */
    @Override
    public String toString() {
        return super.toString()
                + " (from: " + DateTimeUtil.formatForDisplay(from)
                + " to: " + DateTimeUtil.formatForDisplay(to) + ")";
    }
}




