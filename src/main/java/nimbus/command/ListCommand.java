package nimbus.command;

import nimbus.exception.NimbusException;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Lists all tasks currently stored in the task list.
 */
public class ListCommand extends Command {

    @Override
    public String execute(TaskList tasks, Ui ui) throws NimbusException {
        // Fixes the 'showList' warning and empty GUI dialog
        return ui.showList(tasks);
    }
}

