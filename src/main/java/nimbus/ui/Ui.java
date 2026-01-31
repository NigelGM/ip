package nimbus.ui;

import java.util.Scanner;

import nimbus.exception.NimbusException;
import nimbus.task.Task;
import nimbus.task.TaskList;
// Ui.java

/**
 * Handles all user interaction: printing messages and reading input.
 * <p>
 * The UI prints Nimbus-formatted lines and indented responses to match the expected output format.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String INDENT = "    "; // 4 spaces

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prints a horizontal separator line.
     */
    private void printLine() {
        System.out.println(LINE);
    }

    /**
     * Prints a separator line.
     * <p>
     * Intended to be called from a finally-block to consistently format output.
     */
    public void showLine() {
        printLine();
    }

    /**
     * Prints an indented message.
     *
     * @param msg Message to print.
     */
    private void say(String msg) {
        System.out.println(INDENT + msg);
    }

    /**
     * Reads one full command line from the user.
     *
     * @return User input line.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints the greeting banner when Nimbus starts.
     */
    public void showGreeting() {
        printLine();
        System.out.println("Hello! I'm Nimbus");
        System.out.println("What can I do for you?");
        printLine();
    }

    /**
     * Prints an error message in Nimbus format.
     *
     * @param message Error message.
     */
    public void showError(String message) {
        printLine();
        say(message);
        printLine();
    }

    /**
     * Prints the goodbye message when exiting.
     */
    public void showBye() {
        printLine();
        say("Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Prints confirmation that a task was added.
     *
     * @param task The added task.
     * @param size The new total number of tasks.
     */
    public void showAdded(Task task, int size) {
        printLine();
        say("Got it. I've added this task:");
        say("  " + task);
        say("Now you have " + size + " tasks in the list.");
        printLine();
    }

    /**
     * Prints the list of tasks.
     *
     * @param tasks Task list to display.
     */
    public void showList(TaskList tasks) {
        printLine();
        say("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            try {
                say((i + 1) + ". " + tasks.getByZeroBasedIndex(i));
            } catch (NimbusException e) {
                say("Error displaying task " + (i + 1) + ": " + e.getMessage());
            }
        }
        printLine();
    }

    /**
     * Prints confirmation that a task was marked as done.
     *
     * @param task The marked task.
     */
    public void showMarked(Task task) {
        printLine();
        say("Nice! I've marked this task as done:");
        say("  " + task);
        printLine();
    }

    /**
     * Prints confirmation that a task was unmarked.
     *
     * @param task The unmarked task.
     */
    public void showUnmarked(Task task) {
        printLine();
        say("OK, I've marked this task as not done yet:");
        say("  " + task);
        printLine();
    }

    /**
     * Prints confirmation that a task was deleted.
     *
     * @param task The deleted task.
     * @param size The new total number of tasks.
     */
    public void showDeleted(Task task, int size) {
        printLine();
        say("Noted. I've removed this task:");
        say("  " + task);
        say("Now you have " + size + " tasks in the list.");
        printLine();
    }
}






