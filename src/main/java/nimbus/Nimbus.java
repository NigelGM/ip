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
        this.ui = new Ui(false); // false = do not print to console, only buffer
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
        ui.resetBuffer();
        ui.showGreeting();
        return ui.getBufferedOutput();
    }

    /**
     * Processes a user input and returns Nimbus's response as a String.
     * GUI should call this once per command.
     *
     * @param input user input
     * @return Nimbus formatted response
     */
    public String getResponse(String input) {
        ui.resetBuffer();

        try {
            Command c = Parser.parse(input);
            c.execute(tasks, ui);

            // Save after every command (safe and simple)
            try {
                storage.saveLines(tasks.toStorageLines());
            } catch (IOException e) {
                ui.showError("Could not save to file. Your changes may not persist.");
            }

            isExit = c.isExit();
            return ui.getBufferedOutput();

        } catch (NimbusException e) {
            isExit = false;
            ui.showError(e.getMessage());
            return ui.getBufferedOutput();
        }
    }

    /**
     * Whether the last processed command requested to exit.
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
                Task t = Parser.parseStoredTask(line);
                if (t != null) {
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            // First run / no file yet -> start empty (no need to scare user)
        }
    }
}
















