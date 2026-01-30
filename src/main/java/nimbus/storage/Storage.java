package nimbus.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path path;

    public Storage(String filePath) {
        this.path = Paths.get(filePath); // e.g. "data/duke.txt"
    }

    public List<String> loadLines() throws IOException {
        if (!Files.exists(path)) {
            return new ArrayList<>(); // first run, no file yet
        }
        return Files.readAllLines(path);
    }

    public void saveLines(List<String> lines) throws IOException {
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent()); // ensure ./data exists
        }
        Files.write(path, lines);
    }
}
