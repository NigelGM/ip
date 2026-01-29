import java.time.LocalDateTime;

public class AddDeadlineCommand implements Command {
    private final String description;
    private final LocalDateTime by;

    public AddDeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws NimbusException {
        Deadline d = new Deadline(description, by);
        int size = tasks.add(d);
        ui.showAdded(d, size);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}



