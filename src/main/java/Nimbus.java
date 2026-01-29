import java.io.IOException;
import java.util.List;

public class Nimbus {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Nimbus(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        TaskList loaded;
        try {
            List<String> lines = storage.loadLines();
            loaded = new TaskList(lines);
        } catch (IOException e) {
            // if file doesn't exist or can't be read, start fresh
            loaded = new TaskList();
            ui.showError("Could not load save file. Starting with an empty list.");
        }
        this.tasks = loaded;
    }

    public void run() {
        ui.showGreeting();

        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readCommand();
                Command c = Parser.parse(input);
                c.execute(tasks, ui);
                isExit = c.isExit();

                // Save after every command (simple + safe)
                // Storage already ensures ./data exists :contentReference{index=4}
                try {
                    storage.saveLines(tasks.toStorageLines());
                } catch (IOException e) {
                    ui.showError("Could not save to file. Your changes may not persist.");
                }

            } catch (NimbusException e) {
                ui.showError(e.getMessage());
            } finally {
                // optional: visually separate turns
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Nimbus("data/nimbus.txt").run();
    }
}












