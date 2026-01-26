import java.util.Scanner;

public class Nimbus {

    private static final String LINE = "____________________________________________________________";

    public static void main(String[] args) {
        new Nimbus().run();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        TaskList taskList = new TaskList();

        showGreeting();

        while (true) {
            String input = sc.nextLine().trim();

            if (input.equals("bye")) {
                showGoodbye();
                break;
            }

            if (input.equals("list")) {
                showList(taskList);
                continue;
            }

            if (input.startsWith("mark ")) {
                int idx = Integer.parseInt(input.substring(5).trim());
                Task t = taskList.mark(idx);
                showMarked(t);
                continue;
            }

            if (input.startsWith("unmark ")) {
                int idx = Integer.parseInt(input.substring(7).trim());
                Task t = taskList.unmark(idx);
                showUnmarked(t);
                continue;
            }

            if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                Task t = new Todo(desc);
                taskList.add(t);
                showAdded(t, taskList.size());
                continue;
            }

            if (input.startsWith("deadline ")) {
                // deadline <desc> /by <by>
                int byPos = input.indexOf(" /by ");
                String desc = input.substring(9, byPos).trim();
                String by = input.substring(byPos + 5).trim();

                Task t = new Deadline(desc, by);
                taskList.add(t);
                showAdded(t, taskList.size());
                continue;
            }

            if (input.startsWith("event ")) {
                // event <desc> /from <from> /to <to>
                int fromPos = input.indexOf(" /from ");
                int toPos = input.indexOf(" /to ");

                String desc = input.substring(6, fromPos).trim();
                String from = input.substring(fromPos + 7, toPos).trim();
                String to = input.substring(toPos + 5).trim();

                Task t = new Event(desc, from, to);
                taskList.add(t);
                showAdded(t, taskList.size());
                continue;
            }

            // If your spec says "anything else is a task", you can decide what to do here.
            Task t = new Todo(input);
            taskList.add(t);
            showAdded(t, taskList.size());
        }
    }

    private void showGreeting() {
        System.out.println(LINE);
        System.out.println("Hello! I'm Nimbus");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    private void showGoodbye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    private void showAdded(Task task, int count) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
        System.out.println(LINE);
    }

    private void showList(TaskList taskList) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));
        }
        System.out.println(LINE);
    }

    private void showMarked(Task task) {
        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }

    private void showUnmarked(Task task) {
        System.out.println(LINE);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }
}






