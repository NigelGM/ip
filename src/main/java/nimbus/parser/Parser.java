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
import nimbus.command.UpdateCommand;
import nimbus.command.UpdateCommand.EditTaskDescriptor; // Import internal class
import nimbus.exception.NimbusException;
import nimbus.task.Deadline;
import nimbus.task.Event;
import nimbus.task.Task;
import nimbus.task.Todo;

/**
 * Parses raw user input strings into executable {@link Command} objects.
 * <p>
 * This class acts as the bridge between the user's raw text and the application's logic.
 * It is a static utility class, meaning methods are called on the class itself.
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
    private static final String CMD_UPDATE = "update";

    // --- Error Messages ---
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

        String[] parts = trimmed.split("\\s+", 2);
        String commandWord = parts[0].toLowerCase(); // Safe to lower case command word
        String rest = parts.length > 1 ? parts[1].trim() : "";

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
            case CMD_UPDATE -> prepareUpdate(rest);
            default -> throw new NimbusException(ERR_UNKNOWN_COMMAND);
        };
    }

    // ==================================================================================
    // Helper Methods
    // ==================================================================================

    private static Command prepareTodo(String args) throws NimbusException {
        validateDescription(args);
        return new AddTodoCommand(args);
    }

    private static Command prepareFind(String args) throws NimbusException {
        if (args.isEmpty()) {
            throw new NimbusException(USAGE_FIND);
        }
        return new FindCommand(args);
    }

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

    private static Command parseDeadline(String args) throws NimbusException {
        String[] split = args.split("\\s*/by\\s*", 2);
        if (split.length < 2) throw new NimbusException(USAGE_DEADLINE);

        String desc = split[0].trim();
        String byStr = split[1].trim();

        validateDescription(desc);
        if (byStr.isEmpty()) throw new NimbusException(USAGE_DEADLINE);

        LocalDateTime by = DateTimeUtil.parseDateTime(byStr);
        return new AddDeadlineCommand(desc, by);
    }

    private static Command parseEvent(String args) throws NimbusException {
        String[] splitFrom = args.split("\\s*/from\\s*", 2);
        if (splitFrom.length < 2) throw new NimbusException(USAGE_EVENT);

        String desc = splitFrom[0].trim();
        String[] splitTo = splitFrom[1].split("\\s*/to\\s*", 2);
        if (splitTo.length < 2) throw new NimbusException(USAGE_EVENT);

        String fromStr = splitTo[0].trim();
        String toStr = splitTo[1].trim();

        validateDescription(desc);
        if (fromStr.isEmpty() || toStr.isEmpty()) throw new NimbusException(USAGE_EVENT);

        LocalDateTime from = DateTimeUtil.parseDateTime(fromStr);
        LocalDateTime to = DateTimeUtil.parseDateTime(toStr);
        return new AddEventCommand(desc, from, to);
    }

    private static void validateDescription(String desc) throws NimbusException {
        if (desc.isEmpty()) throw new NimbusException(ERR_DESC_EMPTY);
        if (desc.contains("|")) throw new NimbusException(ERR_INVALID_PIPE);
    }

    /**
     * Parses a single line from the storage file into a {@link Task} object.
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

            return switch (type) {
                case "T" -> new Todo(desc, isDone);
                case "D" -> (parts.length >= 4)
                        ? new Deadline(desc, DateTimeUtil.parseDateTime(parts[3]), isDone)
                        : null;
                case "E" -> (parts.length >= 5)
                        ? new Event(desc, DateTimeUtil.parseDateTime(parts[3]), DateTimeUtil.parseDateTime(parts[4]), isDone)
                        : null;
                default -> null;
            };

        } catch (Exception e) {
            return null; // Skip corrupted lines
        }
    }

    /**
     * Parses arguments for the 'update' command.
     * FIX: Now correctly captures descriptions without needing a /d flag.
     */
    private static Command prepareUpdate(String args) throws NimbusException {
        String[] parts = args.trim().split(" ", 2);
        if (parts.length < 1 || parts[0].isEmpty()) {
            throw new NimbusException("Please specify the task index to update.");
        }

        int index;
        try {
            index = Integer.parseInt(parts[0]) - 1; // 0-based index
        } catch (NumberFormatException e) {
            throw new NimbusException("Invalid task index provided.");
        }

        EditTaskDescriptor descriptor = new EditTaskDescriptor();

        if (parts.length > 1) {
            String params = " " + parts[1]; // Add padding for regex safety

            // 1. Identify where the dates start
            int byIndex = params.indexOf(" /by ");
            int fromIndex = params.indexOf(" /from ");
            int toIndex = params.indexOf(" /to ");

            // 2. Determine where the description ends (it ends at the first flag found)
            int cutOffIndex = params.length();
            if (byIndex != -1) cutOffIndex = Math.min(cutOffIndex, byIndex);
            if (fromIndex != -1) cutOffIndex = Math.min(cutOffIndex, fromIndex);
            if (toIndex != -1) cutOffIndex = Math.min(cutOffIndex, toIndex);

            // 3. Extract the description (everything before the first flag)
            String potentialDesc = params.substring(0, cutOffIndex).trim();
            if (!potentialDesc.isEmpty()) {
                descriptor.setDescription(potentialDesc);
            }

            // 4. Extract Flags
            // We use simple substring extraction based on the known indices
            if (byIndex != -1) {
                String val = extractArg(params, byIndex + 5, new int[]{fromIndex, toIndex});
                descriptor.setBy(val);
            }
            if (fromIndex != -1) {
                String val = extractArg(params, fromIndex + 7, new int[]{byIndex, toIndex});
                descriptor.setFrom(val);
            }
            if (toIndex != -1) {
                String val = extractArg(params, toIndex + 5, new int[]{byIndex, fromIndex});
                descriptor.setTo(val);
            }
        }

        return new UpdateCommand(index, descriptor);
    }

    /**
     * Helper to extract argument value until the next flag or end of string.
     */
    private static String extractArg(String full, int startIndex, int[] otherFlagIndices) {
        int endIndex = full.length();
        for (int idx : otherFlagIndices) {
            // If another flag starts after this one, cut off there
            if (idx > startIndex && idx < endIndex) {
                endIndex = idx;
            }
        }
        return full.substring(startIndex, endIndex).trim();
    }
}







