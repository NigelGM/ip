package nimbus.command;

import nimbus.exception.NimbusException;
import nimbus.task.Task;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Marks a task as done.
 */
public class MarkCommand extends Command {
    private final int userIndex;

    public MarkCommand(int userIndex) {
        this.userIndex = userIndex;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) throws NimbusException {
        Task t = tasks.markTaskAsDone(userIndex);
        // Fixes 'showMarked' warning
        return ui.showMarked(t);
    }
}


