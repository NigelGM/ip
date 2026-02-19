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
 * Controller for the main GUI window.
 * <p>
 * This class handles user interaction, manages the layout of the chat container,
 * and acts as the bridge between the JavaFX frontend and the Nimbus backend logic.
 * It also implements quality-of-life features like auto-scrolling and command history.
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

    private Nimbus nimbus;
    private Image userImage;
    private Image nimbusImage;

    // --- Command History Storage ---
    private final LinkedList<String> commandHistory = new LinkedList<>();
    private int historyPointer = 0;

    /**
     * Initializes the controller class.
     * This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
        // 1. Auto-scroll: Binds scroll pane to dialog container height.
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // 2. Default Button: Allows 'Enter' key to send messages.
        sendButton.setDefaultButton(true);

        // 3. Command History Navigation: Listen for Up/Down arrow keys.
        userInput.setOnKeyPressed(this::handleHistoryNavigation);

        userImage = loadImageOrNull("/image/user.png");
        nimbusImage = loadImageOrNull("/image/nimbus.png");
    }

    /**
     * Sets the Nimbus instance for this window and displays the initial greeting.
     *
     * @param nimbus The main application logic instance.
     */
    public void setNimbus(Nimbus nimbus) {
        this.nimbus = nimbus;
        if (nimbusImage != null) {
            dialogContainer.getChildren().add(DialogBox.getNimbusDialog(nimbus.getGreeting(), nimbusImage));
        }
    }

    /**
     * Handles key press events for navigating command history.
     *
     * @param event The key event triggered by the user.
     */
    private void handleHistoryNavigation(KeyEvent event) {
        if (commandHistory.isEmpty()) return;

        if (event.getCode() == KeyCode.UP) {
            // Move back in history
            historyPointer = Math.max(0, historyPointer - 1);
            updateInputFromHistory();
            event.consume(); // Prevent cursor from jumping to start
        } else if (event.getCode() == KeyCode.DOWN) {
            // Move forward in history
            historyPointer = Math.min(commandHistory.size(), historyPointer + 1);
            updateInputFromHistory();
            event.consume();
        }
    }

    /**
     * Updates the text field based on the current history pointer.
     */
    private void updateInputFromHistory() {
        if (historyPointer == commandHistory.size()) {
            userInput.setText(""); // New command line
        } else {
            userInput.setText(commandHistory.get(historyPointer));
            userInput.positionCaret(userInput.getText().length()); // Move cursor to end
        }
    }

    /**
     * Handles the user pressing the send button or hitting Enter.
     * Captures input, updates history, gets response, and displays dialog bubbles.
     */
    @FXML
    private void handleUserInput() {
        if (nimbus == null) return;

        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) return;

        // Save to History
        commandHistory.add(input);
        historyPointer = commandHistory.size(); // Reset pointer to end

        // Display User Message
        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

        // Get and Display Response
        String response = nimbus.getResponse(input);
        dialogContainer.getChildren().add(DialogBox.getNimbusDialog(response, nimbusImage));

        userInput.clear();

        // Handle exit
        if (nimbus.isExit()) {
            Platform.runLater(Platform::exit);
        }
    }

    /**
     * Helper to load images safely without crashing if the file is missing.
     */
    private static Image loadImageOrNull(String classpathResource) {
        URL url = MainWindow.class.getResource(classpathResource);
        if (url == null) return null;
        try {
            return new Image(url.toExternalForm());
        } catch (Exception e) {
            return null;
        }
    }
}



