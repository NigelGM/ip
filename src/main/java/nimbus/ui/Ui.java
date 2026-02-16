package nimbus.ui;

import java.util.List;
import nimbus.exception.NimbusException;
import nimbus.task.Task;
import nimbus.task.TaskList;

/**
 * Handles generating all user-facing messages as formatted Strings for the GUI.
 * This class translates internal application state into human-readable feedback.
 */
public class Ui {

    /**
     * Constructs a {@code Ui} instance.
     */
    public Ui() {}

    /**
     * Returns a confirmation message when a task is successfully added.
     *
     * @param task The task that was added.
     * @param size The new size of the task list.
     * @return A confirmation string for the GUI.
     */
    public String showAdded(Task task, int size) {
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Returns the full task list as a formatted string.
     * Fixed: Added 'throws NimbusException' to resolve IDE red line.
     *
     * @param tasks The task list to display.
     * @return A string representation of the task list.
     * @throws NimbusException If an error occurs accessing tasks.
     */
    public String showList(TaskList tasks) throws NimbusException {
        assert tasks != null : "TaskList cannot be null";
        if (tasks.size() == 0) {
            return "Your task list is currently empty!";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            // TaskList access (e.g., .get()) requires this 'throws' clause
            sb.append((i + 1)).append(". ").append(tasks.get(i + 1)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Returns a message confirming a task is marked as done.
     *
     * @param task The marked task.
     * @return A confirmation string.
     */
    public String showMarked(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    /**
     * Returns a message confirming a task is marked as not done.
     *
     * @param task The unmarked task.
     * @return A confirmation string.
     */
    public String showUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    /**
     * Returns a confirmation message when a task is deleted.
     *
     * @param task The removed task.
     * @param size The remaining number of tasks.
     * @return A confirmation string.
     */
    public String showDeleted(Task task, int size) {
        return "Noted. I've removed this task:\n  " + task
                + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Returns the results of a find operation.
     *
     * @param keyword The keyword used for searching.
     * @param matches The list of tasks containing the keyword.
     * @return A formatted result string.
     */
    public String showFindResults(String keyword, List<Task> matches) {
        if (matches.isEmpty()) {
            return "No matching tasks found for \"" + keyword + "\".";
        }
        StringBuilder sb = new StringBuilder("Matching tasks for \"" + keyword + "\":\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append((i + 1)).append(". ").append(matches.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Returns a formatted goodbye message.
     *
     * @return A farewell string.
     */
    public String showBye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns a formatted error message.
     *
     * @param message The specific error detail.
     * @return A formatted error string.
     */
    public String showError(String message) {
        return "OOPS!!! " + message;
    }

    /**
     * Prints a loading error to the console for debugging.
     */
    public void showLoadingError() {
        System.err.println("Error: Could not load tasks from storage.");
    }
}







