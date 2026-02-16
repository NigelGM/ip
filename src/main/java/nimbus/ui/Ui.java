package nimbus.ui;

import java.util.List;
import java.util.stream.IntStream;

import nimbus.exception.NimbusException;
import nimbus.task.Task;
import nimbus.task.TaskList;

/**
 * Handles all user-facing messages for the Nimbus application.
 * <p>
 * This class provides methods to display feedback to the user and manages a string buffer
 * to facilitate integration with the JavaFX graphical user interface.
 * It supports both standard console output and buffered output for GUI dialog boxes.
 */
public class Ui {

    private final boolean printToConsole;
    private final StringBuilder buffer = new StringBuilder();

    /**
     * Constructs a default Ui instance that prints to the console and buffers output.
     */
    public Ui() {
        this(true);
    }

    /**
     * Constructs an Ui instance with configurable console printing settings.
     *
     * @param printToConsole true to enable System printing, false to only buffer output.
     */
    public Ui(boolean printToConsole) {
        this.printToConsole = printToConsole;
    }

    /**
     * Clears the current buffered output.
     * This should be called before generating a new response to ensure the GUI
     * does not display stale messages.
     */
    public void resetBuffer() {
        buffer.setLength(0);
    }

    /**
     * Returns the accumulated buffered output as a trimmed String.
     *
     * @return The buffered messages formatted for display.
     */
    public String getBufferedOutput() {
        return buffer.toString().stripTrailing();
    }

    /**
     * Internal helper to route messages to both the console and the internal string buffer.
     * * @param msg The message to be displayed.
     */
    private void say(String msg) {
        assert msg != null : "Output message cannot be null";
        if (printToConsole) {
            System.out.println(msg);
        }
        buffer.append(msg).append(System.lineSeparator());
    }

    /**
     * Displays the initial greeting message when the application starts.
     */
    public void showGreeting() {
        say("Hello! I'm Nimbus");
        say("What can I do for you?");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error details to be shown.
     */
    public void showError(String message) {
        assert message != null : "Error message should not be null";
        say("Error: " + message);
    }

    /**
     * Displays a goodbye message when the user exits the application.
     */
    public void showBye() {
        say("Bye. Hope to see you again soon!");
    }

    /**
     * Confirms that a task has been added successfully.
     *
     * @param task The task that was added.
     * @param size The new total number of tasks in the list.
     */
    public void showAdded(Task task, int size) {
        assert task != null : "Added task cannot be null";
        say("Got it. I've added this task:");
        say("  " + task);
        say("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays the full list of tasks.
     * <p>
     * This implementation utilizes {@link IntStream} to iterate through the tasks
     * and follows the Single Level of Abstraction Principle (SLAP) by delegating
     * line formatting to a helper method.
     *
     * @param tasks The TaskList containing tasks to be displayed.
     */
    public void showList(TaskList tasks) {
        assert tasks != null : "TaskList to display cannot be null";
        say("Here are the tasks in your list:");
        IntStream.range(0, tasks.size())
                .forEach(i -> displayTaskAt(tasks, i));
    }

    /**
     * Formats and displays a single task at the specified index.
     *
     * @param tasks The TaskList to retrieve from.
     * @param index The zero-based index of the task.
     */
    private void displayTaskAt(TaskList tasks, int index) {
        try {
            say((index + 1) + ". " + tasks.getByZeroBasedIndex(index));
        } catch (NimbusException e) {
            say((index + 1) + ". [Error retrieving task: " + e.getMessage() + "]");
        }
    }

    /**
     * Confirms that a task has been marked as completed.
     *
     * @param task The task that was marked.
     */
    public void showMarked(Task task) {
        assert task != null : "Marked task cannot be null";
        say("Nice! I've marked this task as done:");
        say("  " + task);
    }

    /**
     * Confirms that a task has been marked as incomplete.
     *
     * @param task The task that was unmarked.
     */
    public void showUnmarked(Task task) {
        assert task != null : "Unmarked task cannot be null";
        say("OK, I've marked this task as not done yet:");
        say("  " + task);
    }

    /**
     * Confirms that a task has been successfully deleted.
     *
     * @param task The task that was removed.
     * @param size The remaining number of tasks in the list.
     */
    public void showDeleted(Task task, int size) {
        assert task != null : "Deleted task cannot be null";
        say("Noted. I've removed this task:");
        say("  " + task);
        say("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays the results of a search operation.
     * <p>
     * Utilizes {@link IntStream} to iterate through matching results.
     *
     * @param keyword The search term used.
     * @param matches The list of tasks matching the keyword.
     */
    public void showFindResults(String keyword, List<Task> matches) {
        assert keyword != null : "Search keyword cannot be null";
        assert matches != null : "Match list cannot be null";

        if (matches.isEmpty()) {
            say("No matching tasks found for \"" + keyword + "\".");
            return;
        }

        say("Here are the matching tasks in your list for \"" + keyword + "\":");
        IntStream.range(0, matches.size())
                .forEach(i -> say((i + 1) + ". " + matches.get(i)));
    }
}







