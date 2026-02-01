package nimbus;

import java.io.IOException;
import java.util.List;

import nimbus.command.Command;
import nimbus.exception.NimbusException;
import nimbus.parser.Parser;
import nimbus.storage.Storage;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Entry point and main runtime loop for the Nimbus task-tracking application.
 * <p>
 * Nimbus reads commands from the user via {@link Ui}, parses them into {@link Command}
 * objects via {@link Parser}, executes them against the {@link TaskList}, and persists
 * tasks using {@link Storage}.
 */
public class Nimbus {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a Nimbus instance and loads any previously saved tasks from the given file path.
     *
     * @param filePath Path to the save file (e.g. {@code "data/nimbus.txt"}).
     */
    public Nimbus(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        TaskList loaded;
        try {
            List<String> lines = storage.loadLines();
            loaded = new TaskList(lines);
        } catch (IOException e) {
            loaded = new TaskList();
            ui.showError("Could not load save file. Starting with an empty list.");
        }
        this.tasks = loaded;
    }

    /**
     * Runs the main application loop until an exit command is received.
     * <p>
     * Each iteration reads a command, parses it, executes it, and attempts to save.
     */
    public void run() {
        ui.showGreeting();

        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readCommand();
                Command c = Parser.parse(input);
                c.execute(tasks, ui);
                isExit = c.isExit();

                // Save after every command
                try {
                    storage.saveLines(tasks.toStorageLines());
                } catch (IOException e) {
                    ui.showError("Could not save to file. Your changes may not persist.");
                }

            } catch (NimbusException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Launches the application with the default save file path.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        new Nimbus("src/main/java/nimbus/data/nimbus.txt").run();
    }
}















