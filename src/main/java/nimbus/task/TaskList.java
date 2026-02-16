package nimbus.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nimbus.exception.NimbusException;
import nimbus.parser.Parser;

/**
 * Represents an in-memory list of {@link Task} objects.
 * <p>
 * Provides basic operations such as add, delete, retrieval, and conversion into
 * storage-friendly lines using Java Streams.
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
        assert storedLines != null : "Input lines for storage cannot be null";
        this.tasks = storedLines.stream()
                .map(Parser::parseStoredTask)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
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
     */
    public int add(Task task) {
        assert task != null : "Cannot add a null task to the list";
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
        if (idx < 0 || idx >= tasks.size()) {
            throw new NimbusException("Task number is out of range.");
        }
        Task t = tasks.get(idx);
        assert t != null : "Task at valid index should not be null";
        return t;
    }

    /**
     * Retrieves a task by zero-based index (used internally for listing).
     *
     * @param index Zero-based index (0 to size-1).
     * @return Task at that index.
     * @throws NimbusException If index is out of range.
     */
    public Task getByZeroBasedIndex(int index) throws NimbusException {
        if (index < 0 || index >= tasks.size()) {
            throw new NimbusException("Task number is out of range.");
        }
        Task t = tasks.get(index);
        assert t != null : "Task at valid internal index should not be null";
        return t;
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
        if (idx < 0 || idx >= tasks.size()) {
            throw new NimbusException("Task number is out of range.");
        }
        int oldSize = tasks.size();
        Task removed = tasks.remove(idx);
        assert tasks.size() == oldSize - 1 : "List size should decrease after deletion";
        return removed;
    }

    /**
     * Finds tasks whose description contains the keyword using Java Streams.
     *
     * @param keyword Search keyword.
     * @return List of matching tasks (perhaps empty).
     */
    public List<Task> findByKeyword(String keyword) {
        assert keyword != null : "Search keyword cannot be null";
        String needle = keyword.toLowerCase();
        return tasks.stream()
                .filter(t -> t.getDescription().toLowerCase().contains(needle))
                .collect(Collectors.toList());
    }

    /**
     * Marks a task as done.
     *
     * @param oneBasedIndex One-based index of the task.
     * @return The updated task.
     * @throws NimbusException If index is out of range.
     */
    public Task markTaskAsDone(int oneBasedIndex) throws NimbusException {
        Task t = get(oneBasedIndex);
        t.markAsDone();
        return t;
    }

    /**
     * Marks a task as not done.
     *
     * @param oneBasedIndex One-based index of the task.
     * @return The updated task.
     * @throws NimbusException If index is out of range.
     */
    public Task unmarkTask(int oneBasedIndex) throws NimbusException {
        Task t = get(oneBasedIndex);
        t.unmarkAsDone();
        return t;
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








