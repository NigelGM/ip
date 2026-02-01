package nimbus.command;

import nimbus.task.Task;
import nimbus.exception.NimbusException;
import nimbus.task.Todo;
import nimbus.ui.Ui;
import nimbus.task.TaskList;

/**
 * Adds a {@link Todo} task into the task list.
 */
public class AddTodoCommand extends Command {
    private final String description;

    /**
     * Creates an add-todo command.
     *
     * @param description Description of the todo task.
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }

    /**
     * Adds the todo task to the task list and shows a confirmation message.
     *
     * @param tasks The task list to add into.
     * @param ui    The UI used to show feedback.
     * @throws NimbusException If the task cannot be added.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws NimbusException {
        Task t = new Todo(description);
        int size = tasks.add(t);
        ui.showAdded(t, size);
    }

}

