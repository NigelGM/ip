package nimbus.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import nimbus.exception.NimbusException;
import org.junit.jupiter.api.Test;

/**
 * Tests the management and defensive logic of the {@link TaskList} class.
 * <p>
 * This suite verifies that tasks are correctly added, deleted, and that
 * duplicates are identified as per the A-MoreErrorHandling requirements.
 */
public class TaskListTest {

    /**
     * Tests that adding a task with identical type and description to an existing
     * task results in a {@link NimbusException}.
     *
     * @throws NimbusException If the initial add fails unexpectedly.
     */
    @Test
    public void add_duplicateTask_exceptionThrown() throws NimbusException {
        TaskList list = new TaskList();
        Todo firstTask = new Todo("read lecture notes", false);
        list.add(firstTask);

        // Fixed: assertThrows requires (ExceptionClass, ExecutableLambda)
        assertThrows(NimbusException.class, () -> list.add(new Todo("read lecture notes", false)));
    }

    /**
     * Tests that a task with the same description but different type is not
     * considered a duplicate.
     *
     * @throws NimbusException If the additions fail unexpectedly.
     */
    @Test
    public void add_sameDescriptionDifferentType_success() throws NimbusException {
        TaskList list = new TaskList();
        list.add(new Todo("Project work", false));

        // Fixed: Ensured LocalDateTime is available and used correctly
        LocalDateTime deadlineTime = LocalDateTime.now().plusDays(1);
        list.add(new Deadline("Project work", deadlineTime, false));

        // Fixed: assertEquals requires (expectedValue, actualValue)
        assertEquals(2, list.size());
    }

    /**
     * Tests that deleting a task correctly reduces the size of the list.
     *
     * @throws NimbusException If the operations fail unexpectedly.
     */
    @Test
    public void delete_validIndex_sizeDecreased() throws NimbusException {
        TaskList list = new TaskList();
        list.add(new Todo("Task to remove", false));
        list.delete(1);

        // Fixed: Providing both required arguments for comparison
        assertEquals(0, list.size());
    }

    /**
     * Tests that providing an out-of-bounds index for retrieval throws an exception.
     */
    @Test
    public void get_invalidIndex_exceptionThrown() {
        TaskList list = new TaskList();

        // Fixed: Added the executable lambda argument
        assertThrows(NimbusException.class, () -> list.get(1));
    }
}
