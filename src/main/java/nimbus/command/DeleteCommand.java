package nimbus.command;

import nimbus.exception.NimbusException;
import nimbus.task.Task;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Deletes a task from the list based on its index.
 */
public class DeleteCommand extends Command {
    private final int oneBasedIndex;

    public DeleteCommand(int oneBasedIndex) {
        this.oneBasedIndex = oneBasedIndex;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) throws NimbusException {
        Task removed = tasks.delete(oneBasedIndex);
        // Fixes the 'showDeleted' warning in Ui.java
        return ui.showDeleted(removed, tasks.size());
    }
}


