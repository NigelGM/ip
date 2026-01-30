package nimbus.task;

public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String icon;

    TaskType(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public static TaskType fromIcon(String icon) {
        for (TaskType t : TaskType.values()) {
            if (t.icon.equals(icon)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown task type icon: " + icon);
    }
}

