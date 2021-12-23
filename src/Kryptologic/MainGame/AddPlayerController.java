package Kryptologic.MainGame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPlayerController implements Initializable {
    @FXML AnchorPane addPlayerPane;
    @FXML JFXTextField addPlayerNameField;
    @FXML JFXButton addPlayerButton;
    @FXML Label addPlayerMsgPrompt;
    @FXML ListView addedPlayersList;
    @FXML Label noPlayersLabel;

    public ObservableList<String> playerList;
    @FXML private void addPlayer() {
        String newPlayer = addPlayerNameField.getText();
        boolean registered = false;

        for(String player : playerList) {
            if(player.equals(newPlayer)) registered = true;
        }

        if(registered) {
            addPlayerMsgPrompt.setText("Player already registered.");
        }

        else {
            if(newPlayer.equals("")) {
                addPlayerMsgPrompt.setText("Player name cannot be blank.");
            } else {
                playerList.add(newPlayer);
                addPlayerMsgPrompt.setText(newPlayer+" is added.");
                addPlayerNameField.setText("");
            }
        }

        noPlayersLabel.setVisible(false);
    }
    @FXML private void removePlayer() {
        String newPlayer = addPlayerNameField.getText();
        boolean registered = false;

        for(String player : playerList) {
            if(player.equals(newPlayer)) registered = true;
        }

        if(registered) {
            playerList.remove(newPlayer);
            addPlayerMsgPrompt.setText(newPlayer+" is removed.");
            addPlayerNameField.setText("");
        }

        else {
            if(newPlayer.equals("")) {
                addPlayerMsgPrompt.setText("Player name cannot be blank.");
            }
            else addPlayerMsgPrompt.setText("Player not registered.");
        }

        if(playerList.isEmpty()) noPlayersLabel.setVisible(true);
    }
    @FXML private void startNewGame() throws IOException {
        if(!playerList.isEmpty()) checkIfReady();
        else addPlayerMsgPrompt.setText("Error: No players found.");
    }
    @FXML private void NewGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("A_GameState.fxml"));
        Parent newGameRoot;
        newGameRoot = (AnchorPane) loader.load();

        // pass playerList to A_GameState controller
        B4_ActualGamePlay B4AGP = loader.getController();
        B4AGP.getPlayerList(playerList);

        Scene newGameScene = new Scene(newGameRoot);
        newGameScene.getStylesheets().add(getClass().getResource("GamePlay.css").toExternalForm());
        newGameScene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        newGameScene.getStylesheets().add(getClass().getResource("AddPlayer.css").toExternalForm());
        Stage newGameStage = (Stage) addPlayerPane.getScene().getWindow();
        newGameStage.setScene(newGameScene);
    }
    private void checkIfReady() throws IOException {
        addPlayerPane.setOpacity(.25);
        Alert readyPrompt = new Alert(Alert.AlertType.CONFIRMATION);
        readyPrompt.setHeaderText("The game will start as soon as you close this window.");
        readyPrompt.setContentText("Are you ready to play?");

        Optional<ButtonType> result = readyPrompt.showAndWait();
        if(result.get() == ButtonType.OK) NewGame();
        else addPlayerPane.setOpacity(1);
    }
    @Override public void initialize(URL location, ResourceBundle resources) {
        playerList = FXCollections.observableArrayList();
        addedPlayersList.setItems(playerList);
    }
}
