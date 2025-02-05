package diligentpenguin.gui;

import diligentpenguin.DiligentPenguin;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private DiligentPenguin diligentPenguin;
    private Stage stage;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image chatbotImage = new Image(this.getClass().getResourceAsStream("/images/chatbot.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /** Injects the DiligentPenguin instance */
    public void setDp(DiligentPenguin d) {
        diligentPenguin = d;
    }

    /** Load saved data file if it exists */
    public void initializeDp() {
        String response = diligentPenguin.run();
        dialogContainer.getChildren().addAll(
                DialogBox.getDpDialog(response, chatbotImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = diligentPenguin.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDpDialog(response, chatbotImage)
        );
        userInput.clear();
        if (diligentPenguin.isOver()) {
            stage.close();
        }
    }
}

