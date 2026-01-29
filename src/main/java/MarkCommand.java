public class MarkCommand implements Command {
    private final int userIndex;

    public MarkCommand(int userIndex) {
        this.userIndex = userIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws NimbusException {
        Task t = tasks.getByUserIndex(userIndex);
        t.markDone();
        ui.showMarked(t);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
