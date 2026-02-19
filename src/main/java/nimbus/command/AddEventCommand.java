package nimbus.command;

import java.time.LocalDateTime;

import nimbus.exception.NimbusException;
import nimbus.task.Event;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Adds an {@link Event} task into the task list.
 */
public class AddEventCommand extends Command {
    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs a command to add an event task.
     */
    public AddEventCommand(String description, LocalDateTime from, LocalDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) throws NimbusException {
        if (to.isBefore(from)) {
            throw new NimbusException("The end time cannot be before the start time!");
        }
        Event e = new Event(description, from, to, false);
        int size = tasks.add(e);
        return ui.showAdded(e, size); // Fixes 'showAdded' warning
    }
}

