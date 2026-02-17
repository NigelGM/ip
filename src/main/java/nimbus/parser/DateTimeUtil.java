package nimbus.parser;

import nimbus.exception.NimbusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Provides centralized utility methods for parsing and formatting {@link LocalDateTime} objects.
 *
 * <p>This class ensures that date-time strings entered by the user, stored in data files,
 * or displayed in the UI maintain a consistent format across the Nimbus application.
 * It supports ISO-8601 formats, custom user input patterns, and date-only inputs.</p>
 */
public class DateTimeUtil {

    /** * Pattern for date-only user input: {@code yyyy-MM-dd} (e.g., 2019-12-02).
     */
    private static final DateTimeFormatter INPUT_DATE =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Add this for your Storage file
    private static final DateTimeFormatter STORAGE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /** * Pattern for date and time user input: {@code yyyy-MM-dd HHmm} (e.g., 2019-12-02 1800).
     */
    private static final DateTimeFormatter INPUT_DATE_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /** * Pattern for date-only display output: {@code MMM dd yyyy} (e.g., Dec 02 2019).
     */
    private static final DateTimeFormatter OUTPUT_DATE =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    /** * Pattern for date and time display output: {@code MMM dd yyyy, h:mm a} (e.g., Dec 02 2019, 6:00 PM).
     */
    private static final DateTimeFormatter OUTPUT_DATE_TIME =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Parses a string representation of a date and/or time into a {@link LocalDateTime} object.
     * * <p>The method attempts to parse the input in the following order of precedence:</p>
     * <ol>
     * <li><b>ISO Format:</b> If the string contains 'T' (e.g., {@code 2019-12-02T18:00:00}).</li>
     * <li><b>User Date-Time:</b> If the string contains a space (e.g., {@code 2019-12-02 1800}).</li>
     * <li><b>User Date-Only:</b> Defaults to the start of the day (e.g., {@code 2019-12-02}).</li>
     * </ol>
     *
     * @param raw The raw input string to be parsed.
     * @return A {@link LocalDateTime} object representing the input string.
     * @throws NimbusException If the input is {@code null}, blank, or does not match any
     * recognized format.
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
     * Formats a {@link LocalDateTime} into a human-readable string for display.
     * * <p>If the time component is exactly midnight (00:00), the method returns only the date
     * (e.g., "Dec 02 2019"). Otherwise, it returns both date and time
     * (e.g., "Dec 02 2019, 6:00 PM").</p>
     *
     * @param dt The {@link LocalDateTime} to format.
     * @return A formatted string, or an empty string if {@code dt} is {@code null}.
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

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private DateTimeUtil() {
        // Utility class; no instances
    }

    /**
     * This fixes the "Cannot resolve method" error.
     */
    public static String formatForStorage(LocalDateTime dateTime) {
        return dateTime.format(STORAGE_FORMATTER);
    }

}


