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
        System.out.println();

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

            if (input.startsWith("mark ")) {
                int idx = Integer.parseInt(input.substring(5).trim());
                Task t = taskList.mark(idx);
                showMarked(t);
                System.out.println();
                continue;
            }

            if (input.startsWith("unmark ")) {
                int idx = Integer.parseInt(input.substring(7).trim());
                Task t = taskList.unmark(idx);
                showUnmarked(t);
                System.out.println();
                continue;
            }

            // default: treat as task description to add
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
        System.out.println(INDENT + "Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(INDENT + (i + 1) + ". " + taskList.get(i));
        }
        System.out.println(INDENT + LINE);
    }

    private void showMarked(Task task) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Nice! I've marked this task as done:");
        System.out.println(INDENT + task);
        System.out.println(INDENT + LINE);
    }

    private void showUnmarked(Task task) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "OK, I've marked this task as not done yet:");
        System.out.println(INDENT + task);
        System.out.println(INDENT + LINE);
    }

    private void showGoodbye() {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Bye. Hope to see you again soon!");
        System.out.println(INDENT + LINE);
    }
}





