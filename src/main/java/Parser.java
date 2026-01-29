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
        // deadline <desc> /by <by>
        String remainder = trimmed.substring("deadline".length()).trim();
        if (remainder.isEmpty()) {
            throw new NimbusException("Oops! The description of a deadline cannot be empty.");
        }

        int byPos = remainder.indexOf(" /by ");
        if (byPos == -1) {
            throw new NimbusException("Deadline format: deadline <task> /by <when>");
        }

        String desc = remainder.substring(0, byPos).trim();
        String by = remainder.substring(byPos + 5).trim(); // 5 = len(" /by ")

        if (desc.isEmpty()) {
            throw new NimbusException("Oops! The description of a deadline cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new NimbusException("Oops! The /by part of a deadline cannot be empty.");
        }

        return new AddDeadlineCommand(desc, by);
    }

    private static Command parseEvent(String trimmed) throws NimbusException {
        // event <desc> /from <start> /to <end>
        String remainder = trimmed.substring("event".length()).trim();
        if (remainder.isEmpty()) {
            throw new NimbusException("Oops! The description of an event cannot be empty.");
        }

        int fromPos = remainder.indexOf(" /from ");
        int toPos = remainder.indexOf(" /to ");

        if (fromPos == -1 || toPos == -1 || toPos < fromPos) {
            throw new NimbusException("Event format: event <task> /from <start> /to <end>");
        }

        String desc = remainder.substring(0, fromPos).trim();
        String from = remainder.substring(fromPos + 7, toPos).trim(); // 7 = len(" /from ")
        String to = remainder.substring(toPos + 5).trim();           // 5 = len(" /to ")

        if (desc.isEmpty()) {
            throw new NimbusException("Oops! The description of an event cannot be empty.");
        }
        if (from.isEmpty()) {
            throw new NimbusException("Oops! The /from part of an event cannot be empty.");
        }
        if (to.isEmpty()) {
            throw new NimbusException("Oops! The /to part of an event cannot be empty.");
        }

        return new AddEventCommand(desc, from, to);
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

}

