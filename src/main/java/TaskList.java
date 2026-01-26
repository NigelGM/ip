public class TaskList {

    private static final int MAX_TASKS = 100;

    private final String[] tasks = new String[MAX_TASKS];
    private int taskCount = 0;

    public void add(String task) {
        tasks[taskCount] = task;
        taskCount++;
    }

    public int size() {
        return taskCount;
    }

    public String get(int index) {
        return tasks[index];
    }
}
