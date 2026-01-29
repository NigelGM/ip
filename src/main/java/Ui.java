public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String INDENT = "    "; // 4 spaces

    private void printLine() {
        System.out.println(LINE);
    }

    private void say(String msg) {
        System.out.println(INDENT + msg);
    }

    public void showGreeting() {
        printLine();
        say("Hello! I'm Nimbus");
        say("What can I do for you?");
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
            say((i + 1) + ". " + tasks.getByZeroBasedIndex(i));
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



