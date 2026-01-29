import java.util.Scanner;

public class Nimbus {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ui ui = new Ui();
        TaskList tasks = new TaskList();

        ui.showGreeting();

        boolean isExit = false;
        while (!isExit) {
            try {
                String input = sc.nextLine();   // <- user prompt stays left automatically
                Command c = Parser.parse(input);
                c.execute(tasks, ui);
                isExit = c.isExit();
            } catch (NimbusException e) {
                ui.showError(e.getMessage());   // <- indented output
            }
        }
    }
}










