package nimbus;

import java.io.IOException;
import java.util.List;
import nimbus.command.Command;
import nimbus.exception.NimbusException;
import nimbus.parser.Parser;
import nimbus.storage.Storage;
import nimbus.task.Task;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Main entry point for the Nimbus application.
 * Handles component initialization, data loading, and the main execution loop for the GUI.
 */
public class Nimbus {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private boolean isExit = false;

    /**
     * Initializes Nimbus with a file path for storage.
     *
     * @param filePath Path to the local save file (e.g., "data/nimbus.txt").
     */
    public Nimbus(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList();
        loadFromStorage();
    }

    /**
     * Loads tasks from the storage file and populates the TaskList.
     * Skips duplicates found in the file to maintain integrity.
     */
    private void loadFromStorage() {
        try {
            List<String> lines = storage.loadLines();
            for (String line : lines) {
                Task t = Parser.parseStoredTask(line);
                if (t != null) {
                    try {
                        tasks.add(t); // Duplicate detection logic triggered here
                    } catch (NimbusException e) {
                        // Skip duplicates in storage silently
                    }
                }
            }
        } catch (IOException e) {
            ui.showLoadingError();
        }
    }

    /**
     * Processes user input, executes commands, and returns the application's response.
     * Automatically saves the current state to disk after every successful command.
     *
     * @param input Raw user input string from the GUI.
     * @return Response string to be displayed in the chat bubble.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            String response = c.execute(tasks, ui);
            storage.save(tasks.toStorageLines());
            this.isExit = c.isExit();
            return response;
        } catch (NimbusException | IOException e) {
            // FIX: Wrap the message in ui.showError to clear the warning
            return ui.showError(e.getMessage());
        }
    }

    /**
     * Returns the initial greeting message when the application starts.
     *
     * @return A welcome string.
     */
    public String getGreeting() {
        return "Hello! I'm Nimbus. How can I help you today?";
    }

    /**
     * Returns whether the application should terminate.
     *
     * @return True if the exit command was processed, false otherwise.
     */
    public boolean isExit() {
        return isExit;
    }
}
















