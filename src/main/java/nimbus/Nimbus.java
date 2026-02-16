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
 * Handles component initialization, data loading, and the execution loop.
 */
public class Nimbus {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private boolean isExit = false;

    /**
     * Initializes Nimbus with a file path for storage.
     *
     * @param filePath Path to the local save file.
     */
    public Nimbus(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList();
        loadFromStorage();
    }

    /**
     * Loads tasks from storage and skips duplicates to ensure list integrity.
     */
    private void loadFromStorage() {
        try {
            List<String> lines = storage.loadLines();
            for (String line : lines) {
                Task t = Parser.parseStoredTask(line);
                if (t != null) {
                    try {
                        tasks.add(t); // Now handles the checked NimbusException
                    } catch (NimbusException e) {
                        // Skip duplicates in the save file quietly
                    }
                }
            }
        } catch (IOException e) {
            ui.showLoadingError(); // Ensure this method exists in Ui.java
        }
    }

    /**
     * Processes user input and returns the application's response.
     *
     * @param input Raw user input string.
     * @return Response string to be displayed in the GUI.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            // 1. Execute with only TWO arguments
            String response = c.execute(tasks, ui);

            // 2. Save the state after every successful command
            storage.save(tasks.toStorageLines());

            this.isExit = c.isExit();
            return response;
        } catch (NimbusException | IOException e) {
            return e.getMessage();
        }
    }

    /**
     * Returns whether the application should exit.
     *
     * @return True if the exit command was called, false otherwise.
     */
    public boolean isExit() {
        return isExit;
    }
}
















