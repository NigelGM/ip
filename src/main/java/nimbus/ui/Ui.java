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
 * To prevent rendering issues (empty boxes) in the GUI, all emojis are represented
 * using specific Unicode escape sequences.
 */
public class Ui {

    private final boolean isPrintingToConsole;
    private final Random random = new Random();

    // --- Unicode Escape Constants for "Safe" Emojis ---
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
     * Returns a random weather-themed greeting to make the bot feel alive.
     * <p>
     * <b>Fix:</b> Uses Unicode escapes to ensure emojis like the sun and cloud
     * render without trailing blocks.
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
     * Returns a formatted goodbye message with a waving theme.
     *
     * @return The goodbye message.
     */
    public String showBye() {
        return say("Drifting away... " + ICON_WAVE + " Hope to see clear skies soon!");
    }

    /**
     * Returns an error message.
     * <p>
     * <b>CRITICAL:</b> This message starts with "Error:" to trigger the
     * red error bubble styling in the GUI.
     *
     * @param message The error details to be shown.
     * @return The formatted error message.
     */
    public String showError(String message) {
        return say("Error: Storm clouds ahead! " + ICON_LIGHTNING + "\n" + message);
    }

    /**
     * Returns a message confirming a task has been successfully added.
     *
     * @param task       The task that was added.
     * @param totalTasks The new total count of tasks in the list.
     * @return The confirmation message.
     */
    public String showAdded(Task task, int totalTasks) {
        return say("Forecast is sunny! " + ICON_SUN + " I've added this to your list:\n  " + task
                + "\nYou now have " + totalTasks + " tasks floating in the cloud.");
    }

    /**
     * Returns a message confirming a task has been removed.
     *
     * @param task       The task that was removed.
     * @param totalTasks The remaining total count of tasks in the list.
     * @return The deletion confirmation message.
     */
    public String showDeleted(Task task, int totalTasks) {
        return say("Whoosh! That task has drifted away... " + ICON_CLOUD + "\n  " + task
                + "\n" + totalTasks + " tasks remaining.");
    }

    /**
     * Returns a message confirming a task has been marked as completed.
     *
     * @param task The task that was marked.
     * @return The confirmation message.
     */
    public String showMarked(Task task) {
        return say("Brilliant like sunshine! " + ICON_SPARKLES + "\nI've marked this task as done:\n  " + task);
    }

    /**
     * Returns a message confirming a task has been set to incomplete.
     *
     * @param task The task that was unmarked.
     * @return The confirmation message.
     */
    public String showUnmarked(Task task) {
        return say("Cloudy with a chance of work. " + ICON_CLOUD + "\nI've marked this task as not done:\n  " + task);
    }

    /**
     * Returns a message confirming a task has been updated.
     *
     * @param task The task that was updated.
     * @return The confirmation message.
     */
    public String showUpdated(Task task) {
        return say("The winds of change have blown! " + ICON_MEMO + "\nUpdated task details:\n  " + task);
    }

    /**
     * Returns the formatted list of all tasks currently in the list.
     *
     * @param tasks The {@link TaskList} to format.
     * @return A formatted string of all tasks, or an empty list message.
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
     * Returns formatted results for a keyword search.
     *
     * @param keyword The search keyword used.
     * @param results The list of tasks matching the keyword.
     * @return A formatted string of search results.
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