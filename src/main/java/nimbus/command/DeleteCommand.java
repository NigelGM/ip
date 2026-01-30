package nimbus.command;

import nimbus.task.TaskList;
import nimbus.task.Task;
import nimbus.exception.NimbusException;
import nimbus.ui.Ui;

public class DeleteCommand extends Command {
    private final int oneBasedIndex;

    public DeleteCommand(int oneBasedIndex) {
        this.oneBasedIndex = oneBasedIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws NimbusException {
        Task removed = tasks.delete(oneBasedIndex);
        ui.showDeleted(removed, tasks.size());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}


