package nimbus.task;

import java.util.ArrayList;
import java.util.List;

import nimbus.exception.NimbusException;
import nimbus.parser.Parser;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<String> storedLines) {
        this.tasks = new ArrayList<>();
        for (String line : storedLines) {
            Task t = Parser.parseStoredTask(line);
            if (t != null) {
                tasks.add(t);
            }
        }
    }

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

    /**
     * Finds tasks whose description contains the keyword (case-insensitive).
     *
     * @param keyword Search keyword
     * @return list of matching tasks (maybe empty)
     */
    public List<Task> findByKeyword(String keyword) {
        String needle = keyword.toLowerCase();
        ArrayList<Task> matches = new ArrayList<>();

        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(needle)) {
                matches.add(t);
            }
        }
        return matches;
    }

    public int size() {
        return tasks.size();
    }
}








