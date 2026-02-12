package nimbus.command;

import nimbus.exception.NimbusException;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Represents an executable user command in Nimbus.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param tasks task list to operate on
     * @param ui ui to show messages
     * @throws NimbusException if command execution fails
     */
    public abstract void execute(TaskList tasks, Ui ui) throws NimbusException;

    /**
     * Returns whether this command signals the program to exit.
     *
     * @return true only if exit after this command
     */
    public boolean isExit() {
        return false;
    }
}



