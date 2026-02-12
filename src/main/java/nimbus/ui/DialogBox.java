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

public class DialogBox extends HBox {

    @FXML
    @SuppressWarnings("unused")
    private Label dialog;

    @FXML
    @SuppressWarnings("unused")
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        // 1. Add spacing between the image and the text box
        this.setSpacing(10);
    }

    /**
     * Flips the dialog box so that the ImageView appears on the left.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        this.getChildren().setAll(tmp);
        this.setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Helper to set the style of the text box.
     * @param color The hex code for the background color.
     */
    private void setDialogStyle(String color) {
        dialog.setStyle(
                "-fx-background-color: " + color + ";" + // Background Color
                        "-fx-background-radius: 15;" +           // Rounded Corners
                        "-fx-padding: 10;" +                     // Space inside the box
                        "-fx-text-fill: black;" +                // Text Color
                        "-fx-font-size: 12px;"                   // Text Size
        );
    }

    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        // User (Right) = Light Green Box
        db.setDialogStyle("#dbf2e3");
        return db;
    }

    public static DialogBox getBotDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        // Nimbus (Left) = Light Blue Box
        db.setDialogStyle("#cce5ff");
        return db;
    }
}

