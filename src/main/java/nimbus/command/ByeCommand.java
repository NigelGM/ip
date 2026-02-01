package nimbus.command;

import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Exits the program and shows a goodbye message.
 */
public class ByeCommand extends Command {

    /**
     * Shows the goodbye message.
     *
     * @param tasks The task list (not used by this command).
     * @param ui    The UI used to show the goodbye message.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.showBye();
    }

    /**
     * Indicates that the program should exit after executing this command.
     *
     * @return {@code true}.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
