package Kryptologic.MainGame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class B2_GameWindow_1 implements Initializable {
    @FXML
    ImageView exitGirl;

    @FXML
    private AnchorPane mainMenuPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainMenuPane.setOpacity(0);
        FadeIn();
    }

    private void FadeIn() {
        FadeTransition fade = new FadeTransition(Duration.seconds(2));
        fade.setNode(mainMenuPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    @FXML private void startNewGame() throws IOException {
        /* calls the addPlayerWindow first */
        FXMLLoader x = new FXMLLoader(getClass().getResource("A_AddPlayer.fxml"));
        Parent addPlayerRoot = x.load();
        Scene addPlayerScene = new Scene(addPlayerRoot);
        addPlayerScene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        addPlayerScene.getStylesheets().add(getClass().getResource("AddPlayer.css").toExternalForm());
        Stage addPlayerStage = (Stage) mainMenuPane.getScene().getWindow();
        addPlayerStage.setScene(addPlayerScene);
        addPlayerStage.show();
    }

    @FXML private void handleHighScore() throws IOException {
        Parent highScoreRoot;
        highScoreRoot = (AnchorPane) FXMLLoader.load(getClass().getResource("A_HighScores.fxml"));
        Scene highScoreScene = new Scene(highScoreRoot);
        highScoreScene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        Stage highScoreStage = (Stage) mainMenuPane.getScene().getWindow();
        highScoreStage.setScene(highScoreScene);
    }

    @FXML private void handleAbout() throws IOException {
        Parent aboutRoot;
        aboutRoot = (AnchorPane) FXMLLoader.load(getClass().getResource("A_About.fxml"));
        Scene aboutScene = new Scene(aboutRoot);
        aboutScene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        aboutScene.getStylesheets().add(getClass().getResource("GamePlay.css").toExternalForm());
        Stage aboutStage = (Stage) mainMenuPane.getScene().getWindow();
        aboutStage.setScene(aboutScene);
    }

    @FXML private void handleHowToPlay() throws IOException {
        Parent howRoot;
        howRoot = (AnchorPane) FXMLLoader.load(getClass().getResource("A_Howtoplay.fxml"));
        Scene howScene = new Scene(howRoot);
        howScene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        Stage howStage = (Stage) mainMenuPane.getScene().getWindow();
        howStage.setScene(howScene);
    }

    @FXML private void exitGamePrompt(ActionEvent event) throws InterruptedException {
        mainMenuPane.setOpacity(.25);
        Alert exitPrompt = new Alert(Alert.AlertType.CONFIRMATION);
        exitPrompt.setHeaderText("Are you sure on quitting the game?");
        exitPrompt.setContentText("All progress will be lost.");
        Optional<ButtonType> userChoice = exitPrompt.showAndWait();
        if(userChoice.get() == (ButtonType.OK)) {
            System.exit(0);
        }
        else mainMenuPane.setOpacity(1);
    }

}
