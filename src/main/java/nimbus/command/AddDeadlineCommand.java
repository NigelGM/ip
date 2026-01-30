package nimbus.command;

import java.time.LocalDateTime;
import nimbus.task.TaskList;
import nimbus.task.Deadline;
import nimbus.ui.Ui;
import nimbus.exception.NimbusException;

public class AddDeadlineCommand extends Command {
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



