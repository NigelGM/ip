import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    // Accept user input as "yyyy-MM-dd" OR "yyyy-MM-dd HHmm"
    private static final DateTimeFormatter INPUT_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter INPUT_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    // Print nicely
    private static final DateTimeFormatter OUTPUT_DATE = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter OUTPUT_DATE_TIME = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Parse user input or stored values into LocalDateTime.
     * Accepts:
     * - yyyy-MM-dd
     * - yyyy-MM-dd HHmm
     * - ISO LocalDateTime (e.g. 2019-12-02T18:00) from save file
     */
    public static LocalDateTime parseDateTime(String raw) throws NimbusException {
        String t = raw.trim();

        // 1) Stored ISO format: 2019-12-02T18:00
        try {
            if (t.contains("T")) {
                return LocalDateTime.parse(t);
            }
        } catch (DateTimeParseException e) {
            // fall through
        }

        // 2) With time: yyyy-MM-dd HHmm
        try {
            if (t.contains(" ")) {
                return LocalDateTime.parse(t, INPUT_DATE_TIME);
            }
        } catch (DateTimeParseException e) {
            throw new NimbusException("Date/time must be yyyy-MM-dd HHmm (e.g., 2019-12-02 1800).");
        }

        // 3) Date only: yyyy-MM-dd -> treat as start of day
        try {
            LocalDate d = LocalDate.parse(t, INPUT_DATE);
            return d.atTime(LocalTime.MIDNIGHT);
        } catch (DateTimeParseException e) {
            throw new NimbusException(
                    "Date must be yyyy-MM-dd (e.g., 2019-10-15) or yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)."
            );
        }
    }

    public static String format(LocalDateTime dt) {
        // If user entered date-only (we store as 00:00), print date only.
        if (dt.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return formatDate(dt.toLocalDate());
        }
        return dt.format(OUTPUT_DATE_TIME);
    }

    public static String formatDate(LocalDate d) {
        return d.format(OUTPUT_DATE);
    }
}


