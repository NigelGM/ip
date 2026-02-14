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
 * A custom control using FXML that represents a message bubble in the chat.
 */
public class DialogBox extends HBox {

    // Extracted magic strings into constants for better maintainability
    private static final String USER_BUBBLE_STYLE = "-fx-background-color: #0084FF; "
            + "-fx-text-fill: white; -fx-background-radius: 15; -fx-padding: 10;";
    private static final String NIMBUS_BUBBLE_STYLE = "-fx-background-color: #F0F0F0; "
            + "-fx-text-fill: black; -fx-background-radius: 15; -fx-padding: 10;";

    @FXML
    private Label dialog;

    @FXML
    private ImageView displayPicture;

    /**
     * Private constructor for the DialogBox.
     */
    private DialogBox(String text, Image img, String bubbleStyle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Critical failure loading DialogBox FXML", e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);
        dialog.setStyle(bubbleStyle);

        Circle clip = new Circle(25, 25, 25);
        displayPicture.setClip(clip);
    }

    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a user dialog box with the specified text and image.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, USER_BUBBLE_STYLE);
    }

    /**
     * Creates a Nimbus dialog box with the specified text and image.
     */
    public static DialogBox getNimbusDialog(String text, Image img) {
        var db = new DialogBox(text, img, NIMBUS_BUBBLE_STYLE);
        db.flip();
        return db;
    }
}
