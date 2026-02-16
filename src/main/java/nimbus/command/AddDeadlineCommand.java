package nimbus.command;

import java.time.LocalDateTime;
import nimbus.task.TaskList;
import nimbus.task.Deadline;
import nimbus.ui.Ui;
import nimbus.exception.NimbusException;

/**
 * Adds a {@link Deadline} task into the task list.
 */
public class AddDeadlineCommand extends Command {
    private final String description;
    private final LocalDateTime by;

    /**
     * Creates an add-deadline command.
     *
     * @param description Description of the deadline task.
     * @param by          Deadline date-time.
     */
    public AddDeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    /**
     * Adds the deadline task to the task list and shows a confirmation message.
     *
     * @param tasks The task list to add into.
     * @param ui    The UI used to show feedback.
     * @throws NimbusException If the task cannot be added.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws NimbusException {
        Deadline d = new Deadline(description, by);
        int size = tasks.add(d);
        // FIX: Return the UI string instead of null
        return ui.showAdded(d, size);
    }

}



