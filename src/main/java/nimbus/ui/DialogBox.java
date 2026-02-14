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
    @FXML
    private Label dialog;

    @FXML
    private ImageView displayPicture;

    /**
     * Private constructor for the DialogBox.
     * The use of 'this.dialog' and 'this.displayPicture' here signals to the IDE
     * that the variables are being accessed and used.
     */
    private DialogBox(String text, Image img, String bubbleStyle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            // Robust error handling replacing printStackTrace
            throw new RuntimeException("Critical failure loading DialogBox FXML", e);
        }

        // Using the private variables here
        dialog.setText(text);
        displayPicture.setImage(img);
        dialog.setStyle(bubbleStyle);

        // Circular avatar crop for a modern look
        Circle clip = new Circle(25, 25, 25);
        displayPicture.setClip(clip);
    }

    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        String style = "-fx-background-color: #0084FF; -fx-text-fill: white; "
                + "-fx-background-radius: 15; -fx-padding: 10;";
        return new DialogBox(text, img, style);
    }

    public static DialogBox getNimbusDialog(String text, Image img) {
        String style = "-fx-background-color: #F0F0F0; -fx-text-fill: black; "
                + "-fx-background-radius: 15; -fx-padding: 10;";
        var db = new DialogBox(text, img, style);
        db.flip();
        return db;
    }
}
