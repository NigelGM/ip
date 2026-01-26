import java.util.Scanner;

public class Nimbus {

    private static final String LINE =
            "____________________________________________________________";
    private static final String INDENT = "    ";

    public static void main(String[] args) {
        new Nimbus().run();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        TaskList taskList = new TaskList();

        showGreeting();
        System.out.println(); // blank line before first command (only keep if required)

        while (true) {
            String input = sc.nextLine().trim();

            if (input.equals("bye")) {
                showGoodbye();
                System.out.println();
                break;
            }

            if (input.equals("list")) {
                showList(taskList);
                System.out.println();
                continue;
            }

            taskList.add(input);
            showAdded(input);
            System.out.println();
        }
    }

    private void showGreeting() {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Hello! I'm Nimbus");
        System.out.println(INDENT + "What can I do for you?");
        System.out.println(INDENT + LINE);
    }

    private void showAdded(String task) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "added: " + task);
        System.out.println(INDENT + LINE);
    }

    private void showList(TaskList taskList) {
        System.out.println(INDENT + LINE);
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(INDENT + (i + 1) + ". " + taskList.get(i));
        }
        System.out.println(INDENT + LINE);
    }

    private void showGoodbye() {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Bye. Hope to see you again soon!");
        System.out.println(INDENT + LINE);
    }
}




