package nimbus.command;

import java.time.LocalDateTime;
import nimbus.exception.NimbusException;
import nimbus.ui.Ui;
import nimbus.task.TaskList;
import nimbus.task.Event;

/**
 * Adds an {@link Event} task into the task list.
 */
public class AddEventCommand extends Command {
    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates an add-event command.
     *
     * @param description Description of the event.
     * @param from        Event start date-time.
     * @param to          Event end date-time.
     */
    public AddEventCommand(String description, LocalDateTime from, LocalDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    /**
     * Adds the event task to the task list and shows a confirmation message.
     *
     * @param tasks The task list to add into.
     * @param ui    The UI used to show feedback.
     * @return
     * @throws NimbusException If the task cannot be added.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws NimbusException {
        // Guard Clause: Ensure logical consistency before proceeding
        if (to.isBefore(from)) {
            throw new NimbusException("The end time cannot be before the start time!");
        }

        Event e = new Event(description, from, to);
        int size = tasks.add(e);
        ui.showAdded(e, size);
        return null;
    }
}

