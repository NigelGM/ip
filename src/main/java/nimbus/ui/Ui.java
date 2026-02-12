package nimbus.ui;

import java.util.List;

import nimbus.exception.NimbusException;
import nimbus.task.Task;
import nimbus.task.TaskList;

/**
 * Handles all user-facing messages.
 * <p>
 * Supports two modes:
 * <ul>
 *   <li>Console printing (optional)</li>
 *   <li>Buffering output as a String (for JavaFX GUI)</li>
 * </ul>
 */
public class Ui {

    private final boolean printToConsole;
    private final StringBuilder buffer = new StringBuilder();

    /**
     * Creates an Ui that prints to console and buffers output.
     */
    public Ui() {
        this(true);
    }

    /**
     * Creates an Ui with configurable console printing.
     *
     * @param printToConsole true to print to console, false to only buffer
     */
    public Ui(boolean printToConsole) {
        this.printToConsole = printToConsole;
    }

    /**
     * Clears buffered output (call before generating a new response).
     */
    public void resetBuffer() {
        buffer.setLength(0);
    }

    /**
     * Returns the buffered output as a String (trimmed).
     *
     * @return buffered output
     */
    public String getBufferedOutput() {
        return buffer.toString().stripTrailing();
    }

    private void out(String s) {
        if (printToConsole) {
            System.out.println(s);
        }
        buffer.append(s).append(System.lineSeparator());
    }

    private void say(String msg) {
        out(msg);
    }

    public void showGreeting() {
        out("Hello! I'm Nimbus");
        out("What can I do for you?");
    }

    public void showError(String message) {
        say(message);
    }

    public void showBye() {
        say("Bye. Hope to see you again soon!");
    }

    public void showAdded(Task task, int size) {
        say("Got it. I've added this task:");
        say("  " + task);
        say("Now you have " + size + " tasks in the list.");
    }

    public void showList(TaskList tasks) {
        say("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            try {
                say((i + 1) + ". " + tasks.getByZeroBasedIndex(i));
            } catch (NimbusException e) {
                // should not happen, but safe
                say("Error displaying task " + (i + 1) + ": " + e.getMessage());
            }
        }
    }

    public void showMarked(Task task) {
        say("Nice! I've marked this task as done:");
        say("  " + task);
    }

    public void showUnmarked(Task task) {
        say("OK, I've marked this task as not done yet:");
        say("  " + task);
    }

    public void showDeleted(Task task, int size) {
        say("Noted. I've removed this task:");
        say("  " + task);
        say("Now you have " + size + " tasks in the list.");
    }

    public void showFindResults(String keyword, List<Task> matches) {
        if (matches == null || matches.isEmpty()) {
            say("No matching tasks found for \"" + keyword + "\".");
        } else {
            say("Here are the matching tasks in your list for \"" + keyword + "\":");
            for (int i = 0; i < matches.size(); i++) {
                say((i + 1) + ". " + matches.get(i));
            }
        }
    }
}







