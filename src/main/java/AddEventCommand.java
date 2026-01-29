import java.time.LocalDateTime;

public class AddEventCommand implements Command {
    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public AddEventCommand(String description, LocalDateTime from, LocalDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws NimbusException {
        Event e = new Event(description, from, to);
        int size = tasks.add(e);
        ui.showAdded(e, size);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}


