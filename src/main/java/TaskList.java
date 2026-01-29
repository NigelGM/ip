import java.util.ArrayList;
import java.util.List;

// TaskList.java
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    // NEW: build tasks from saved lines
    public TaskList(List<String> storedLines) {
        this.tasks = new ArrayList<>();
        for (String line : storedLines) {
            Task t = Parser.parseStoredTask(line); // returns null if corrupted
            if (t != null) {
                tasks.add(t);
            }
        }
    }

    // NEW: convert tasks to lines to save
    public List<String> toStorageLines() {
        ArrayList<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.toStorageString());
        }
        return lines;
    }

    public int add(Task task) {
        tasks.add(task);
        return tasks.size();
    }

    public Task get(int oneBasedIndex) throws NimbusException {
        int idx = oneBasedIndex - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new NimbusException("Task number is out of range.");
        }
        return tasks.get(idx);
    }

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







