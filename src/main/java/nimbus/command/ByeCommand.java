package nimbus.command;

import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Exits the program and returns a goodbye message.
 */
public class ByeCommand extends Command {

    @Override
    public String execute(TaskList tasks, Ui ui) {
        return ui.showBye(); // Ensure you are RETURNING this, not just calling it.
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
