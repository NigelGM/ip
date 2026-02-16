package nimbus.command;

import nimbus.exception.NimbusException;
import nimbus.task.Task;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Marks a task (by its user-visible index) as done.
 */
public class MarkCommand extends Command {
    private final int userIndex;

    /**
     * Creates a mark command.
     *
     * @param userIndex The 1-based index provided by the user.
     */
    public MarkCommand(int userIndex) {
        this.userIndex = userIndex;
    }

    /**
     * Marks the specified task as done and shows a confirmation message.
     *
     * @param tasks The task list containing the task.
     * @param ui    The UI used to show feedback.
     * @return
     * @throws NimbusException If the index is invalid.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws NimbusException {
        // Refactored for SLAP: TaskList now handles the internal marking logic
        Task t = tasks.markTaskAsDone(userIndex);
        ui.showMarked(t);
        return null;
    }
}


