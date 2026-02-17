package nimbus.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import nimbus.exception.NimbusException;
import org.junit.jupiter.api.Test;

/**
 * Tests the chronological validation logic within the {@link Event} class.
 * <p>
 * This ensures that the application prevents the creation of events where
 * the end time precedes the start time.
 */
public class EventTest {

    /**
     * Tests that an {@link Event} throws a {@link NimbusException} when the end time
     * is before the start time.
     */
    @Test
    public void constructor_endTimeBeforeStartTime_exceptionThrown() {
        LocalDateTime start = LocalDateTime.of(2026, 2, 20, 18, 0);
        LocalDateTime end = LocalDateTime.of(2026, 2, 19, 18, 0);

        assertThrows(NimbusException.class, () ->
                new Event("Invalid Event", start, end, false));
    }

    /**
     * Tests that an {@link Event} throws a {@link NimbusException} when the start
     * and end times are identical.
     */
    @Test
    public void constructor_timesAreEqual_exceptionThrown() {
        LocalDateTime time = LocalDateTime.of(2026, 2, 20, 12, 0);

        assertThrows(NimbusException.class, () ->
                new Event("Instant Event", time, time, false));
    }

    /**
     * Tests that a valid {@link Event} is created without any exceptions.
     */
    @Test
    public void constructor_validTimes_success() {
        LocalDateTime start = LocalDateTime.of(2026, 2, 20, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 2, 20, 12, 0);

        assertDoesNotThrow(() -> new Event("Valid Event", start, end, false));
    }
}
