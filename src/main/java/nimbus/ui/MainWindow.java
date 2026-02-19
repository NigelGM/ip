package nimbus.ui;

import java.net.URL;
import java.util.LinkedList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import nimbus.Nimbus;

/**
 * Controller for the main GUI window of the Nimbus application.
 * <p>
 * This class serves as the primary bridge between the JavaFX frontend and the
 * backend logic. It manages user input, displays task-related feedback in the
 * form of dialog bubbles, and handles quality-of-life features such as
 * auto-scrolling and command history navigation.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    /** The main logic engine for Nimbus. */
    private Nimbus nimbus;

    /** Profile image for the user. */
    private Image userImage;

    /** Profile image for the Nimbus bot. */
    private Image nimbusImage;

    /** Stores previous user inputs for history navigation. */
    private final LinkedList<String> commandHistory = new LinkedList<>();

    /** Current position within the command history list. */
    private int historyPointer = 0;

    /**
     * Initializes the controller. This method is automatically called by the
     * FXML loader after the view file has been successfully loaded.
     * <p>
     * It binds the scroll pane to the container height for auto-scrolling,
     * configures the send button, and initializes image resources.
     */
    @FXML
    public void initialize() {
        // Auto-scroll: Ensure the latest messages are always visible
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Enable 'Enter' key submission
        sendButton.setDefaultButton(true);

        // Set up listeners for command history navigation (Up/Down arrows)
        userInput.setOnKeyPressed(this::handleHistoryNavigation);

        userImage = loadImageOrNull("/image/user.png");
        nimbusImage = loadImageOrNull("/image/nimbus.png");
    }

    /**
     * Injects the Nimbus backend instance and displays the initial greeting.
     *
     * @param nimbus The main application logic instance.
     */
    public void setNimbus(Nimbus nimbus) {
        this.nimbus = nimbus;
        if (nimbusImage != null) {
            String greeting = nimbus.getGreeting();
            dialogContainer.getChildren().add(DialogBox.getNimbusDialog(greeting, nimbusImage));
        }
    }

    /**
     * Handles keyboard events for navigating through previous commands.
     * Uses UP to go back in time and DOWN to move toward current commands.
     *
     * @param event The key event triggered by the user.
     */
    private void handleHistoryNavigation(KeyEvent event) {
        if (commandHistory.isEmpty()) {
            return;
        }

        if (event.getCode() == KeyCode.UP) {
            historyPointer = Math.max(0, historyPointer - 1);
            updateInputFromHistory();
            event.consume();
        } else if (event.getCode() == KeyCode.DOWN) {
            historyPointer = Math.min(commandHistory.size(), historyPointer + 1);
            updateInputFromHistory();
            event.consume();
        }
    }

    /**
     * Updates the text field content based on the current historyPointer position.
     * Moves the caret to the end of the text for convenience.
     */
    private void updateInputFromHistory() {
        if (historyPointer == commandHistory.size()) {
            userInput.setText("");
        } else {
            String pastCommand = commandHistory.get(historyPointer);
            userInput.setText(pastCommand);
            userInput.positionCaret(pastCommand.length());
        }
    }

    /**
     * Processes user input when the send button is clicked or Enter is pressed.
     * <p>
     * It captures the input, updates history, retrieves the response from the
     * backend, and renders the dialog bubbles.
     * <p>
     * Note: The bot response is added within a Platform.runLater call to ensure
     * that JavaFX has sufficient time to calculate layout dimensions for large
     * text blocks (like the help menu) before rendering.
     */
    @FXML
    private void handleUserInput() {
        if (nimbus == null) {
            return;
        }

        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        // Update history and reset pointer
        commandHistory.add(input);
        historyPointer = commandHistory.size();

        // 1. Instantly display user message
        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

        // 2. Fetch response from logic engine
        String response = nimbus.getResponse(input);

        // 3. Render bot response safely on the JavaFX application thread.
        // We only call this ONCE within Platform.runLater to avoid blank duplicate bubbles.
        Platform.runLater(() -> {
            dialogContainer.getChildren().add(DialogBox.getNimbusDialog(response, nimbusImage));
        });

        userInput.clear();

        // Initiate shutdown sequence if the exit command was triggered
        if (nimbus.isExit()) {
            Platform.runLater(Platform::exit);
        }
    }

    /**
     * Safely loads an image from the classpath.
     *
     * @param classpathResource The path to the image resource.
     * @return The loaded Image, or null if the resource is missing or invalid.
     */
    private static Image loadImageOrNull(String classpathResource) {
        URL url = MainWindow.class.getResource(classpathResource);
        if (url == null) {
            return null;
        }
        try {
            return new Image(url.toExternalForm());
        } catch (Exception e) {
            return null;
        }
    }
}



