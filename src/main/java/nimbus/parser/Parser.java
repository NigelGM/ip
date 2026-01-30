package nimbus.parser;

import java.time.LocalDateTime;

import nimbus.command.AddDeadlineCommand;
import nimbus.command.AddEventCommand;
import nimbus.command.AddTodoCommand;
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

public class Parser {

    public static Command parse(String fullCommand) throws NimbusException {
        String trimmed = fullCommand == null ? "" : fullCommand.trim();
        if (trimmed.isEmpty()) {
            throw new NimbusException("Please type a command.");
        }

        String[] parts = trimmed.split("\\s+", 2);
        String commandWord = parts[0];
        String rest = parts.length > 1 ? parts[1].trim() : "";

        switch (commandWord) {
            case "bye":
                return new ByeCommand();

            case "list":
                return new ListCommand();

            case "mark": {
                int idx = parseOneBasedIndex(rest, "mark");
                return new MarkCommand(idx);
            }

            case "unmark": {
                int idx = parseOneBasedIndex(rest, "unmark");
                return new UnmarkCommand(idx);
            }

            case "delete": {
                int idx = parseOneBasedIndex(rest, "delete");
                return new DeleteCommand(idx);
            }

            case "todo":
                if (rest.isEmpty()) {
                    throw new NimbusException("The description of a todo cannot be empty.");
                }
                return new AddTodoCommand(rest);

            case "deadline":
                return parseDeadline(rest);

            case "event":
                return parseEvent(rest);

            default:
                throw new NimbusException("I'm sorry, but I don't know what that means.");
        }
    }

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

    // Used by TaskList when loading from save file
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







