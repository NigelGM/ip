package nimbus.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nimbus.exception.NimbusException;
import nimbus.parser.Parser;

/**
 * Represents an in-memory list of {@link Task} objects.
 * <p>
 * This class encapsulates a list of tasks and supports operations such as adding,
 * deleting, retrieving, and updating tasks. It also handles conversion to and from
 * storage-friendly string formats using Java Streams.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a {@code TaskList} by parsing lines loaded from a save file.
     * <p>
     * Corrupted or unparseable lines are filtered out automatically.
     *
     * @param storedLines A list of strings representing stored tasks.
     */
    public TaskList(List<String> storedLines) {
        assert storedLines != null : "Input lines for storage cannot be null";
        this.tasks = storedLines.stream()
                .map(Parser::parseStoredTask)       // Convert String -> Task
                .filter(java.util.Objects::nonNull) // Remove any nulls (failed parses)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Converts all tasks in the list into their storage string format.
     *
     * @return A list of strings suitable for saving to a file.
     */
    public List<String> toStorageLines() {
        assert tasks != null : "Tasks list must exist to convert to storage lines";
        return tasks.stream()
                .map(Task::toStorageString)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new task to the list.
     *
     * @param task The task to add.
     * @return The new size of the task list.
     */
    public int add(Task task) {
        assert task != null : "Cannot add a null task to the list";
        tasks.add(task);
        return tasks.size();
    }

    /**
     * Retrieves a task using a one-based index (typically provided by user input).
     *
     * @param oneBasedIndex The one-based index (1 to size).
     * @return The task at the specified index.
     * @throws NimbusException If the index is out of valid range.
     */
    public Task get(int oneBasedIndex) throws NimbusException {
        // Delegate to zero-based logic to avoid code duplication
        return getByZeroBasedIndex(oneBasedIndex - 1);
    }

    /**
     * Retrieves a task using a zero-based index (used internally by commands).
     *
     * @param index The zero-based index (0 to size-1).
     * @return The task at the specified index.
     * @throws NimbusException If the index is out of valid range.
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
     * Deletes a task from the list using a one-based index.
     *
     * @param oneBasedIndex The one-based index of the task to delete.
     * @return The task that was removed.
     * @throws NimbusException If the index is out of valid range.
     */
    public Task delete(int oneBasedIndex) throws NimbusException {
        int idx = oneBasedIndex - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new NimbusException("Task number is out of range.");
        }

        Task removed = tasks.remove(idx);
        assert removed != null : "Removed task should not be null";
        return removed;
    }

    /**
     * Replaces the task at the specified zero-based index with a new task.
     * Used by update commands to modify existing tasks.
     *
     * @param index   The zero-based index of the task to replace.
     * @param newTask The new task instance to set.
     * @throws NimbusException If the index is out of valid range.
     */
    public void setTask(int index, Task newTask) throws NimbusException {
        assert newTask != null : "Cannot set a null task";
        if (index < 0 || index >= tasks.size()) {
            throw new NimbusException("Task index out of range.");
        }
        tasks.set(index, newTask);
    }

    /**
     * Finds and returns a list of tasks whose descriptions contain the specified keyword.
     * The search is case-insensitive.
     *
     * @param keyword The keyword to search for.
     * @return A list of matching tasks.
     */
    public List<Task> findByKeyword(String keyword) {
        assert keyword != null : "Search keyword cannot be null";
        String needle = keyword.toLowerCase();
        return tasks.stream()
                .filter(t -> t.getDescription().toLowerCase().contains(needle))
                .collect(Collectors.toList());
    }

    /**
     * Marks the task at the specified one-based index as done.
     *
     * @param oneBasedIndex The one-based index of the task.
     * @return The updated task.
     * @throws NimbusException If the index is out of range.
     */
    public Task markTaskAsDone(int oneBasedIndex) throws NimbusException {
        Task t = get(oneBasedIndex); // Reuses the bounds checking in get()
        t.markAsDone();
        return t;
    }

    /**
     * Marks the task at the specified one-based index as not done.
     *
     * @param oneBasedIndex The one-based index of the task.
     * @return The updated task.
     * @throws NimbusException If the index is out of range.
     */
    public Task unmarkTask(int oneBasedIndex) throws NimbusException {
        Task t = get(oneBasedIndex); // Reuses the bounds checking in get()
        t.unmarkAsDone();
        return t;
    }

    /**
     * Returns the total number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }
}








