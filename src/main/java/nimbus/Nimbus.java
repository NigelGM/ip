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
 * Main logic class for Nimbus.
 * <p>
 * In GUI mode (JavaFX), the UI calls {@link #getResponse(String)} for each user input.
 * Nimbus returns the formatted response as a {@code String}.
 */
public class Nimbus {

    public static final String DEFAULT_FILE_PATH = "data/nimbus.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    private boolean isExit;

    /**
     * Creates Nimbus using the default save file path.
     */
    public Nimbus() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Creates Nimbus using a custom save file path.
     *
     * @param filePath path to save/load data
     */
    public Nimbus(String filePath) {
        this.ui = new Ui(false); // false = do not print to console
        this.storage = new Storage(filePath);
        this.tasks = new TaskList();
        this.isExit = false;

        loadFromStorage();
    }

    /**
     * Returns Nimbus greeting message for GUI.
     *
     * @return greeting message
     */
    public String getGreeting() {
        // Removed ui.resetBuffer() - no longer needed
        return ui.showGreeting();
    }

    /**
     * Processes a user input and returns Nimbus's response as a String.
     * GUI should call this once per command.
     *
     * @param input user input
     * @return Nimbus formatted response
     */
    public String getResponse(String input) {
        // Removed ui.resetBuffer() - no longer needed

        try {
            Command c = Parser.parse(input);
            String response = c.execute(tasks, ui);

            try {
                storage.saveLines(tasks.toStorageLines());
            } catch (IOException e) {
                return ui.showError("Could not save to file. Your changes may not persist.");
            }

            isExit = c.isExit();
            return response;

        } catch (NimbusException e) {
            isExit = false;
            return ui.showError(e.getMessage());
        } catch (Exception e) {
            isExit = false;
            return ui.showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Checks if the last processed command requested to exit.
     *
     * @return true if app should exit, false otherwise
     */
    public boolean isExit() {
        return isExit;
    }

    private void loadFromStorage() {
        try {
            List<String> lines = storage.loadLines();
            for (String line : lines) {
                try {
                    Task t = Parser.parseStoredTask(line);
                    if (t != null) {
                        tasks.add(t);
                    }
                } catch (Exception e) {
                    // Ignore corrupted lines
                }
            }
        } catch (IOException e) {
            // First run / no file yet
        }
    }
}
















