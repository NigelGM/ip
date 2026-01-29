import java.time.LocalDateTime;

public class Parser {

    public static Command parse(String input) throws NimbusException {
        String trimmed = input.trim();

        if (trimmed.isEmpty()) {
            throw new NimbusException("Please enter a command.");
        }

        String[] parts = trimmed.split(" ", 2);
        String keyword = parts[0];

        return switch (keyword) {
            case "bye" -> new ByeCommand();
            case "list" -> new ListCommand();

            case "todo" -> parseTodo(parts);
            case "deadline" -> parseDeadline(trimmed);
            case "event" -> parseEvent(trimmed);

            case "mark" -> new MarkCommand(parseIndex(parts, "mark"));
            case "unmark" -> new UnmarkCommand(parseIndex(parts, "unmark"));
            case "delete" -> new DeleteCommand(parseIndex(parts, "delete"));

            default -> throw new NimbusException("Oops! I don't know what that means.");
        };
    }

    private static Command parseTodo(String[] parts) throws NimbusException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new NimbusException("Oops! The description of a todo cannot be empty.");
        }
        return new AddTodoCommand(parts[1].trim());
    }

    private static Command parseDeadline(String trimmed) throws NimbusException {
        // deadline <desc> /by <yyyy-MM-dd HHmm>
        String remainder = trimmed.substring("deadline".length()).trim();
        if (remainder.isEmpty()) {
            throw new NimbusException("Oops! The description of a deadline cannot be empty.");
        }

        int byPos = remainder.indexOf(" /by ");
        if (byPos == -1) {
            throw new NimbusException("Deadline format: deadline <task> /by <yyyy-MM-dd HHmm>");
        }

        String desc = remainder.substring(0, byPos).trim();
        String byStr = remainder.substring(byPos + 5).trim(); // len(" /by ") = 5

        if (desc.isEmpty()) {
            throw new NimbusException("Oops! The description of a deadline cannot be empty.");
        }
        if (byStr.isEmpty()) {
            throw new NimbusException("Oops! The /by part of a deadline cannot be empty.");
        }

        LocalDateTime byDt = DateTimeUtil.parseDateTime(byStr);
        return new AddDeadlineCommand(desc, byDt);
    }

    private static Command parseEvent(String trimmed) throws NimbusException {
        // event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>
        String remainder = trimmed.substring("event".length()).trim();
        if (remainder.isEmpty()) {
            throw new NimbusException("Oops! The description of an event cannot be empty.");
        }

        int fromPos = remainder.indexOf(" /from ");
        int toPos = remainder.indexOf(" /to ");

        if (fromPos == -1 || toPos == -1 || toPos < fromPos) {
            throw new NimbusException("Event format: event <task> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        }

        String desc = remainder.substring(0, fromPos).trim();
        String fromStr = remainder.substring(fromPos + 7, toPos).trim(); // len(" /from ") = 7
        String toStr = remainder.substring(toPos + 5).trim();           // len(" /to ") = 5

        if (desc.isEmpty()) {
            throw new NimbusException("Oops! The description of an event cannot be empty.");
        }
        if (fromStr.isEmpty()) {
            throw new NimbusException("Oops! The /from part of an event cannot be empty.");
        }
        if (toStr.isEmpty()) {
            throw new NimbusException("Oops! The /to part of an event cannot be empty.");
        }

        LocalDateTime fromDt = DateTimeUtil.parseDateTime(fromStr);
        LocalDateTime toDt = DateTimeUtil.parseDateTime(toStr);
        return new AddEventCommand(desc, fromDt, toDt);
    }

    private static int parseIndex(String[] parts, String cmd) throws NimbusException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new NimbusException(cmd + " needs a task number. Example: " + cmd + " 2");
        }

        try {
            int n = Integer.parseInt(parts[1].trim());
            if (n <= 0) {
                throw new NimbusException("Task number must be a positive integer. Example: " + cmd + " 2");
            }
            return n;
        } catch (NumberFormatException e) {
            throw new NimbusException("Task number must be an integer. Example: " + cmd + " 2");
        }
    }

    // Used when loading from your save file
    public static Task parseStoredTask(String line) {
        // Expected formats:
        // T | 1 | read book
        // D | 0 | return book | 2019-12-02T18:00
        // E | 0 | meeting | 2019-12-02T14:00 | 2019-12-02T16:00
        try {
            String[] p = line.split(" \\| ");
            if (p.length < 3) return null;

            String icon = p[0].trim();
            boolean done = p[1].trim().equals("1");
            String desc = p[2].trim();

            TaskType type = TaskType.fromIcon(icon);

            Task task = switch (type) {
                case TODO -> new Todo(desc);

                case DEADLINE -> {
                    if (p.length < 4) yield null;
                    LocalDateTime byDt = DateTimeUtil.parseDateTime(p[3].trim());
                    yield new Deadline(desc, byDt);
                }

                case EVENT -> {
                    if (p.length < 5) yield null;
                    LocalDateTime fromDt = DateTimeUtil.parseDateTime(p[3].trim());
                    LocalDateTime toDt = DateTimeUtil.parseDateTime(p[4].trim());
                    yield new Event(desc, fromDt, toDt);
                }
            };

            if (task == null) return null;
            if (done) task.markDone();
            return task;

        } catch (Exception e) {
            return null; // ignore corrupted lines safely
        }
    }
}





