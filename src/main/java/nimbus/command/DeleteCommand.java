package nimbus.command;

import nimbus.task.TaskList;
import nimbus.task.Task;
import nimbus.exception.NimbusException;
import nimbus.ui.Ui;

/**
 * Deletes a task (by its user-visible index) from the task list.
 */
public class DeleteCommand extends Command {
    private final int oneBasedIndex;

    /**
     * Creates a delete command.
     *
     * @param oneBasedIndex The 1-based index provided by the user.
     */
    public DeleteCommand(int oneBasedIndex) {
        this.oneBasedIndex = oneBasedIndex;
    }

    /**
     * Removes the specified task from the task list and shows a confirmation message.
     *
     * @param tasks The task list containing the task.
     * @param ui    The UI used to show feedback.
     * @throws NimbusException If the index is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws NimbusException {
        // High-level: Just tell the list to delete and the UI to show it
        Task removed = tasks.delete(oneBasedIndex);
        ui.showDeleted(removed, tasks.size());
    }
}


