package nimbus.ui;

import java.io.IOException;
import java.util.Collections;

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

/**
 * Represents a custom chat bubble control for the Nimbus GUI.
 * <p>
 * This control consists of a text label for the message and an {@link ImageView} for the
 * speaker's avatar. It supports alternating alignments (left for Nimbus, right for User)
 * through CSS styling and structural flipping.
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Private constructor to initialize a dialog box with text and an image.
     *
     * @param text The message content to be displayed.
     * @param img The avatar image of the speaker.
     * @param styleClass The CSS class to apply to the dialog label (e.g., "user-label").
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
    }

    /**
     * Applies relevant CSS classes to the controls and sets the base container style.
     */
    private void applyStyling(String labelStyleClass) {
        dialog.getStyleClass().add(labelStyleClass);
        this.getStyleClass().add("dialog-box");
    }

    /**
     * Clips the speaker's avatar into a circular shape for a modern aesthetic.
     */
    private void clipAvatar() {
        Circle clip = new Circle(25, 25, 25);
        displayPicture.setClip(clip);
    }

    /**
     * Flips the dialog box horizontally.
     * Reverses the order of children (moving image to the left) and changes
     * alignment to {@code Pos.TOP_LEFT} for bot responses.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Factory method to create a user dialog box.
     * The bubble is right-aligned by default with the "user-label" style.
     *
     * @param text The user's input message.
     * @param img The user's avatar image.
     * @return A styled DialogBox for the user.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, "user-label");
    }

    /**
     * Factory method to create a response dialog box from Nimbus.
     * The bubble is flipped to the left and styled with the "nimbus-label" class.
     *
     * @param text The response message from Nimbus.
     * @param img The cloud avatar for Nimbus.
     * @return A flipped and styled DialogBox for Nimbus.
     */
    public static DialogBox getNimbusDialog(String text, Image img) {
        var db = new DialogBox(text, img, "nimbus-label");
        db.flip();
        return db;
    }
}
