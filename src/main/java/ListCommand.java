public class ListCommand implements Command {
    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.showList(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

