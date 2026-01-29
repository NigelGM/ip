import java.util.Scanner;

// Ui.java
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String INDENT = "    "; // 4 spaces

    private final Scanner scanner = new Scanner(System.in);

    private void printLine() {
        System.out.println(LINE);
    }

    // optional helper (used by Nimbus finally-block)
    public void showLine() {
        printLine();
    }

    // Nimbus responses (indented)
    private void say(String msg) {
        System.out.println(INDENT + msg);
    }

    // read one full command line from user
    public String readCommand() {
        return scanner.nextLine();
    }

    public void showGreeting() {
        printLine();
        System.out.println("Hello! I'm Nimbus");
        System.out.println("What can I do for you?");
        printLine();
    }

    public void showError(String message) {
        printLine();
        say(message);
        printLine();
    }

    public void showBye() {
        printLine();
        say("Bye. Hope to see you again soon!");
        printLine();
    }

    public void showAdded(Task task, int size) {
        printLine();
        say("Got it. I've added this task:");
        say("  " + task);
        say("Now you have " + size + " tasks in the list.");
        printLine();
    }

    public void showList(TaskList tasks) {
        printLine();
        say("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            try {
                say((i + 1) + ". " + tasks.getByZeroBasedIndex(i));
            } catch (NimbusException e) {
                say("Error displaying task " + (i + 1) + ": " + e.getMessage());
            }
        }
        printLine();
    }

    public void showMarked(Task task) {
        printLine();
        say("Nice! I've marked this task as done:");
        say("  " + task);
        printLine();
    }

    public void showUnmarked(Task task) {
        printLine();
        say("OK, I've marked this task as not done yet:");
        say("  " + task);
        printLine();
    }

    public void showDeleted(Task task, int size) {
        printLine();
        say("Noted. I've removed this task:");
        say("  " + task);
        say("Now you have " + size + " tasks in the list.");
        printLine();
    }
}






