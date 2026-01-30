package nimbus.command;

import nimbus.exception.NimbusException;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

public abstract class Command {
    protected boolean isExit = false;

    public abstract void execute(TaskList tasks, Ui ui) throws NimbusException;

    public boolean isExit() {
        return isExit;
    }
}


