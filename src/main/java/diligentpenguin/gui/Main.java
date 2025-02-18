package diligentpenguin.gui;

import java.io.IOException;
import java.util.Objects;

import diligentpenguin.DiligentPenguin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 * This class is adopted from SE JavaFX tutorial:
 * <a href="https://se-education.org/guides/tutorials/javaFxPart5.html">...</a>
 * @author Debbie Hii (@flexibo)
 */
public class Main extends Application {

    private final DiligentPenguin diligentPenguin = new DiligentPenguin("src/main/data/",
            "tasks.txt");
    private final String title = "DiligentPenguin Chatbot";
    // Icon image taken from this source:
    // https://www.shutterstock.com/image-vector/penguin-flat-vector-illustration-on-white-2459383763
    private final Image iconImage = new Image(this.getClass().getResourceAsStream("/images/icon.png"));
    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle(title);
            stage.getIcons().add(iconImage);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            scene.getStylesheets().add(Objects.requireNonNull(Main.class
                    .getResource("/css/dialog-box.css")).toExternalForm());
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDp(diligentPenguin);
            fxmlLoader.<MainWindow>getController().initializeDp();
            fxmlLoader.<MainWindow>getController().setStage(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
