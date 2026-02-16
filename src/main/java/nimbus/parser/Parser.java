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
 * The input is expected to follow the Nimbus command format. This class acts as a
 * facade for interpreting user intentions and converting them into command objects
 * that can be executed by the application logic.
 */
public class Parser {

    // --- Command Keywords ---
    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_DELETE = "delete";
    private static final String CMD_FIND = "find";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";

    // --- Error Messages & Formats ---
    private static final String ERR_EMPTY_COMMAND = "Please type a command.";
    private static final String ERR_UNKNOWN_COMMAND = "I'm sorry, but I don't know what that means.";
    private static final String ERR_DESC_EMPTY = "The description cannot be empty.";
    private static final String ERR_INVALID_PIPE = "Description cannot contain the '|' character (used for storage).";
    private static final String ERR_NO_INDEX = "Please provide a task number for: ";
    private static final String ERR_INVALID_INDEX = "Task number must be a positive integer for: ";

    private static final String USAGE_FIND = "Usage: find <keyword>";
    private static final String USAGE_DEADLINE = "Usage: deadline <description> /by <yyyy-mm-dd HHmm>";
    private static final String USAGE_EVENT = "Usage: event <desc> /from <time> /to <time>";

    /**
     * Parses a full command line into a specific {@link Command}.
     *
     * @param fullCommand Raw user input line.
     * @return A {@link Command} instance representing the user request.
     * @throws NimbusException If the command is empty, unknown, or has invalid arguments.
     */
    public static Command parse(String fullCommand) throws NimbusException {
        String trimmed = fullCommand == null ? "" : fullCommand.trim();
        if (trimmed.isEmpty()) {
            throw new NimbusException(ERR_EMPTY_COMMAND);
        }

        // Split into [commandWord, restOfArguments]
        String[] parts = trimmed.split("\\s+", 2);
        String commandWord = parts[0];
        String rest = parts.length > 1 ? parts[1].trim() : "";

        // High-level SLAP: Delegates specific parsing to helper methods
        return switch (commandWord) {
            case CMD_BYE -> new ByeCommand();
            case CMD_LIST -> new ListCommand();
            case CMD_MARK -> new MarkCommand(parseOneBasedIndex(rest, CMD_MARK));
            case CMD_UNMARK -> new UnmarkCommand(parseOneBasedIndex(rest, CMD_UNMARK));
            case CMD_DELETE -> new DeleteCommand(parseOneBasedIndex(rest, CMD_DELETE));
            case CMD_FIND -> prepareFind(rest);
            case CMD_TODO -> prepareTodo(rest);
            case CMD_DEADLINE -> parseDeadline(rest);
            case CMD_EVENT -> parseEvent(rest);
            default -> throw new NimbusException(ERR_UNKNOWN_COMMAND);
        };
    }

    // ==================================================================================
    // Helper Methods (SLAP Improvements)
    // ==================================================================================

    /**
     * Prepares a {@link AddTodoCommand} by validating the description argument.
     *
     * @param args The description of the todo task.
     * @return A valid {@link AddTodoCommand}.
     * @throws NimbusException If the description is empty or invalid.
     */
    private static Command prepareTodo(String args) throws NimbusException {
        validateDescription(args);
        return new AddTodoCommand(args);
    }

    /**
     * Prepares a {@link FindCommand} by validating the search keyword.
     *
     * @param args The keyword to search for.
     * @return A valid {@link FindCommand}.
     * @throws NimbusException If the keyword is empty.
     */
    private static Command prepareFind(String args) throws NimbusException {
        if (args.isEmpty()) {
            throw new NimbusException(USAGE_FIND);
        }
        return new FindCommand(args);
    }

    /**
     * Parses the argument string into a one-based integer index.
     * <p>
     * This helper centralizes integer parsing and error handling for commands
     * that require a task index (mark, unmark, delete).
     *
     * @param args The argument portion containing the index.
     * @param commandName The name of the command (for error messaging).
     * @return The parsed one-based index.
     * @throws NimbusException If the argument is missing, not a number, or not positive.
     */
    private static int parseOneBasedIndex(String args, String commandName) throws NimbusException {
        if (args.isEmpty()) {
            throw new NimbusException(ERR_NO_INDEX + commandName);
        }
        try {
            int index = Integer.parseInt(args);
            if (index <= 0) {
                throw new NumberFormatException();
            }
            return index;
        } catch (NumberFormatException e) {
            throw new NimbusException(ERR_INVALID_INDEX + commandName);
        }
    }

    /**
     * Parses the arguments for a deadline command.
     * <p>
     * Expected format: {@code <description> /by <yyyy-MM-dd HHmm>}
     *
     * @param args The arguments containing description and deadline time.
     * @return A valid {@link AddDeadlineCommand}.
     * @throws NimbusException If the format is invalid or parts are missing.
     */
    private static Command parseDeadline(String args) throws NimbusException {
        String[] split = args.split("\\s*/by\\s*", 2);

        if (split.length < 2) {
            throw new NimbusException(USAGE_DEADLINE);
        }

        String desc = split[0].trim();
        String byStr = split[1].trim();

        validateDescription(desc);
        if (byStr.isEmpty()) {
            throw new NimbusException(USAGE_DEADLINE);
        }

        LocalDateTime by = DateTimeUtil.parseDateTime(byStr);
        return new AddDeadlineCommand(desc, by);
    }

    /**
     * Parses the arguments for an event command.
     * <p>
     * Expected format: {@code <description> /from <start> /to <end>}
     *
     * @param args The arguments containing description, start time, and end time.
     * @return A valid {@link AddEventCommand}.
     * @throws NimbusException If the format is invalid or parts are missing.
     */
    private static Command parseEvent(String args) throws NimbusException {
        String[] splitFrom = args.split("\\s*/from\\s*", 2);
        if (splitFrom.length < 2) {
            throw new NimbusException(USAGE_EVENT);
        }

        String desc = splitFrom[0].trim();
        String[] splitTo = splitFrom[1].split("\\s*/to\\s*", 2);
        if (splitTo.length < 2) {
            throw new NimbusException(USAGE_EVENT);
        }

        String fromStr = splitTo[0].trim();
        String toStr = splitTo[1].trim();

        validateDescription(desc);
        if (fromStr.isEmpty() || toStr.isEmpty()) {
            throw new NimbusException(USAGE_EVENT);
        }

        LocalDateTime from = DateTimeUtil.parseDateTime(fromStr);
        LocalDateTime to = DateTimeUtil.parseDateTime(toStr);
        return new AddEventCommand(desc, from, to);
    }

    /**
     * Validates that the description is not empty and does not contain illegal characters.
     *
     * @param desc The description string to check.
     * @throws NimbusException If the description is empty or contains '|'.
     */
    private static void validateDescription(String desc) throws NimbusException {
        if (desc.isEmpty()) {
            throw new NimbusException(ERR_DESC_EMPTY);
        }
        if (desc.contains("|")) {
            throw new NimbusException(ERR_INVALID_PIPE);
        }
    }

    /**
     * Parses a single line from the storage file into a {@link Task} object.
     * <p>
     * Used during startup to load the task list. Robustly handles corrupted lines
     * by returning {@code null} instead of crashing.
     *
     * @param line A single line of text from the saved file.
     * @return The reconstructed {@link Task}, or {@code null} if parsing fails.
     */
    public static Task parseStoredTask(String line) {
        try {
            String[] parts = line.split("\\s*\\|\\s*");
            if (parts.length < 3) {
                return null;
            }

            String type = parts[0];
            boolean isDone = "1".equals(parts[1]);
            String desc = parts[2];

            Task task = switch (type) {
                case "T" -> new Todo(desc);
                case "D" -> (parts.length >= 4)
                        ? new Deadline(desc, DateTimeUtil.parseDateTime(parts[3]))
                        : null;
                case "E" -> (parts.length >= 5)
                        ? new Event(desc, DateTimeUtil.parseDateTime(parts[3]), DateTimeUtil.parseDateTime(parts[4]))
                        : null;
                default -> null;
            };

            if (task != null && isDone) {
                task.markDone();
            }
            return task;

        } catch (Exception e) {
            // Return null to skip corrupted lines without crashing the app
            return null;
        }
    }
}







