package nimbus.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import nimbus.exception.NimbusException;

public class DateTimeUtilTest {

    @Test
    public void parseDateTime_invalidCalendarDate_throwsNimbusException() {
        // Arrange: Set up an impossible date
        String invalidDate = "2026-02-31 1800";

        // Act & Assert: Verify that parsing this throws a NimbusException
        NimbusException thrown = assertThrows(NimbusException.class, () -> DateTimeUtil.parseDateTime(invalidDate));

        // Optional Assert: Check that the error message is exactly what you expect
        assertEquals("Invalid date/time. Use: yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)", thrown.getMessage());
    }
}