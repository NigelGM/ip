import java.io.IOException;
import java.util.Scanner;

public class Nimbus {
    private static final String SAVE_FILE_PATH = "data/nimbus.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ui ui = new Ui();
        TaskList tasks = new TaskList();
        Storage storage = new Storage(SAVE_FILE_PATH);

        // Load tasks at startup (Level-7)
        try {
            for (String line : storage.loadLines()) {
                Task t = Parser.parseStoredTask(line);
                if (t != null) {
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            ui.showError("Could not load saved tasks: " + e.getMessage());
        }

        ui.showGreeting();

        boolean isExit = false;
        while (!isExit) {
            try {
                String input = sc.nextLine();
                Command c = Parser.parse(input);
                c.execute(tasks, ui);

                // Save automatically when task list changes (Level-7)
                if (c instanceof AddTodoCommand
                        || c instanceof AddDeadlineCommand
                        || c instanceof AddEventCommand
                        || c instanceof DeleteCommand
                        || c instanceof MarkCommand
                        || c instanceof UnmarkCommand) {
                    try {
                        storage.saveLines(tasks.toStorageLines());
                    } catch (IOException e) {
                        ui.showError("Could not save tasks: " + e.getMessage());
                    }
                }

                isExit = c.isExit();
            } catch (NimbusException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}











