package nimbus.task;

/**
 * Represents a simple to-do task without any additional fields.
 */
public class Todo extends Task {

    /**
     * Creates a to-do task with a specified completion status.
     *
     * @param description Task description.
     * @param isDone      Whether the task is already completed.
     */
    public Todo(String description, boolean isDone) {
        super(TaskType.TODO, description);
        if (isDone) {
            this.markAsDone();
        }
    }

    // No override for toStorageString needed;
    // Task.toStorageString() (T | 0 | desc) works perfectly for Todo.

    @Override
    public String toString() {
        return super.toString();
    }
}



