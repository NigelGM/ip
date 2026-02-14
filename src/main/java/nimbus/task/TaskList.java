package nimbus.task;

import java.util.ArrayList;
import java.util.List;

import nimbus.exception.NimbusException;
import nimbus.parser.Parser;

/**
 * Represents an in-memory list of {@link Task} objects.
 * <p>
 * Provides basic operations such as add, delete, retrieval, and conversion into
 * storage-friendly lines.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList from already-stored lines.
     * Lines that cannot be parsed are ignored.
     *
     * @param storedLines Lines loaded from the save file.
     */
    public TaskList(List<String> storedLines) {
        assert storedLines != null;
        this.tasks = new ArrayList<>();
        for (String line : storedLines) {
            Task t = Parser.parseStoredTask(line);
            if (t != null) {
                tasks.add(t);
            }
        }
    }

    /**
     * Converts all tasks into save-file lines.
     *
     * @return List of storage strings for all tasks.
     */
    public List<String> toStorageLines() {
        assert tasks != null;
        ArrayList<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.toStorageString());
        }
        return lines;
    }

    /**
     * Adds a task into the list.
     *
     * @param task Task to add.
     * @return New size of the task list.
     */
    public int add(Task task) {
        assert task != null;
        tasks.add(task);
        return tasks.size();
    }

    /**
     * Retrieves a task by one-based index (used by user commands).
     *
     * @param oneBasedIndex One-based index (1 to size).
     * @return Task at that index.
     * @throws NimbusException If index is out of range.
     */
    public Task get(int oneBasedIndex) throws NimbusException {
        int idx = oneBasedIndex - 1;
        validateIndex(idx);
        return tasks.get(idx);
    }

    /**
     * Retrieves a task by zero-based index (used internally for listing).
     *
     * @param index Zero-based index (0 to size-1).
     * @return Task at that index.
     * @throws NimbusException If index is out of range.
     */
    public Task getByZeroBasedIndex(int index) throws NimbusException {
        validateIndex(index);
        return tasks.get(index);
    }

    /**
     * Validates that the index is within the bounds of the task list.
     */
    private void validateIndex(int index) throws NimbusException {
        if (index < 0 || index >= tasks.size()) {
            throw new NimbusException("Task number is out of range.");
        }
    }

    /**
     * Deletes a task by one-based index.
     *
     * @param oneBasedIndex One-based index (1 to size).
     * @return The removed task.
     * @throws NimbusException If index is out of range.
     */
    public Task delete(int oneBasedIndex) throws NimbusException {
        int idx = oneBasedIndex - 1;
        validateIndex(idx);
        return tasks.remove(idx);
    }

    /**
     * Finds tasks whose description contains the keyword (case-insensitive).
     * Uses a guard clause for better code quality.
     *
     * @param keyword Search keyword
     * @return list of matching tasks (maybe empty)
     */
    public List<Task> findByKeyword(String keyword) {
        assert keyword != null;
        if (keyword.isBlank()) {
            return new ArrayList<>();
        }

        String needle = keyword.toLowerCase();
        ArrayList<Task> matches = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(needle)) {
                matches.add(t);
            }
        }
        return matches;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return List size.
     */
    public int size() {
        return tasks.size();
    }
}








