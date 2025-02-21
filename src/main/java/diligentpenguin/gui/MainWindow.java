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
 * Represents a controller for the main GUI.
 * This class is adopted from SE JavaFX tutorial:
 * <a href="https://se-education.org/guides/tutorials/javaFxPart5.html">...</a>
 * @author Debbie Hii (@flexibo)
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

    // User image taken from my Github profile picture: https://github.com/DiligentPenguinn
    // Chatbot image taken from:
    // https://lovepik.com/image-401570104/penguin-animal-small-avatar-illustration-design.html
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private final Image chatbotImage = new Image(this.getClass().getResourceAsStream("/images/chatbot.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /** Injects the DiligentPenguin instance. */
    public void setDp(DiligentPenguin d) {
        diligentPenguin = d;
    }

    /** Load saved data file if it exists. */
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
        String[] responses = diligentPenguin.getResponse(input);
        String response = responses[0];
        String output = responses[1];

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDpDialog(response, chatbotImage)
        );
        userInput.clear();
        userInput.setText(output);
        if (diligentPenguin.isOver()) {
            stage.close();
        }
    }
}

