package nimbus.task;

import nimbus.parser.DateTimeUtil;
import java.time.LocalDateTime;

/**
 * Represents a task that must be completed by a specific time.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Creates a deadline task.
     *
     * @param description Task description.
     * @param by Deadline time string (display/storage form).
     */
    public Deadline(String description, LocalDateTime by) {
        super(TaskType.DEADLINE, description);
        this.by = by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + DateTimeUtil.formatForDisplay(by) + ")";
    }
}


