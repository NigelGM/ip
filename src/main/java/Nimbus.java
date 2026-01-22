public class Nimbus {

    private static final String LINE =
            "____________________________________________________________";

    public static void main(String[] args) {
        new Nimbus().run();
    }

    public void run() {
        showGreeting();
        showGoodbye();
    }

    private void showGreeting() {
        System.out.println(LINE);
        System.out.println("  Hello! I'm Nimbus");
        System.out.println("  What can I do for you?");
        System.out.println(LINE);
    }

    private void showGoodbye() {
        System.out.println("  Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }
}


