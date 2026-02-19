package nimbus.command;

import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Represents a command to display a help message to the user.
 * <p>
 * This command provides a summary of available commands and their usage
 * to assist users who are unsure of how to interact with the application.
 */
public class HelpCommand extends Command {

    /**
     * Executes the help command by returning a guide string.
     *
     * @param tasks The list of tasks (unused).
     * @param ui    The user interface (unused).
     * @return A string containing the list of available commands and usage examples.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) {
        return """
                Lost in the clouds? Here's how to navigate:
                1. todo <desc> - Add a simple task
                2. deadline <desc> /by <time> - Add a task with a deadline
                3. event <desc> /from <time> /to <time> - Add a timed event
                4. list - See all your tasks
                5. mark/unmark <index> - Change task status
                6. delete <index> - Remove a task
                7. find <keyword> - Search for tasks
                8. bye - Exit the application
                """;
    }

    /**
     * Checks if this command terminates the application.
     *
     * @return false, as help does not exit the app.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
