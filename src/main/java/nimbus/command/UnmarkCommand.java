package nimbus.command;

import nimbus.exception.NimbusException;
import nimbus.task.Task;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Unmarks a task (by its user-visible index), setting it as not done.
 */
public class UnmarkCommand extends Command {
    private final int userIndex;

    /**
     * Creates an unmark command.
     *
     * @param userIndex The 1-based index provided by the user.
     */
    public UnmarkCommand(int userIndex) {
        this.userIndex = userIndex;
    }

    /**
     * Unmarks the specified task and returns a confirmation message.
     *
     * @param tasks The task list containing the task.
     * @param ui    The UI used to show feedback.
     * @return A string confirming the task is unmarked.
     * @throws NimbusException If the index is invalid.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws NimbusException {
        Task t = tasks.unmarkTask(userIndex);
        // Fixes 'showUnmarked' warning
        return ui.showUnmarked(t);
    }
}


