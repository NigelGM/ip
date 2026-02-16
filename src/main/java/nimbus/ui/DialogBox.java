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

public class DialogBox extends HBox {

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    // Use CSS classes instead of hardcoded strings
    private DialogBox(String text, Image img, String styleClass) {
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

        // Code Quality: Add CSS class instead of setting inline style
        dialog.getStyleClass().add(styleClass);
        this.getStyleClass().add("dialog-box"); // Adds padding/spacing from CSS

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
        // Pass the CSS class name "user-label"
        return new DialogBox(text, img, "user-label");
    }

    public static DialogBox getNimbusDialog(String text, Image img) {
        var db = new DialogBox(text, img, "nimbus-label");
        db.flip();
        return db;
    }
}
