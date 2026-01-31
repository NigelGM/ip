package nimbus.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading from and writing to the save file on disk.
 * <p>
 * Storage is file-path based and stores tasks as plain text lines.
 */
public class Storage {
    private final Path path;

    /**
     * Creates a Storage instance pointing to the given file path.
     *
     * @param filePath Path to the save file (e.g. {@code "data/nimbus.txt"}).
     */
    public Storage(String filePath) {
        this.path = Paths.get(filePath); // e.g. "data/duke.txt"
    }

    /**
     * Loads all lines from the save file.
     *
     * @return List of lines. Returns an empty list if the file does not exist.
     * @throws IOException If reading from disk fails.
     */
    public List<String> loadLines() throws IOException {
        if (!Files.exists(path)) {
            return new ArrayList<>(); // first run, no file yet
        }
        return Files.readAllLines(path);
    }

    /**
     * Writes all lines to the save file, creating parent directories if necessary.
     *
     * @param lines Lines to save.
     * @throws IOException If writing to disk fails.
     */
    public void saveLines(List<String> lines) throws IOException {
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent()); // ensure ./data exists
        }
        Files.write(path, lines);
    }
}
