package nimbus.command;

import nimbus.exception.NimbusException;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Represents an executable command in the Nimbus application.
 * Commands operate on the task list and return a result string to the UI.
 */
public abstract class Command {

    /**
     * Executes the command using the provided task list and user interface.
     * <p>
     * Note: This method does not handle storage saving. The caller (e.g., Nimbus.java)
     * is responsible for saving the task list state after execution.
     *
     * @param tasks The list of tasks to operate on.
     * @param ui    The UI instance to generate response messages.
     * @return A string representing the result of the command execution.
     * @throws NimbusException If an error occurs during execution (e.g., invalid index).
     */
    public abstract String execute(TaskList tasks, Ui ui) throws NimbusException;

    /**
     * Checks if this command should terminate the application.
     *
     * @return {@code true} if the application should exit; {@code false} otherwise.
     */
    public boolean isExit() {
        return false;
    }
}



