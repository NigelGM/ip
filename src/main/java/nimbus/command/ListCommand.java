package nimbus.command;

import nimbus.ui.Ui;
import nimbus.task.TaskList;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.showList(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

