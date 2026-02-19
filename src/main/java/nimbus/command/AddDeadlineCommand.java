package nimbus.command;

import java.time.LocalDateTime;

import nimbus.exception.NimbusException;
import nimbus.task.Deadline;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Adds a {@link Deadline} task into the task list.
 */
public class AddDeadlineCommand extends Command {
    private final String description;
    private final LocalDateTime by;

    /**
     * Executes the command to add a deadline task.
     */
    public AddDeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) throws NimbusException {
        Deadline d = new Deadline(description, by, false);
        int size = tasks.add(d);
        return ui.showAdded(d, size);
    }
}
