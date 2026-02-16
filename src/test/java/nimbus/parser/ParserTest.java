package nimbus.parser;

import static org.junit.jupiter.api.Assertions.*;

import nimbus.task.Task;
import org.junit.jupiter.api.Test;

import nimbus.command.Command;
import nimbus.exception.NimbusException;
import nimbus.task.TaskList;
import nimbus.ui.Ui;

/**
 * Tests Parser.parse(...) end-to-end by running the resulting Command
 * and checking TaskList state changes.
 */
public class ParserTest {

    // Minimal test UI to avoid spamming console
    static class TestUi extends Ui {
        @Override
        public String showAdded(Task task, int size) { return ""; }

        @Override
        public String showMarked(Task task) { return ""; }

        // Fixed: Change from void to String to resolve clash
        @Override
        public String showUnmarked(Task task) { return ""; }

        @Override
        public String showList(TaskList tasks) {
            return "";
        }

        @Override
        public String showDeleted(Task task, int size) { return ""; }

        @Override
        public String showError(String message) { return ""; }
    }

    @Test
    void parse_todoCommand_addsTodoToTaskList() throws Exception {
        TaskList tasks = new TaskList();
        Ui ui = new TestUi();

        Command c = Parser.parse("todo borrow book");
        c.execute(tasks, ui);

        assertEquals(1, tasks.size());
        assertTrue(tasks.get(1).toString().contains("borrow book"));
    }

    @Test
    void parse_markCommand_marksTaskDone() throws Exception {
        TaskList tasks = new TaskList();
        Ui ui = new TestUi();

        Parser.parse("todo read book").execute(tasks, ui);
        assertFalse(tasks.get(1).toString().contains("[X]"));

        Parser.parse("mark 1").execute(tasks, ui);
        assertTrue(tasks.get(1).toString().contains("[X]"));
    }

    @Test
    void parse_emptyCommand_throws() {
        assertThrows(NimbusException.class, () -> Parser.parse("   "));
    }

    @Test
    void parse_markNonNumber_throws() {
        assertThrows(NimbusException.class, () -> Parser.parse("mark abc"));
    }
}

