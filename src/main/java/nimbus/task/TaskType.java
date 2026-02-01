package nimbus.task;

/**
 * Represents the supported task types in Nimbus.
 * Each type has a single-letter icon used for display and storage.
 */
public enum TaskType {
    /** A simple to-do task. */
    TODO("T"),
    /** A task with a deadline. */
    DEADLINE("D"),
    /** A task with a start and end time. */
    EVENT("E");

    private final String icon;

    /**
     * Creates a TaskType with the given icon.
     *
     * @param icon Single-letter icon used for display/storage.
     */
    TaskType(String icon) {
        this.icon = icon;
    }

    /**
     * Returns the icon for this task type.
     *
     * @return Type icon.
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Converts an icon string into a TaskType.
     *
     * @param icon Type icon string.
     * @return Matching TaskType.
     * @throws IllegalArgumentException If the icon does not match any known type.
     */
    public static TaskType fromIcon(String icon) {
        for (TaskType t : TaskType.values()) {
            if (t.icon.equals(icon)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown task type icon: " + icon);
    }
}

