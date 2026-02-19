package nimbus;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import nimbus.command.Command;
import nimbus.exception.NimbusException;
import nimbus.parser.Parser;
import nimbus.storage.Storage;
import nimbus.task.Task;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Represents the main logic controller for the Nimbus application.
 * <p>
 * This class coordinates the interactions between the {@code Ui}, {@code Storage},
 * and {@code TaskList} components. In GUI mode, it provides the bridge between
 * user input and the visual response.
 */
public class Nimbus {
    /** Default path for data persistence. */
    public static final String DEFAULT_FILE_PATH = "data/nimbus.txt";
    private static final Logger logger = Logger.getLogger(Nimbus.class.getName());

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private boolean isExit;

    /**
     * Initializes Nimbus with the default storage file path.
     */
    public Nimbus() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Initializes Nimbus with a specific storage file path and loads existing tasks.
     *
     * @param filePath The relative path to the file where tasks are saved.
     * @throws AssertionError if filePath is null.
     */
    public Nimbus(String filePath) {
        assert filePath != null : "File path should not be null";

        // IMPORTANT FIX: Setting Ui to false disables terminal printing,
        // relying solely on the internal StringBuilder buffer for GUI use.
        this.ui = new Ui(false);
        this.storage = new Storage(filePath);
        this.tasks = new TaskList();
        this.isExit = false;

        loadFromStorage();
    }

    /**
     * Generates the initial greeting message for the user.
     * @return A formatted welcome string from the UI buffer.
     */
    public String getGreeting() {
        ui.resetBuffer();
        ui.showGreeting();
        return ui.getBufferedOutput();
    }

    /**
     * Processes a user input string, executes the resulting command, and returns
     * the system's response for display in the GUI.
     * <p>
     * This method ensures that even if a command fails, the UI buffer is
     * captured and returned to prevent the application from appearing unresponsive.
     *
     * @param input The raw text entered by the user.
     * @return The formatted response to be displayed in the chat bubble.
     */
    public String getResponse(String input) {
        ui.resetBuffer();

        try {
            Command c = Parser.parse(input);
            c.execute(tasks, ui);

            // Updates the application exit state based on the command
            this.isExit = c.isExit();

            // Auto-save state after successful command execution
            handleAutoSave();

        } catch (NimbusException e) {
            ui.showError(e.getMessage());
        }

        // Return only the UI buffer. Ignored any return value from the execution.
        return ui.getBufferedOutput();
    }

    /**
     * Handles the background persistence of the task list.
     * Errors during saving are communicated to the user but do not crash the app.
     */
    private void handleAutoSave() {
        try {
            storage.saveLines(tasks.toStorageLines());
        } catch (IOException e) {
            ui.showError("Warning: Unable to save changes to disk.");
            logger.severe("Critical Storage Failure: " + e.getMessage());
        }
    }

    /**
     * Indicates whether the last processed command was a termination command.
     *
     * @return true if the application should close, false otherwise.
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Populates the task list from the storage file on startup.
     * If the file is missing or corrupted, initializes an empty list.
     */
    private void loadFromStorage() {
        try {
            List<String> lines = storage.loadLines();
            for (String line : lines) {
                Task t = Parser.parseStoredTask(line);
                if (t != null) {
                    tasks.add(t);
                }
            }
        } catch (IOException | NimbusException e) {
            logger.info("No valid save file found. Starting with an empty TaskList.");
        }
    }
}
















