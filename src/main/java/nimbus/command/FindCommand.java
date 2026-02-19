package nimbus.command;

import java.util.List;

import nimbus.exception.NimbusException;
import nimbus.task.Task;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Finds tasks whose descriptions contain a given keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) throws NimbusException {
        List<Task> matches = tasks.findByKeyword(keyword);
        return ui.showFindResults(keyword, matches); // Fixes 'showFindResults' warning
    }
}
