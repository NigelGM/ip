import java.util.Scanner;

public class Nimbus {

    private static final String LINE =
            "____________________________________________________________";
    private static final String INDENT = "    ";

    public static void main(String[] args) {
        new Nimbus().run();
    }

    public void run() {
        showGreeting();
        System.out.println(); // <-- blank line before first user command

        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine().trim();

            if (input.equals("bye")) {
                showGoodbye();
                System.out.println(); // <-- optional; blank line after goodbye
                break;
            }

            echo(input);
            System.out.println(); // <-- blank line before next user command
        }
    }

    private void showGreeting() {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Hello! I'm Nimbus");
        System.out.println(INDENT + "What can I do for you?");
        System.out.println(INDENT + LINE);
    }

    private void echo(String msg) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + msg);
        System.out.println(INDENT + LINE);
    }

    private void showGoodbye() {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Bye. Hope to see you again soon!");
        System.out.println(INDENT + LINE);
    }
}




