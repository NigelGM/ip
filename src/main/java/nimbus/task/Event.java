package nimbus.task;

import java.time.LocalDateTime;
import java.util.Objects;

import nimbus.exception.NimbusException;
import nimbus.parser.DateTimeUtil;

/**
 * Represents an event task with a specific start and end time.
 * <p>
 * This class includes logical validation to ensure that the event's end time
 * does not occur before or at the same time as its start time.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs an {@code Event} task with chronological validation.
     *
     * @param description The description of the event, must not be null.
     * @param from        The starting {@link LocalDateTime} of the event, must not be null.
     * @param to          The ending {@link LocalDateTime} of the event, must not be null.
     * @param isDone      The initial completion status of the task.
     * @throws NimbusException      If the end time is before or equal to the start time.
     * @throws NullPointerException If description, from, or to is null.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to, boolean isDone)
            throws NimbusException {
        super(TaskType.EVENT, Objects.requireNonNull(description, "Description cannot be null"));

        this.from = Objects.requireNonNull(from, "Start time (from) cannot be null");
        this.to = Objects.requireNonNull(to, "End time (to) cannot be null");

        if (!to.isAfter(from)) {
            throw new NimbusException("Time paradox! The end time must be later than the start time.");
        }

        if (isDone) {
            markAsDone();
        }
    }

    /**
     * Returns the start time of the event.
     *
     * @return The {@link LocalDateTime} representing the event start.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end time of the event.
     *
     * @return The {@link LocalDateTime} representing the event end.
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Converts the event into a format suitable for file storage.
     *
     * @return A pipe-delimited string representing the event's state.
     */
    @Override
    public String toStorageString() {
        return String.format("%s | %s | %s",
                super.toStorageString(),
                DateTimeUtil.formatForStorage(from),
                DateTimeUtil.formatForStorage(to));
    }

    /**
     * Returns a string representation of the event for display in the GUI.
     *
     * @return A formatted string including type, status, description, and time range.
     */
    @Override
    public String toString() {
        return String.format("%s (from: %s to: %s)",
                super.toString(),
                DateTimeUtil.formatForDisplay(from),
                DateTimeUtil.formatForDisplay(to));
    }
}




