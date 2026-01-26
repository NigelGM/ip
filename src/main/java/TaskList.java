public class TaskList {

    private static final int MAX_TASKS = 100;

    private final Task[] tasks = new Task[MAX_TASKS];
    private int taskCount = 0;

    public void add(String description) {
        tasks[taskCount] = new Task(description);
        taskCount++;
    }

    public int size() {
        return taskCount;
    }

    public Task get(int index) {
        return tasks[index];
    }

    // user types "mark 2" -> index = 1
    public Task mark(int oneBasedIndex) {
        Task t = tasks[oneBasedIndex - 1];
        t.markDone();
        return t;
    }

    public Task unmark(int oneBasedIndex) {
        Task t = tasks[oneBasedIndex - 1];
        t.unmark();
        return t;
    }
}


