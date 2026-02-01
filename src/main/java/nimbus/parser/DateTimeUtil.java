package nimbus.parser;

import nimbus.exception.NimbusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {

    // Accept user input as "yyyy-MM-dd" OR "yyyy-MM-dd HHmm"
    private static final DateTimeFormatter INPUT_DATE =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter INPUT_DATE_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    // Print nicely
    private static final DateTimeFormatter OUTPUT_DATE =
            DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter OUTPUT_DATE_TIME =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Parses a date/time string into a LocalDateTime.
     * Accepts:
     *  - yyyy-MM-dd
     *  - yyyy-MM-dd HHmm
     *  - ISO LocalDateTime (e.g., 2019-12-02T18:00) from save file
     */
    public static LocalDateTime parseDateTime(String raw) throws NimbusException {
        if (raw == null) {
            throw new NimbusException("Date/time cannot be empty.");
        }

        String t = raw.trim();
        if (t.isEmpty()) {
            throw new NimbusException("Date/time cannot be empty.");
        }

        // 1) Stored ISO format: 2019-12-02T18:00
        if (t.contains("T")) {
            try {
                return LocalDateTime.parse(t); // ISO_LOCAL_DATE_TIME by default
            } catch (DateTimeParseException e) {
                throw new NimbusException("Invalid stored date/time: " + t);
            }
        }

        // 2) User input: yyyy-MM-dd HHmm
        // e.g. "2019-12-02 1800"
        if (t.contains(" ")) {
            try {
                return LocalDateTime.parse(t, INPUT_DATE_TIME);
            } catch (DateTimeParseException e) {
                throw new NimbusException(
                        "Invalid date/time. Use: yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)"
                );
            }
        }

        // 3) User input: yyyy-MM-dd
        try {
            LocalDate d = LocalDate.parse(t, INPUT_DATE);
            return d.atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new NimbusException(
                    "Invalid date. Use: yyyy-MM-dd (e.g., 2019-12-02)"
            );
        }
    }

    /**
     * Formats a LocalDateTime for display.
     * If time is midnight (00:00), prints date only.
     */
    public static String formatForDisplay(LocalDateTime dt) {
        if (dt == null) {
            return "";
        }
        if (dt.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dt.toLocalDate().format(OUTPUT_DATE);
        }
        return dt.format(OUTPUT_DATE_TIME);
    }

    private DateTimeUtil() {
        // Utility class; no instances
    }
}


