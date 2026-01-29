public class TaskList {
    private static final int MAX = 100;

    private final Task[] tasks = new Task[MAX];
    private int size = 0;

    public int size() {
        return size;
    }

    public int add(Task task) throws NimbusException {
        if (size >= MAX) {
            throw new NimbusException("Your task list is full (max " + MAX + ").");
        }
        tasks[size] = task;
        size++;
        return size;
    }

    public Task getByZeroBasedIndex(int idx) {
        return tasks[idx];
    }

    public Task getByUserIndex(int userIndex) throws NimbusException {
        int idx = userIndex - 1;
        if (idx < 0 || idx >= size) {
            throw new NimbusException("That task number is out of range.");
        }
        return tasks[idx];
    }

    public Task deleteByUserIndex(int userIndex) throws NimbusException {
        int idx = userIndex - 1;
        if (idx < 0 || idx >= size) {
            throw new NimbusException("That task number is out of range.");
        }

        Task removed = tasks[idx];

        // shift left
        for (int i = idx; i < size - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[size - 1] = null;
        size--;

        return removed;
    }
}




