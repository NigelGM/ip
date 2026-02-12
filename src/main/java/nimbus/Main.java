package nimbus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nimbus.ui.MainWindow;

/**
 * JavaFX entry point for Nimbus.
 */
public class Main extends Application {

    private final Nimbus nimbus = new Nimbus();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Nimbus");
        stage.setScene(scene);

        // Give the controller access to Nimbus logic
        MainWindow controller = fxmlLoader.getController();
        controller.setNimbus(nimbus);

        stage.show();
    }
}

