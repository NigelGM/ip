package nimbus.command;

import nimbus.exception.NimbusException;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Command to display all tasks to the user.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command and returns the formatted task list.
     * Fixed: Added throws NimbusException to handle UI layer errors.
     *
     * @param tasks The task list.
     * @param ui    The UI to format output.
     * @return The task list as a String.
     * @throws NimbusException If list retrieval fails.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws NimbusException {
        // Now correctly returns the string for the GUI
        return ui.showList(tasks);
    }
}

