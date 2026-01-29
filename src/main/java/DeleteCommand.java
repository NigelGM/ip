public class DeleteCommand implements Command {
    private final int userIndex;

    public DeleteCommand(int userIndex) {
        this.userIndex = userIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws NimbusException {
        Task removed = tasks.deleteByUserIndex(userIndex);
        ui.showDeleted(removed, tasks.size());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

