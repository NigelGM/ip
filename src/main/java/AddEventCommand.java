public class AddEventCommand implements Command {
    private final String description;
    private final String from;
    private final String to;

    public AddEventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws NimbusException {
        Task t = new Event(description, from, to);
        int size = tasks.add(t);
        ui.showAdded(t, size);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
