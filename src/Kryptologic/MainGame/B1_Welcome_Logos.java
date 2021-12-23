package Kryptologic.MainGame;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.plugin.javascript.navig.Anchor;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class B1_Welcome_Logos implements Initializable {
    @FXML AnchorPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeOut();
    }

    private void FadeOut() {
        FadeTransition fade = new FadeTransition(Duration.seconds(3));
        fade.setNode(mainPane);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();
        fade.setOnFinished(event -> {
            try {
                goToNextScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void goToNextScreen() throws IOException {
        Parent secondRoot;
        secondRoot = (AnchorPane) FXMLLoader.load(getClass().getResource("A_Welcome_2.fxml"));
        Scene secondScene = new Scene(secondRoot);
        secondScene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        Stage secondStage = (Stage) mainPane.getScene().getWindow();
        secondStage.setScene(secondScene);
    }
}


