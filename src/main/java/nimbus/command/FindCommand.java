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

    /**
     * Creates a FindCommand using the keyword provided by the user.
     *
     * @param keyword Keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws NimbusException {
        List<Task> matches = tasks.findByKeyword(keyword);
        ui.showFindResults(keyword, matches);
    }

}
