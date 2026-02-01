package nimbus.command;

import nimbus.exception.NimbusException;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Represents an executable user command in Nimbus.
 * <p>
 * Each {@code Command} performs an operation on the application's {@link TaskList}
 * and uses {@link Ui} to display feedback to the user.
 * </p>
 */
public abstract class Command {

    /**
     * Executes this command.
     *
     * @param tasks The task list to operate on.
     * @param ui    The UI used to show messages to the user.
     * @throws NimbusException If execution fails due to invalid inputs or application constraints.
     */
    public abstract void execute(TaskList tasks, Ui ui) throws NimbusException;

    /**
     * Returns whether this command signals the program to exit.
     *
     * @return {@code true} if the program should exit after executing this command; {@code false} otherwise.
     */
    public boolean isExit() {
        return false;
    }
}


