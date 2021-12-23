package Kryptologic.MainGame;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HowToPlayController {
    @FXML
    AnchorPane howToPlayPane;       // How to play window

    @FXML
    AnchorPane sampleGPane;     // Sample game play window

    @FXML
    AnchorPane scoringSystemPane;     // Scoring system window

    @FXML
    private void backToMainMenu() throws IOException {
        Parent mainMenu;
        mainMenu = (AnchorPane) FXMLLoader.load(getClass().getResource("A_Welcome_2.fxml"));
        Scene mainScene = new Scene(mainMenu);
        mainScene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        Stage mainStage = (Stage) howToPlayPane.getScene().getWindow();
        mainStage.setScene(mainScene);
    }

    @FXML
    private void backToHowToPlay() throws IOException {
        Parent howToPane;
        howToPane = (AnchorPane) FXMLLoader.load(getClass().getResource("A_Howtoplay.fxml"));
        Scene howToScene = new Scene(howToPane);
        howToScene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        Stage howToStage = (Stage) sampleGPane.getScene().getWindow();
        howToStage.setScene(howToScene);
    }

    @FXML
    private void goToSampleGPlay() throws IOException {
        Parent gameP;
        gameP = (AnchorPane) FXMLLoader.load(getClass().getResource("A_SampleGamePlay.fxml"));
        Scene gameC = new Scene(gameP);
        gameC.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        Stage gameS = (Stage) howToPlayPane.getScene().getWindow();
        gameS.setScene(gameC);
    }

    @FXML
    private void backToSampleGPlay() throws IOException {
        Parent gameP;
        gameP = (AnchorPane) FXMLLoader.load(getClass().getResource("A_SampleGamePlay.fxml"));
        Scene gameC = new Scene(gameP);
        gameC.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        Stage gameS = (Stage) scoringSystemPane.getScene().getWindow();
        gameS.setScene(gameC);
    }

    @FXML
    private void goToScoringSystem() throws IOException {
        Parent scoreP;
        scoreP = (AnchorPane) FXMLLoader.load(getClass().getResource("A_ScoringSystem.fxml"));
        Scene scoreC = new Scene(scoreP);
        scoreC.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        Stage scoreS = (Stage) sampleGPane.getScene().getWindow();
        scoreS.setScene(scoreC);
    }

}
