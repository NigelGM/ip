package nimbus.ui;

import nimbus.exception.NimbusException;
import nimbus.task.Task;
import nimbus.task.TaskList;
import java.util.List;

/**
 * Handles all user-facing messages for the Nimbus application.
 * <p>
 * This class provides methods to format feedback for the user. It supports
 * both direct console printing (for CLI testing) and returning formatted strings
 * (for GUI integration).
 */
public class Ui {

    private final boolean isPrintingToConsole;

    /**
     * Constructs a default {@code Ui} instance that prints to the console.
     */
    public Ui() {
        this(true);
    }

    /**
     * Constructs a {@code Ui} instance with configurable console printing settings.
     *
     * @param isPrintingToConsole {@code true} to enable System printing, {@code false} to behave silently.
     */
    public Ui(boolean isPrintingToConsole) {
        this.isPrintingToConsole = isPrintingToConsole;
    }

    /**
     * Internal helper to print messages to console (if enabled) and return them.
     *
     * @param msg The message to be displayed.
     * @return The message string.
     */
    private String say(String msg) {
        assert msg != null : "Output message cannot be null";
        if (isPrintingToConsole) {
            System.out.println(msg);
        }
        return msg;
    }

    /**
     * Returns the initial greeting message when the application starts.
     *
     * @return The greeting message.
     */
    public String showGreeting() {
        return say("Hello! I'm Nimbus\nWhat can I do for you?");
    }

    /**
     * Returns a formatted goodbye message.
     *
     * @return The goodbye message.
     */
    public String showBye() {
        return say("Bye. Hope to see you again soon!");
    }

    /**
     * Returns an error message formatted for display.
     *
     * @param message The error details to be shown.
     * @return The formatted error message.
     */
    public String showError(String message) {
        return say("Error: " + message);
    }

    /**
     * Returns a message confirming a task has been successfully added.
     *
     * @param task       The task that was added.
     * @param totalTasks The new total count of tasks in the list.
     * @return The confirmation message.
     */
    public String showAdded(Task task, int totalTasks) {
        return say("Got it. I've added this task:\n  " + task
                + "\nNow you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Returns a message confirming a task has been successfully removed.
     *
     * @param task       The task that was removed.
     * @param totalTasks The remaining total count of tasks in the list.
     * @return The deletion confirmation message.
     */
    public String showDeleted(Task task, int totalTasks) {
        return say("Noted. I've removed this task:\n  " + task
                + "\nNow you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Returns a message confirming a task has been marked as completed.
     *
     * @param task The task that was marked.
     * @return The confirmation message.
     */
    public String showMarked(Task task) {
        return say("Nice! I've marked this task as done:\n  " + task);
    }

    /**
     * Returns a message confirming a task has been set to incomplete.
     *
     * @param task The task that was unmarked.
     * @return The confirmation message.
     */
    public String showUnmarked(Task task) {
        return say("OK, I've marked this task as not done yet:\n  " + task);
    }

    /**
     * Returns a message confirming a task has been updated.
     *
     * @param task The task that was updated.
     * @return The confirmation message.
     */
    public String showUpdated(Task task) {
        return say("Got it. I've updated the details for this task:\n  " + task);
    }

    /**
     * Returns the formatted list of all tasks currently in the list.
     *
     * @param tasks The {@link TaskList} to format.
     * @return A formatted string of all tasks, or an empty list message.
     */
    public String showList(TaskList tasks) {
        if (tasks.size() == 0) {
            return say("Your task list is empty.");
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(getTaskDisplayString(tasks, i)).append("\n");
        }
        return say(sb.toString().trim());
    }

    /**
     * Returns formatted results for a keyword search.
     *
     * @param keyword The search keyword used.
     * @param results The list of tasks matching the keyword.
     * @return A formatted string of search results.
     */
    public String showFindResults(String keyword, List<Task> results) {
        if (results.isEmpty()) {
            return say("No tasks found matching: " + keyword);
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < results.size(); i++) {
            sb.append((i + 1)).append(".").append(results.get(i)).append("\n");
        }
        return say(sb.toString().trim());
    }

    private String getTaskDisplayString(TaskList tasks, int index) {
        try {
            return (index + 1) + ". " + tasks.getByZeroBasedIndex(index);
        } catch (NimbusException e) {
            return (index + 1) + ". [Error retrieving task]";
        }
    }
}