package nimbus.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import nimbus.exception.NimbusException;
import nimbus.parser.Parser;

/**
 * Represents an in-memory list of {@link Task} objects.
 * <p>
 * This class encapsulates a list of tasks and supports operations such as adding,
 * deleting, retrieving, and updating tasks. It includes defensive checks for
 * duplicate tasks and null-safety for storage operations.
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
     * @throws NullPointerException If storedLines is null.
     */
    public TaskList(List<String> storedLines) {
        Objects.requireNonNull(storedLines, "Input lines for storage cannot be null");
        this.tasks = storedLines.stream()
                .filter(Objects::nonNull)
                .map(Parser::parseStoredTask)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Converts all tasks in the list into their storage string format.
     *
     * @return An unmodifiable list of strings suitable for saving to a file.
     */
    public List<String> toStorageLines() {
        return tasks.stream()
                .filter(Objects::nonNull)
                .map(Task::toStorageString)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    /**
     * Adds a new task to the list after verifying it is not a duplicate.
     *
     * @param task The task to add.
     * @return The new size of the task list.
     * @throws NimbusException      If an identical task already exists in the list.
     * @throws NullPointerException If the task is null.
     */
    public int add(Task task) throws NimbusException {
        Objects.requireNonNull(task, "Cannot add a null task to the list");
        if (isDuplicateTask(task)) {
            throw new NimbusException("This task is already floating in your clouds!");
        }
        tasks.add(task);
        return tasks.size();
    }

    /**
     * Checks if a task with the same type and description already exists in the list.
     *
     * @param newTask The task to check.
     * @return true if a duplicate is found, false otherwise.
     */
    private boolean isDuplicateTask(Task newTask) {
        return tasks.stream().anyMatch(t ->
                t.getType() == newTask.getType()
                        && t.getDescription().equalsIgnoreCase(newTask.getDescription())
        );
    }

    /**
     * Retrieves a task using a one-based index.
     *
     * @param oneBasedIndex The one-based index (1 to size).
     * @return The task at the specified index.
     * @throws NimbusException If the index is out of valid range.
     */
    public Task get(int oneBasedIndex) throws NimbusException {
        return getByZeroBasedIndex(oneBasedIndex - 1);
    }

    /**
     * Retrieves a task using a zero-based index.
     *
     * @param index The zero-based index (0 to size-1).
     * @return The task at the specified index.
     * @throws NimbusException If the index is out of valid range.
     */
    public Task getByZeroBasedIndex(int index) throws NimbusException {
        if (index < 0 || index >= tasks.size()) {
            throw new NimbusException("Task number is out of range.");
        }
        return tasks.get(index);
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
        return tasks.remove(idx);
    }

    /**
     * Replaces the task at the specified zero-based index with a new task.
     *
     * @param index   The zero-based index.
     * @param newTask The new task instance.
     * @throws NimbusException      If the index is out of range.
     * @throws NullPointerException If newTask is null.
     */
    public void setTask(int index, Task newTask) throws NimbusException {
        Objects.requireNonNull(newTask, "Replacement task cannot be null");
        if (index < 0 || index >= tasks.size()) {
            throw new NimbusException("Task index out of range.");
        }
        tasks.set(index, newTask);
    }

    /**
     * Finds tasks whose descriptions contain the specified keyword.
     *
     * @param keyword The keyword to search for.
     * @return A list of matching tasks.
     * @throws NullPointerException If keyword is null.
     */
    public List<Task> findByKeyword(String keyword) {
        String needle = Objects.requireNonNull(keyword, "Search keyword cannot be null").toLowerCase();
        return tasks.stream()
                .filter(t -> t.getDescription().toLowerCase().contains(needle))
                .collect(Collectors.toList());
    }

    /**
     * Marks a task as done using a one-based index.
     *
     * @param oneBasedIndex The one-based index.
     * @return The updated task.
     * @throws NimbusException If index is invalid.
     */
    public Task markTaskAsDone(int oneBasedIndex) throws NimbusException {
        Task t = get(oneBasedIndex);
        t.markAsDone();
        return t;
    }

    /**
     * Marks a task as not done using a one-based index.
     *
     * @param oneBasedIndex The one-based index.
     * @return The updated task.
     * @throws NimbusException If index is invalid.
     */
    public Task unmarkTask(int oneBasedIndex) throws NimbusException {
        Task t = get(oneBasedIndex);
        t.unmarkAsDone();
        return t;
    }

    /**
     * Returns the total number of tasks in the list.
     *
     * @return The size of the internal list.
     */
    public int size() {
        return tasks.size();
    }
}








