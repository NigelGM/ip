package nimbus.ui;

import nimbus.exception.NimbusException;
import nimbus.task.Task;
import nimbus.task.TaskList;

import java.util.List;
import java.util.Random;

/**
 * Handles all user-facing messages for the Nimbus application.
 * <p>
 * This class implements "The Chill Cloud" personality with weather-themed responses.
 * It supports both console printing (for text UI) and a string buffer (for JavaFX GUI).
 */
public class Ui {

    private final boolean isPrintingToConsole;
    private final Random random = new Random();

    /**
     * Buffer used to store messages for retrieval by the GUI.
     * This fixes the "Cannot resolve method" errors in Nimbus.java.
     */
    private final StringBuilder buffer = new StringBuilder();

    // --- Unicode Escape Constants for "Safe" Emojis ---
    // Using escapes prevents rendering issues on different operating systems.
    private static final String ICON_CLOUD = "\u2601";      // ☁
    private static final String ICON_SUN = "\u2600";        // ☀
    private static final String ICON_LIGHTNING = "\u26A1";  // ⚡
    private static final String ICON_SPARKLES = "\u2728";   // ✨
    private static final String ICON_WAVE = "\uD83D\uDC4B"; // 👋
    private static final String ICON_MEMO = "\uD83D\uDCDD"; // 📝

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
     * Resets the internal message buffer.
     * Called by Nimbus before processing a new command to ensure the GUI only shows the latest response.
     */
    public void resetBuffer() {
        buffer.setLength(0);
    }

    /**
     * Returns the accumulated messages as a single String.
     * Used by the GUI to display the bot's response in the dialog box.
     *
     * @return The formatted response string.
     */
    public String getBufferedOutput() {
        return buffer.toString().trim();
    }

    /**
     * Internal helper to print to console (if enabled) AND append to the GUI buffer.
     *
     * @param msg The message to be processed.
     * @return The original message string.
     */
    private String say(String msg) {
        assert msg != null : "Output message cannot be null";

        // 1. Print to console (for debugging or CLI mode)
        if (isPrintingToConsole) {
            System.out.println(msg);
        }

        // 2. Append to buffer (for JavaFX GUI)
        buffer.append(msg).append("\n");

        return msg;
    }

    /**
     * Returns a random weather-themed greeting.
     *
     * @return A random greeting message.
     */
    public String showGreeting() {
        String[] greetings = {
                "Floating in... " + ICON_CLOUD + " I'm Nimbus.\nWhat's on your horizon today?",
                "Skies are clearing up! " + ICON_SUN + "\nHow can I help you organize your day?",
                "Hello! I'm Nimbus, your personal cloud assistant.\nReady to weather the storm?"
        };
        return say(greetings[random.nextInt(greetings.length)]);
    }

    /**
     * Returns a goodbye message.
     *
     * @return The goodbye message.
     */
    public String showBye() {
        return say("Drifting away... " + ICON_WAVE + " Hope to see clear skies soon!");
    }

    /**
     * Returns an error message. Starting with "Error:" helps trigger specific GUI styling.
     *
     * @param message The error details.
     * @return The formatted error message.
     */
    public String showError(String message) {
        return say("Error: Storm clouds ahead! " + ICON_LIGHTNING + "\n" + message);
    }

    /**
     * Confirms a task has been added.
     *
     * @param task       The added task.
     * @param totalTasks The new total count.
     * @return The confirmation message.
     */
    public String showAdded(Task task, int totalTasks) {
        return say("Forecast is sunny! " + ICON_SUN + " I've added this to your list:\n  " + task
                + "\nYou now have " + totalTasks + " tasks floating in the cloud.");
    }

    /**
     * Confirms a task has been removed.
     *
     * @param task       The removed task.
     * @param totalTasks The remaining count.
     * @return The deletion confirmation message.
     */
    public String showDeleted(Task task, int totalTasks) {
        return say("Whoosh! That task has drifted away... " + ICON_CLOUD + "\n  " + task
                + "\n" + totalTasks + " tasks remaining.");
    }

    /**
     * Confirms a task has been marked as completed.
     */
    public String showMarked(Task task) {
        return say("Brilliant like sunshine! " + ICON_SPARKLES + "\nI've marked this task as done:\n  " + task);
    }

    /**
     * Confirms a task has been set to incomplete.
     */
    public String showUnmarked(Task task) {
        return say("Cloudy with a chance of work. " + ICON_CLOUD + "\nI've marked this task as not done:\n  " + task);
    }

    /**
     * Confirms a task has been updated.
     */
    public String showUpdated(Task task) {
        return say("The winds of change have blown! " + ICON_MEMO + "\nUpdated task details:\n  " + task);
    }

    /**
     * Displays all tasks in the list.
     *
     * @param tasks The {@link TaskList} to format.
     * @return The formatted list string.
     */
    public String showList(TaskList tasks) {
        if (tasks.size() == 0) {
            return say("Your sky is clear! No tasks found.");
        }
        StringBuilder sb = new StringBuilder("Here is your current forecast:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(getTaskDisplayString(tasks, i)).append("\n");
        }
        return say(sb.toString().trim());
    }

    /**
     * Formats search results for a keyword.
     */
    public String showFindResults(String keyword, List<Task> results) {
        if (results.isEmpty()) {
            return say("I looked through the fog but couldn't find anything matching: " + keyword);
        }
        StringBuilder sb = new StringBuilder("I found these in the clouds:\n");
        for (int i = 0; i < results.size(); i++) {
            sb.append((i + 1)).append(".").append(results.get(i)).append("\n");
        }
        return say(sb.toString().trim());
    }

    /**
     * Helper to format a single task with its 1-based index.
     */
    private String getTaskDisplayString(TaskList tasks, int index) {
        try {
            return (index + 1) + ". " + tasks.getByZeroBasedIndex(index);
        } catch (NimbusException e) {
            return (index + 1) + ". [Error retrieving task]";
        }
    }
}