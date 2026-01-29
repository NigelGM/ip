// TaskList.java
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public int add(Task task) {
        tasks.add(task);
        return tasks.size();
    }

    // One-based access (used by mark/unmark/delete commands)
    public Task get(int oneBasedIndex) throws NimbusException {
        int idx = oneBasedIndex - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new NimbusException("Task number is out of range.");
        }
        return tasks.get(idx);
    }

    // ✅ Option A: zero-based access (used by Ui.showList)
    public Task getByZeroBasedIndex(int index) throws NimbusException {
        if (index < 0 || index >= tasks.size()) {
            throw new NimbusException("Task number is out of range.");
        }
        return tasks.get(index);
    }

    public Task delete(int oneBasedIndex) throws NimbusException {
        int idx = oneBasedIndex - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new NimbusException("Task number is out of range.");
        }
        return tasks.remove(idx);
    }

    public int size() {
        return tasks.size();
    }
}







