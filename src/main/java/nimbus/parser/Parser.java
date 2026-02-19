package nimbus.parser;

import java.time.LocalDateTime;
import java.util.Objects;

import nimbus.command.AddDeadlineCommand;
import nimbus.command.AddEventCommand;
import nimbus.command.AddTodoCommand;
import nimbus.command.ByeCommand;
import nimbus.command.Command;
import nimbus.command.DeleteCommand;
import nimbus.command.FindCommand;
import nimbus.command.HelpCommand; // Added HelpCommand import
import nimbus.command.ListCommand;
import nimbus.command.MarkCommand;
import nimbus.command.UnmarkCommand;
import nimbus.command.UpdateCommand;
import nimbus.command.UpdateCommand.EditTaskDescriptor;
import nimbus.exception.NimbusException;
import nimbus.task.Deadline;
import nimbus.task.Event;
import nimbus.task.Task;
import nimbus.task.Todo;

/**
 * Parses raw user input strings into executable {@link Command} objects.
 * <p>
 * This class handles format errors such as multiple spaces and provides
 * clear, descriptive error messages while maintaining defensive null-safety.
 */
public class Parser {

    /** Keyword to exit the application. */
    private static final String CMD_BYE = "bye";
    /** Keyword to list all tasks. */
    private static final String CMD_LIST = "list";
    /** Keyword to help unsure users. */
    private static final String CMD_HELP = "help";
    /** Keyword to mark a task as done. */
    private static final String CMD_MARK = "mark";
    /** Keyword to unmark a task. */
    private static final String CMD_UNMARK = "unmark";
    /** Keyword to delete a task. */
    private static final String CMD_DELETE = "delete";
    /** Keyword to find tasks by keyword. */
    private static final String CMD_FIND = "find";
    /** Keyword to add a todo task. */
    private static final String CMD_TODO = "todo";
    /** Keyword to add a deadline task. */
    private static final String CMD_DEADLINE = "deadline";
    /** Keyword to add an event task. */
    private static final String CMD_EVENT = "event";
    /** Keyword to update an existing task. */
    private static final String CMD_UPDATE = "update";

    private static final String ERR_EMPTY_COMMAND = "The sky is silent... Please type a command or 'help' for guidance.";
    private static final String ERR_UNKNOWN_COMMAND = "I looked through the fog, but I don't know what that means.";
    private static final String ERR_DESC_EMPTY = "Storm clouds! The description cannot be empty.";
    private static final String ERR_INVALID_PIPE = "The clouds can't hold the '|' character. Please remove it.";
    private static final String ERR_NO_INDEX = "I need a task number to reach that cloud for: ";
    private static final String ERR_INVALID_INDEX = "Please provide a valid positive task number for: ";

    private static final String USAGE_FIND = "Usage: find <keyword>";
    private static final String USAGE_DEADLINE = "Usage: deadline <description> /by <yyyy-mm-dd HHmm>";
    private static final String USAGE_EVENT = "Usage: event <desc> /from <time> /to <time>";

    /**
     * Parses a full command line into a specific {@link Command}.
     *
     * @param fullCommand Raw user input line. Must not be null.
     * @return A {@link Command} instance representing the user request.
     * @throws NimbusException      If the command is empty, unknown, or missing parameters.
     * @throws NullPointerException If {@code fullCommand} is null.
     */
    public static Command parse(String fullCommand) throws NimbusException {
        String trimmed = Objects.requireNonNull(fullCommand, "Command input cannot be null").trim();
        if (trimmed.isEmpty()) {
            throw new NimbusException(ERR_EMPTY_COMMAND);
        }

        String[] parts = trimmed.split("\\s+", 2);
        String commandWord = parts[0].toLowerCase();
        String rest = parts.length > 1 ? parts[1].trim() : "";

        return switch (commandWord) {
            case CMD_BYE -> new ByeCommand();
            case CMD_LIST -> new ListCommand();
            case CMD_HELP -> new HelpCommand();
            case CMD_MARK -> new MarkCommand(parseOneBasedIndex(rest, CMD_MARK));
            case CMD_UNMARK -> new UnmarkCommand(parseOneBasedIndex(rest, CMD_UNMARK));
            case CMD_DELETE -> new DeleteCommand(parseOneBasedIndex(rest, CMD_DELETE));
            case CMD_FIND -> prepareFind(rest);
            case CMD_TODO -> prepareTodo(rest);
            case CMD_DEADLINE -> parseDeadline(rest);
            case CMD_EVENT -> parseEvent(rest);
            case CMD_UPDATE -> prepareUpdate(rest);
            default -> throw new NimbusException(ERR_UNKNOWN_COMMAND);
        };
    }

    /**
     * Prepares a todo command after validating the description.
     *
     * @param args The description of the todo.
     * @return An {@link AddTodoCommand}.
     * @throws NimbusException If the description is empty.
     */
    private static Command prepareTodo(String args) throws NimbusException {
        validateDescription(args);
        return new AddTodoCommand(args);
    }

    /**
     * Prepares a find command after validating the keyword.
     *
     * @param args The keyword to search for.
     * @return A {@link FindCommand}.
     * @throws NimbusException If the keyword is empty.
     */
    private static Command prepareFind(String args) throws NimbusException {
        if (args.isEmpty()) {
            throw new NimbusException(USAGE_FIND);
        }
        return new FindCommand(args);
    }

    /**
     * Parses a task index from a string.
     *
     * @param args        The string argument containing the index.
     * @param commandName The name of the command for error messaging.
     * @return The parsed integer index.
     * @throws NimbusException If the index is missing or not a valid positive number.
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
     * Parses arguments for a deadline command.
     *
     * @param args The user input containing description and time.
     * @return An {@link AddDeadlineCommand}.
     * @throws NimbusException If format is incorrect or dates are invalid.
     */
    private static Command parseDeadline(String args) throws NimbusException {
        if (!args.contains("/by")) {
            throw new NimbusException("I can't find the deadline time! " + USAGE_DEADLINE);
        }
        String[] split = args.split("\\s*/by\\s*", 2);
        String desc = split[0].trim();
        String byStr = (split.length > 1) ? split[1].trim() : "";

        validateDescription(desc);
        if (byStr.isEmpty()) {
            throw new NimbusException("When is this due? " + USAGE_DEADLINE);
        }

        LocalDateTime by = DateTimeUtil.parseDateTime(byStr);
        return new AddDeadlineCommand(desc, by);
    }

    /**
     * Parses arguments for an event command.
     *
     * @param args The user input containing description, start time, and end time.
     * @return An {@link AddEventCommand}.
     * @throws NimbusException If format is incorrect or dates are invalid.
     */
    private static Command parseEvent(String args) throws NimbusException {
        if (!args.contains("/from") || !args.contains("/to")) {
            throw new NimbusException("An event needs a timeline! " + USAGE_EVENT);
        }
        String[] splitFrom = args.split("\\s*/from\\s*", 2);
        String desc = splitFrom[0].trim();

        String[] splitTo = splitFrom[1].split("\\s*/to\\s*", 2);
        String fromStr = splitTo[0].trim();
        String toStr = (splitTo.length > 1) ? splitTo[1].trim() : "";

        validateDescription(desc);
        if (fromStr.isEmpty() || toStr.isEmpty()) {
            throw new NimbusException("The start or end time is missing! " + USAGE_EVENT);
        }

        LocalDateTime from = DateTimeUtil.parseDateTime(fromStr);
        LocalDateTime to = DateTimeUtil.parseDateTime(toStr);
        return new AddEventCommand(desc, from, to);
    }

    /**
     * Validates that a description is not empty and does not contain illegal characters.
     *
     * @param desc The string to validate.
     * @throws NimbusException If validation fails.
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
     * Parses a stored string into a Task. Corrupted lines return null.
     *
     * @param line The storage string. Must not be null.
     * @return The parsed Task or null if corrupted.
     * @throws NullPointerException If {@code line} is null.
     */
    public static Task parseStoredTask(String line) {
        Objects.requireNonNull(line, "Storage line cannot be null");
        try {
            String[] parts = line.split("\\s*\\|\\s*");
            if (parts.length < 3) return null;

            String type = parts[0];
            boolean isDone = "1".equals(parts[1]);
            String desc = parts[2];

            return switch (type) {
                case "T" -> new Todo(desc, isDone);
                case "D" -> (parts.length >= 4)
                        ? new Deadline(desc, DateTimeUtil.parseDateTime(parts[3]), isDone) : null;
                case "E" -> (parts.length >= 5)
                        ? new Event(desc, DateTimeUtil.parseDateTime(parts[3]),
                        DateTimeUtil.parseDateTime(parts[4]), isDone) : null;
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Prepares an update command based on provided descriptors.
     *
     * @param args The index and parameters to update.
     * @return An {@link UpdateCommand}.
     * @throws NimbusException If the index is invalid or parameters are malformed.
     */
    private static Command prepareUpdate(String args) throws NimbusException {
        String[] parts = args.trim().split("\\s+", 2);
        if (parts.length < 1 || parts[0].isEmpty()) {
            throw new NimbusException("Please specify which task number to update.");
        }

        // Improved: Reuses parseOneBasedIndex to handle positive/integer validation consistently
        int index = parseOneBasedIndex(parts[0], CMD_UPDATE) - 1;

        EditTaskDescriptor descriptor = new EditTaskDescriptor();
        if (parts.length > 1) {
            String params = " " + parts[1];
            int byIdx = params.indexOf(" /by ");
            int fromIdx = params.indexOf(" /from ");
            int toIdx = params.indexOf(" /to ");

            int cutOff = params.length();
            if (byIdx != -1) cutOff = Math.min(cutOff, byIdx);
            if (fromIdx != -1) cutOff = Math.min(cutOff, fromIdx);
            if (toIdx != -1) cutOff = Math.min(cutOff, toIdx);

            String potentialDesc = params.substring(0, cutOff).trim();
            if (!potentialDesc.isEmpty()) descriptor.setDescription(potentialDesc);

            if (byIdx != -1) descriptor.setBy(extractArg(params, byIdx + 5, new int[]{fromIdx, toIdx}));
            if (fromIdx != -1) descriptor.setFrom(extractArg(params, fromIdx + 7, new int[]{byIdx, toIdx}));
            if (toIdx != -1) descriptor.setTo(extractArg(params, toIdx + 5, new int[]{byIdx, fromIdx}));
        }
        return new UpdateCommand(index, descriptor);
    }

    /**
     * Extracts an argument value from a string containing multiple potential flags.
     *
     * @param full   The full argument string.
     * @param start  The starting index of the value.
     * @param others Indices of other potential flags to avoid over-reading.
     * @return The extracted and trimmed argument value.
     */
    private static String extractArg(String full, int start, int[] others) {
        int end = full.length();
        for (int idx : others) {
            if (idx > start && idx < end) end = idx;
        }
        return full.substring(start, end).trim();
    }
}







