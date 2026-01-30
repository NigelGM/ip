package nimbus.command;

import nimbus.exception.NimbusException;
import nimbus.task.Task;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

public class MarkCommand extends Command {
    private final int userIndex;

    public MarkCommand(int userIndex) {
        this.userIndex = userIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws NimbusException {
        Task t = tasks.get(userIndex);
        t.markDone();
        ui.showMarked(t);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}


