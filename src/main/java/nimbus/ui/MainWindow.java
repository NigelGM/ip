package nimbus.ui;

import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import nimbus.Nimbus;

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

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        sendButton.setDefaultButton(true);

        userImage = loadImageOrNull("/image/user.png");
        nimbusImage = loadImageOrNull("/image/nimbus.png");
    }

    public void setNimbus(Nimbus nimbus) {
        this.nimbus = nimbus;
        // Greet the user on startup
        dialogContainer.getChildren().add(DialogBox.getNimbusDialog(nimbus.getGreeting(), nimbusImage));
    }

    @FXML
    private void handleUserInput() {
        if (nimbus == null) return;

        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) return;

        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));
        String response = nimbus.getResponse(input);
        dialogContainer.getChildren().add(DialogBox.getNimbusDialog(response, nimbusImage));

        userInput.clear();

        if (nimbus.isExit()) {
            Platform.runLater(Platform::exit);
        }
    }

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



