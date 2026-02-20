package nimbus;

/**
 * A launcher class to run the application.
 * This is used as a workaround to avoid the "Unsupported JavaFX configuration" warning
 * that occurs when running a JAR file where the main class extends the JavaFX Application class.
 */
public class Launcher {
    /**
     * The main entry point for the entire application.
     * Launches the JavaFX application by delegating to the Main class.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        javafx.application.Application.launch(Main.class, args);
    }
}

