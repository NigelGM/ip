package nimbus.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nimbus.exception.NimbusException;
import nimbus.parser.Parser;

/**
 * Represents an in-memory list of {@link Task} objects.
 * <p>
 * Provides operations to manage tasks including adding, deleting, marking,
 * and searching using Java Streams.
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
     *
     * @param storedLines Lines loaded from the save file.
     */
    public TaskList(List<String> storedLines) {
        assert storedLines != null : "Input lines for storage cannot be null";
        this.tasks = new ArrayList<>();
        for (String line : storedLines) {
            Task t = Parser.parseStoredTask(line);
            if (t != null) {
                tasks.add(t);
            }
        }
    }

    /**
     * Converts all tasks into save-file lines using Java Streams.
     *
     * @return List of storage strings for all tasks.
     */
    public List<String> toStorageLines() {
        assert tasks != null : "Tasks list must exist to convert to storage lines";
        return tasks.stream()
                .map(Task::toStorageString)
                .collect(Collectors.toList());
    }

    /**
     * Adds a task into the list.
     *
     * @param task Task to add.
     * @return New size of the task list.
     * @throws NimbusException If the task is a duplicate.
     */
    public int add(Task task) throws NimbusException {
        assert task != null : "Cannot add a null task to the list";
        if (tasks.contains(task)) {
            throw new NimbusException("This task is already in your list!");
        }
        tasks.add(task);
        return tasks.size();
    }

    /**
     * Retrieves a task by one-based index.
     *
     * @param oneBasedIndex One-based index (1 to size).
     * @return Task at that index.
     * @throws NimbusException If index is out of range.
     */
    public Task get(int oneBasedIndex) throws NimbusException {
        int idx = oneBasedIndex - 1;
        validateIndex(idx);
        Task t = tasks.get(idx);
        assert t != null : "Task at valid index should not be null";
        return t;
    }

    /**
     * Retrieves a task by zero-based index.
     *
     * @param index Zero-based index.
     * @return Task at that index.
     * @throws NimbusException If index is out of range.
     */
    public Task getByZeroBasedIndex(int index) throws NimbusException {
        validateIndex(index);
        Task t = tasks.get(index);
        assert t != null : "Task at valid internal index should not be null";
        return t;
    }

    /**
     * Validates that the index is within the bounds of the task list.
     *
     * @param index Zero-based index to check.
     * @throws NimbusException If index is out of range.
     */
    private void validateIndex(int index) throws NimbusException {
        if (index < 0 || index >= tasks.size()) {
            throw new NimbusException("Task number is out of range.");
        }
    }

    /**
     * Finds tasks whose description contains the keyword using Java Streams.
     *
     * @param keyword Search keyword.
     * @return List of matching tasks.
     */
    public List<Task> findByKeyword(String keyword) {
        assert keyword != null : "Search keyword cannot be null";
        if (keyword.isBlank()) {
            return new ArrayList<>();
        }
        String needle = keyword.toLowerCase();
        return tasks.stream()
                .filter(t -> t.getDescription().toLowerCase().contains(needle))
                .collect(Collectors.toList());
    }

    /**
     * Marks a task as done.
     *
     * @param oneBasedIndex One-based index.
     * @return The marked task.
     * @throws NimbusException If index is out of range.
     */
    public Task markTaskAsDone(int oneBasedIndex) throws NimbusException {
        int idx = oneBasedIndex - 1;
        validateIndex(idx);
        Task t = tasks.get(idx);
        t.markDone();
        return t;
    }

    /**
     * Unmarks a task.
     *
     * @param oneBasedIndex One-based index.
     * @return The unmarked task.
     * @throws NimbusException If index is out of range.
     */
    public Task unmarkTask(int oneBasedIndex) throws NimbusException {
        int idx = oneBasedIndex - 1;
        validateIndex(idx);
        Task t = tasks.get(idx);
        t.unmark();
        return t;
    }

    /**
     * Deletes a task from the list.
     *
     * @param oneBasedIndex One-based index.
     * @return The removed task.
     * @throws NimbusException If index is out of range.
     */
    public Task deleteTask(int oneBasedIndex) throws NimbusException {
        int idx = oneBasedIndex - 1;
        validateIndex(idx);
        int oldSize = tasks.size();
        Task removed = tasks.remove(idx);
        assert tasks.size() == oldSize - 1 : "List size should decrease after deletion";
        return removed;
    }

    /**
     * Returns the current number of tasks.
     *
     * @return List size.
     */
    public int size() {
        return tasks.size();
    }
}








