package nimbus.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import nimbus.Nimbus;
import java.io.InputStream;

/**
 * Controller for the main GUI.
 */
public class MainWindow {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Nimbus nimbus;

    private Image userImage;
    private Image botImage;

    /**
     * Initializes the UI components after FXML is loaded.
     */
    @FXML
    public void initialize() {
        // Automatically scrolls to the bottom when the dialog container height changes
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects Nimbus logic into this controller.
     *
     * @param nimbus Nimbus app logic.
     */
    public void setNimbus(Nimbus nimbus) {
        this.nimbus = nimbus;

        // Load images using safe resource streams to prevent NullPointerExceptions
        InputStream userStream = MainWindow.class.getResourceAsStream("/image/user.png");
        InputStream botStream = MainWindow.class.getResourceAsStream("/image/nimbus.png");

        // Use assertions to verify file paths exist during development
        assert userStream != null : "User image not found! Check src/main/resources/image/user.png";
        assert botStream != null : "Bot image not found! Check src/main/resources/image/nimbus.png";

        userImage = new Image(userStream);
        botImage = new Image(botStream);

        // Display the initial welcome message
        dialogContainer.getChildren().add(
                DialogBox.getBotDialog("Hello! I'm Nimbus \nWhat can I do for you?", botImage)
        );
    }

    /**
     * Handles user input when Enter is pressed.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim(); // Trim to handle accidental spacing
        if (input.isEmpty()) {
            return;
        }

        String response = nimbus.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBotDialog(response, botImage)
        );

        userInput.clear();
    }
}

