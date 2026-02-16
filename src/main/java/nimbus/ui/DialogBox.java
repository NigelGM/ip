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
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

/**
 * A custom control for dialog bubbles in the Nimbus GUI.
 * Enforces layout constraints to keep text contained within the window.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img, String styleClass) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("FXML loading failed for DialogBox", e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        // --- THE UI FIXES ---
        // Tells the label to move text to the next line
        dialog.setWrapText(true);
        // Forces the bubble to grow vertically to fit wrapped text
        dialog.setMinHeight(Region.USE_PREF_SIZE);
        // Hard limit to prevent the horizontal leak
        dialog.setMaxWidth(250.0);

        dialog.getStyleClass().add(styleClass);
        displayPicture.setClip(new Circle(25, 25, 25));
    }

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
        return db;
    }
}