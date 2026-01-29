public class AddDeadlineCommand implements Command {
    private final String description;
    private final String by;

    public AddDeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws NimbusException {
        Task t = new Deadline(description, by);
        int size = tasks.add(t);
        ui.showAdded(t, size);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
