package Kryptologic.MainGame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class aBoutController {
    @FXML
    AnchorPane aBoutPane;

    @FXML
    private void backToMainMenu() throws IOException {
        Parent mainMenu;
        mainMenu = (AnchorPane) FXMLLoader.load(getClass().getResource("A_Welcome_2.fxml"));
        Scene mainScene = new Scene(mainMenu);
        mainScene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        Stage mainStage = (Stage) aBoutPane.getScene().getWindow();
        mainStage.setScene(mainScene);
    }
}
