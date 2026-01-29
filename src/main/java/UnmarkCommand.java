public class UnmarkCommand implements Command {
    private final int userIndex;

    public UnmarkCommand(int userIndex) {
        this.userIndex = userIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws NimbusException {
        Task t = tasks.getByUserIndex(userIndex);
        t.unmark();
        ui.showUnmarked(t);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
