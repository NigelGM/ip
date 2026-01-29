public class AddTodoCommand implements Command {
    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws NimbusException {
        Task t = new Todo(description);
        int size = tasks.add(t);
        ui.showAdded(t, size);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

