package nimbus.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Handles file I/O operations for saving and loading tasks.
 */
public class Storage {
    private final Path path;

    public Storage(String filePath) {
        this.path = Paths.get(filePath);
    }

    /**
     * Saves the task list to the storage file.
     *
     * @param lines List of task strings in storage format.
     * @throws IOException If writing to disk fails.
     */
    public void save(List<String> lines) throws IOException {
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        // Corrected: Use 'this.path' field to resolve scope error
        Files.write(this.path, lines);
    }

    public List<String> loadLines() throws IOException {
        return Files.exists(path) ? Files.readAllLines(path) : new java.util.ArrayList<>();
    }
}