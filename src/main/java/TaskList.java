public class TaskList {
    private static final int MAX_TASKS = 100;

    private final Task[] tasks = new Task[MAX_TASKS];
    private int size = 0;

    public int size() {
        return size;
    }

    public Task get(int index) {
        return tasks[index];
    }

    public void add(Task task) {
        tasks[size] = task;
        size++;
    }

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



