package nimbus.command;

import nimbus.exception.NimbusException;
import nimbus.task.TaskList;
import nimbus.task.Todo;
import nimbus.ui.Ui;

/**
 * Adds a {@link Todo} task into the task list.
 */
public class AddTodoCommand extends Command {
    private final String description;

    /**
     * Constructs an AddTodoCommand with the specified description.
     *
     * @param description The description of the todo task.
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the command by creating a new Todo and adding it to the list.
     *
     * @param tasks The task list to operate on.
     * @param ui    The UI instance to generate the response message.
     * @return A formatted string confirming the task addition.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws NimbusException {
        // Pass 'false' for new, incomplete tasks
        Todo t = new Todo(description, false);
        int size = tasks.add(t);
        return ui.showAdded(t, size); // Fixes 'showAdded' warning
    }
}

