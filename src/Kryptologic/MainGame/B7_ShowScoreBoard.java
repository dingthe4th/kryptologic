package Kryptologic.MainGame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class B7_ShowScoreBoard implements Initializable {
    @FXML AnchorPane scorePane;
    @FXML private TableView<Player> scoreBoard;
    @FXML private TableColumn<Player,String> playerName;
    @FXML private TableColumn<Player,Integer> hand1;
    @FXML private TableColumn<Player,Integer> hand2;
    @FXML private TableColumn<Player,Integer> hand3;
    @FXML private TableColumn<Player,Integer> hand4;
    @FXML private TableColumn<Player,Integer> hand5;
    @FXML private TableColumn<Player,Integer> hand6;
    @FXML private TableColumn<Player,Integer> hand7;
    @FXML private TableColumn<Player,Integer> hand8;
    @FXML private TableColumn<Player,Integer> hand9;
    @FXML private TableColumn<Player,Integer> hand10;
    @FXML private TableColumn<Player,Integer> handTotal;
    ObservableList<Player> playerList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerList = FXCollections.observableArrayList();
        playerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        hand1.setCellValueFactory(new PropertyValueFactory<>("h1"));
        hand2.setCellValueFactory(new PropertyValueFactory<>("h2"));
        hand3.setCellValueFactory(new PropertyValueFactory<>("h3"));
        hand4.setCellValueFactory(new PropertyValueFactory<>("h4"));
        hand5.setCellValueFactory(new PropertyValueFactory<>("h5"));
        hand6.setCellValueFactory(new PropertyValueFactory<>("h6"));
        hand7.setCellValueFactory(new PropertyValueFactory<>("h7"));
        hand8.setCellValueFactory(new PropertyValueFactory<>("h8"));
        hand9.setCellValueFactory(new PropertyValueFactory<>("h9"));
        hand10.setCellValueFactory(new PropertyValueFactory<>("h10"));
        handTotal.setCellValueFactory(new PropertyValueFactory<>("hTotal"));
        scoreBoard.setItems(playerList);
    }

    @FXML private void exitGame() {
        System.exit(0);
    }
    @FXML private void backToMainMenu() throws IOException {
        Parent mainMenu = (AnchorPane) FXMLLoader.load(getClass().getResource("A_Welcome_2.fxml"));
        Scene mainScene = new Scene(mainMenu);
        mainScene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        Stage mainStage = (Stage) scorePane.getScene().getWindow();
        mainStage.setScene(mainScene);
    }
    public void getPassableInformation(ObservableList<Player> playerList) {
        this.playerList.addAll(playerList);
    }
}
