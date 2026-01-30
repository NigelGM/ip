package nimbus.storage;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    void loadLines_fileMissing_returnsEmptyList() throws Exception {
        Path file = tempDir.resolve("data").resolve("nimbus.txt");
        Storage storage = new Storage(file.toString());

        List<String> lines = storage.loadLines();
        assertNotNull(lines);
        assertEquals(0, lines.size());
    }

    @Test
    void saveThenLoad_roundTrip_sameContent() throws Exception {
        Path file = tempDir.resolve("data").resolve("nimbus.txt");
        Storage storage = new Storage(file.toString());

        List<String> expected = List.of(
                "T | 0 | borrow book",
                "D | 1 | return book | 2019-12-02T18:00"
        );

        storage.saveLines(expected);
        List<String> actual = storage.loadLines();

        assertEquals(expected, actual);
    }
}
