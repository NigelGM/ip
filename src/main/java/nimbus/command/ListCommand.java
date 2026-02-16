package nimbus.command;

import nimbus.ui.Ui;
import nimbus.task.TaskList;

/**
 * Lists all tasks currently stored in the task list.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by displaying all tasks.
     *
     * @param tasks The task list to display.
     * @param ui    The UI used to show the task list.
     * @return
     */
    @Override
    public String execute(TaskList tasks, Ui ui) {
        ui.showList(tasks);
        return null;
    }

}

