package nimbus.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import nimbus.exception.NimbusException;
import nimbus.parser.DateTimeUtil;
import nimbus.task.Deadline;
import nimbus.task.Event;
import nimbus.task.Task;
import nimbus.task.TaskList;
import nimbus.task.Todo;
import nimbus.ui.Ui;

/**
 * Represents a command to update details of an existing task in the task list.
 * This class facilitates partial updates, allowing users to modify specific
 * fields or upgrade task types (e.g., adding a deadline to a Todo).
 */
public class UpdateCommand extends Command {
    private final int targetIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * Constructs an {@code UpdateCommand} with the specified index and edit details.
     *
     * @param targetIndex        The zero-based index of the task to be updated.
     * @param editTaskDescriptor Details to edit the task with.
     */
    public UpdateCommand(int targetIndex, EditTaskDescriptor editTaskDescriptor) {
        this.targetIndex = targetIndex;
        this.editTaskDescriptor = editTaskDescriptor;
    }

    /**
     * Executes the update command by modifying the task at the target index.
     *
     * @param tasks The list of tasks managed by the application.
     * @param ui    The user interface for generating feedback.
     * @return A message confirming the task update.
     * @throws NimbusException If the index is invalid or date parsing fails.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws NimbusException {
        if (targetIndex < 0 || targetIndex >= tasks.size()) {
            throw new NimbusException("The task index provided is invalid.");
        }

        Task taskToEdit = tasks.getByZeroBasedIndex(targetIndex);
        Task updatedTask = createUpdatedTask(taskToEdit, editTaskDescriptor);

        tasks.setTask(targetIndex, updatedTask);
        return ui.showUpdated(updatedTask);
    }

    /**
     * Creates and returns a new {@code Task} by merging {@code taskToEdit} details
     * with {@code editTaskDescriptor}.
     *
     * @param taskToEdit The original task before modification.
     * @param descriptor The descriptor containing the updated values.
     * @return A new task instance with updated values.
     * @throws NimbusException If an error occurs during task creation (e.g. invalid date format).
     */
    private Task createUpdatedTask(Task taskToEdit, EditTaskDescriptor descriptor) throws NimbusException {
        String newDesc = descriptor.getDescription().orElse(taskToEdit.getDescription());
        boolean isDone = taskToEdit.isDone();

        try {
            // Case 1: Upgrade to (or update) a Deadline
            if (descriptor.getBy().isPresent()) {
                return createNewDeadline(newDesc, descriptor.getBy().get(), isDone);
            }

            // Case 2: Upgrade to (or update) an Event
            if (descriptor.getFrom().isPresent() || descriptor.getTo().isPresent()) {
                return createNewEvent(taskToEdit, descriptor, newDesc, isDone);
            }

            // Case 3: No type change requested; preserve original type with new description
            return createPreservedTypeTask(taskToEdit, newDesc, isDone);

        } catch (DateTimeParseException e) {
            throw new NimbusException("Invalid date format! Please use yyyy-MM-dd HHmm (e.g., 2026-02-25 2359).");
        }
    }

    /**
     * Helper to create a Deadline task.
     */
    private Deadline createNewDeadline(String description, String byString, boolean isDone) throws NimbusException {
        LocalDateTime by = DateTimeUtil.parseDateTime(byString);
        return new Deadline(description, by, isDone);
    }

    /**
     * Helper to create an Event task.
     * Uses Pattern Matching to simplify logic.
     */
    private Event createNewEvent(Task original, EditTaskDescriptor descriptor, String description, boolean isDone) throws NimbusException {
        LocalDateTime from;
        LocalDateTime to;

        // FIX: Pattern Matching for instanceof (removes explicit cast warning)
        if (original instanceof Event oldEvent) {
            from = descriptor.getFrom().isPresent()
                    ? DateTimeUtil.parseDateTime(descriptor.getFrom().get())
                    : oldEvent.getFrom();
            to = descriptor.getTo().isPresent()
                    ? DateTimeUtil.parseDateTime(descriptor.getTo().get())
                    : oldEvent.getTo();
        } else {
            // Upgrading from Todo/Deadline requires both fields
            if (descriptor.getFrom().isEmpty() || descriptor.getTo().isEmpty()) {
                throw new NimbusException("To convert this task to an Event, you need both /from and /to dates.");
            }
            from = DateTimeUtil.parseDateTime(descriptor.getFrom().get());
            to = DateTimeUtil.parseDateTime(descriptor.getTo().get());
        }
        return new Event(description, from, to, isDone);
    }

    /**
     * Helper to create a task preserving the original type.
     */
    private Task createPreservedTypeTask(Task original, String description, boolean isDone) {
        if (original instanceof Deadline deadline) {
            return new Deadline(description, deadline.getBy(), isDone);
        } else if (original instanceof Event event) {
            return new Event(description, event.getFrom(), event.getTo(), isDone);
        } else {
            return new Todo(description, isDone);
        }
    }

    /**
     * Stores the details to edit the task with. Each field is optional.
     */
    public static class EditTaskDescriptor {
        private String description;
        private String by;
        private String from;
        private String to;

        public void setDescription(String description) { this.description = description; }
        public Optional<String> getDescription() { return Optional.ofNullable(description); }

        public void setBy(String by) { this.by = by; }
        public Optional<String> getBy() { return Optional.ofNullable(by); }

        public void setFrom(String from) { this.from = from; }
        public Optional<String> getFrom() { return Optional.ofNullable(from); }

        public void setTo(String to) { this.to = to; }
        public Optional<String> getTo() { return Optional.ofNullable(to); }
    }
}