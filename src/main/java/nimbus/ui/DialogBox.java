package nimbus.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Represents a custom chat bubble control for the Nimbus GUI.
 * <p>
 * This control consists of a text label and an avatar image. It supports:
 * <ul>
 * <li>Alternating alignment (User right, Nimbus left)</li>
 * <li>Circular avatar clipping</li>
 * <li>Error message highlighting (Red bubbles)</li>
 * <li>Smooth fade-in entrance animations</li>
 * </ul>
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Private constructor to initialize a dialog box.
     *
     * @param text       The message content.
     * @param img        The avatar image.
     * @param styleClass The initial CSS class for the label.
     */
    private DialogBox(String text, Image img, String styleClass) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Critical failure: Could not load DialogBox.fxml", e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        applyStyling(styleClass);
        clipAvatar();
        animateEntrance(); // Trigger fade-in
    }

    /**
     * Applies relevant CSS classes to the controls.
     */
    private void applyStyling(String labelStyleClass) {
        dialog.getStyleClass().add(labelStyleClass);
        this.getStyleClass().add("dialog-box");
    }

    /**
     * Clips the avatar into a circle.
     */
    private void clipAvatar() {
        Circle clip = new Circle(25, 25, 25); // Radius 25 matches 50x50 ImageView
        displayPicture.setClip(clip);
    }

    /**
     * Plays a smooth fade-in animation when the dialog box is created.
     */
    private void animateEntrance() {
        FadeTransition ft = new FadeTransition(Duration.millis(200), this);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    /**
     * Changes the bubble style to indicate an error (Red theme).
     */
    private void setErrorStyle() {
        ObservableList<String> styles = dialog.getStyleClass();

        // Fix: Just remove it directly.
        // If "nimbus-label" isn't there, this line does nothing safely.
        styles.remove("nimbus-label");

        styles.add("error-label");
    }

    /**
     * Flips the dialog box horizontally for Nimbus responses.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, "user-label");
    }

    public static DialogBox getNimbusDialog(String text, Image img) {
        var db = new DialogBox(text, img, "nimbus-label");
        db.flip();

        // Check for error prefix and apply red style
        if (text != null && text.startsWith("Error:")) {
            db.setErrorStyle();
        }

        return db;
    }
}
