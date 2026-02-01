package nimbus.task;

/**
 * Represents a simple to-do task without any additional fields.
 */
public class Todo extends Task {

    /**
     * Creates a to-do task with the given description.
     *
     * @param description Task description.
     */
    public Todo(String description) {
        super(TaskType.TODO, description); // IMPORTANT: (TaskType, String)
    }

    @Override
    public String toString() {
        return super.toString();
    }
}



