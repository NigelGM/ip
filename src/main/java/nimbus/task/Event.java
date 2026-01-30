package nimbus.task;

import nimbus.parser.DateTimeUtil;

import java.time.LocalDateTime;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

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

    @Override
    public String toString() {
        return super.toString()
                + " (from: " + DateTimeUtil.formatForDisplay(from)
                + " to: " + DateTimeUtil.formatForDisplay(to) + ")";
    }
}




