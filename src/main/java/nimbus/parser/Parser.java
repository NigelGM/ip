package nimbus.parser;

import java.time.LocalDateTime;

import nimbus.command.AddDeadlineCommand;
import nimbus.command.AddEventCommand;
import nimbus.command.AddTodoCommand;
import nimbus.command.FindCommand;
import nimbus.command.ByeCommand;
import nimbus.command.Command;
import nimbus.command.DeleteCommand;
import nimbus.command.ListCommand;
import nimbus.command.MarkCommand;
import nimbus.command.UnmarkCommand;
import nimbus.exception.NimbusException;
import nimbus.task.Deadline;
import nimbus.task.Event;
import nimbus.task.Task;
import nimbus.task.Todo;

/**
 * Parses raw user input strings into executable {@link Command} objects.
 * <p>
 * The input is expected to follow the Nimbus command format such as:
 * <ul>
 *   <li>{@code todo <description>}</li>
 *   <li>{@code deadline <description> /by <yyyy-mm-ddTHH:mm>}</li>
 *   <li>{@code event <description> /from <yyyy-mm-ddTHH:mm> /to <yyyy-mm-ddTHH:mm>}</li>
 *   <li>{@code mark <taskNumber>}</li>
 *   <li>{@code unmark <taskNumber>}</li>
 *   <li>{@code delete <taskNumber>}</li>
 * </ul>
 */
public class Parser {

    /**
     * Parses a full command line into a {@link Command}.
     *
     * @param fullCommand Raw user input line.
     * @return A {@link Command} instance representing the user request.
     * @throws NimbusException If the command is empty or invalid.
     */
    public static Command parse(String fullCommand) throws NimbusException {
        String trimmed = fullCommand == null ? "" : fullCommand.trim();
        if (trimmed.isEmpty()) {
            throw new NimbusException("Please type a command.");
        }

        String[] parts = trimmed.split("\\s+", 2);
        String commandWord = parts[0];
        String rest = parts.length > 1 ? parts[1].trim() : "";

        return switch (commandWord) {
            case "bye" -> new ByeCommand();

            case "list" -> new ListCommand();

            case "mark" -> new MarkCommand(parseOneBasedIndex(rest, "mark"));
<<<<<<< HEAD

            case "unmark" -> new UnmarkCommand(parseOneBasedIndex(rest, "unmark"));

            case "delete" -> new DeleteCommand(parseOneBasedIndex(rest, "delete"));

            case "find" -> {
                if (rest.trim().isEmpty()) {
                    throw new NimbusException("Usage: find <keyword>");
                }
                yield new FindCommand(rest.trim());
            }

=======

            case "unmark" -> new UnmarkCommand(parseOneBasedIndex(rest, "unmark"));

            case "delete" -> new DeleteCommand(parseOneBasedIndex(rest, "delete"));

>>>>>>> branch-A-JavaDoc
            case "todo" -> {
                if (rest.isEmpty()) {
                    throw new NimbusException("The description of a todo cannot be empty.");
                }
                yield new AddTodoCommand(rest);
            }

            case "deadline" -> parseDeadline(rest);

            case "event" -> parseEvent(rest);

            default -> throw new NimbusException("I'm sorry, but I don't know what that means.");
        };
    }

    /**
     * Parses the task number (1-based) for commands that operate on an existing task.
     *
     * @param s The argument portion after the command word.
     * @param cmd The command word (used for error messages).
     * @return Parsed task number as an integer (1-based).
     * @throws NimbusException If the argument is missing or not a valid positive integer.
     */
    private static int parseOneBasedIndex(String s, String cmd) throws NimbusException {
        if (s == null || s.trim().isEmpty()) {
            throw new NimbusException("Please provide a task number for: " + cmd);
        }
        try {
            int idx = Integer.parseInt(s.trim());
            if (idx <= 0) {
                throw new NumberFormatException();
            }
            return idx; // keep 1-based (matches your TaskList.get(userIndex))
        } catch (NumberFormatException e) {
            throw new NimbusException("Task number must be a positive integer for: " + cmd);
        }
    }

    /**
     * Parses a {@code deadline} command of the form:
     * {@code deadline <description> /by <yyyy-mm-ddTHH:mm>}
     *
     * @param rest Remainder after the command word.
     * @return A {@link AddDeadlineCommand} constructed from the input.
     * @throws NimbusException If the format is invalid or required parts are missing.
     */
    private static Command parseDeadline(String rest) throws NimbusException {
        String[] split = rest.split("\\s*/by\\s*", 2);
        if (split.length < 2) {
            throw new NimbusException("Usage: deadline <description> /by <yyyy-mm-ddTHH:mm>");
        }
        String desc = split[0].trim();
        String byStr = split[1].trim();
        if (desc.isEmpty() || byStr.isEmpty()) {
            throw new NimbusException("Usage: deadline <description> /by <yyyy-mm-ddTHH:mm>");
        }

        LocalDateTime by = DateTimeUtil.parseDateTime(byStr);
        return new AddDeadlineCommand(desc, by);
    }

    /**
     * Parses an {@code event} command of the form:
     * {@code event <description> /from <yyyy-mm-ddTHH:mm> /to <yyyy-mm-ddTHH:mm>}
     *
     * @param rest Remainder after the command word.
     * @return A {@link AddEventCommand} constructed from the input.
     * @throws NimbusException If the format is invalid or required parts are missing.
     */
    private static Command parseEvent(String rest) throws NimbusException {
        String[] splitFrom = rest.split("\\s*/from\\s*", 2);
        if (splitFrom.length < 2) {
            throw new NimbusException("Usage: event <description> /from <yyyy-mm-ddTHH:mm> /to <yyyy-mm-ddTHH:mm>");
        }

        String desc = splitFrom[0].trim();
        String[] splitTo = splitFrom[1].split("\\s*/to\\s*", 2);
        if (splitTo.length < 2) {
            throw new NimbusException("Usage: event <description> /from <yyyy-mm-ddTHH:mm> /to <yyyy-mm-ddTHH:mm>");
        }

        String fromStr = splitTo[0].trim();
        String toStr = splitTo[1].trim();
        if (desc.isEmpty() || fromStr.isEmpty() || toStr.isEmpty()) {
            throw new NimbusException("Usage: event <description> /from <yyyy-mm-ddTHH:mm> /to <yyyy-mm-ddTHH:mm>");
        }

        LocalDateTime from = DateTimeUtil.parseDateTime(fromStr);
        LocalDateTime to = DateTimeUtil.parseDateTime(toStr);
        return new AddEventCommand(desc, from, to);
    }

    /**
     * Parses a saved task line from storage into a {@link Task} instance.
     * <p>
     * Expected formats:
     * <ul>
     *   <li>{@code T | 0 | borrow book}</li>
     *   <li>{@code D | 1 | return book | 2019-12-02T18:00}</li>
     *   <li>{@code E | 0 | meeting | 2019-12-02T14:00 | 2019-12-02T16:00}</li>
     * </ul>
     *
     * @param line One line from the save file.
     * @return A {@link Task} if parse succeeds, otherwise {@code null}.
     */
    public static Task parseStoredTask(String line) {
        try {
            String[] parts = line.split("\\s*\\|\\s*");
            if (parts.length < 3) {
                return null;
            }

            String type = parts[0];
            boolean done = "1".equals(parts[1]);
            String desc = parts[2];

            Task t;
            switch (type) {
                case "T":
                    t = new Todo(desc);
                    break;
                case "D":
                    if (parts.length < 4) return null;
                    t = new Deadline(desc, LocalDateTime.parse(parts[3]));
                    break;
                case "E":
                    if (parts.length < 5) return null;
                    t = new Event(desc, LocalDateTime.parse(parts[3]), LocalDateTime.parse(parts[4]));
                    break;
                default:
                    return null;
            }

            if (done) {
                t.markDone();
            }
            return t;

        } catch (Exception e) {
            return null;
        }
    }
}







