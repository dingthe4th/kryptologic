package Kryptologic.MainGame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.EmptyStackException;
import java.util.Optional;
import java.util.ResourceBundle;

public class B5_KryptoChecker implements Initializable {
    @FXML AnchorPane checkerPane;
    @FXML JFXButton checkButton;
    @FXML JFXButton updateScoreButton;
    @FXML JFXButton nextHandButton;
    @FXML JFXListView playersListView;
    @FXML JFXTextField expressionField;
    @FXML Label penguMsgPrompt;
    @FXML Label kryptoWindowMsgPrompt;
    @FXML ImageView flopCard1;
    @FXML ImageView flopCard2;
    @FXML ImageView flopCard3;
    @FXML ImageView turnCard;
    @FXML ImageView riverCard;
    @FXML ImageView targetCard;
    // "G = {∧,∨,↑,↓,→,¬,⇔,⊻,⌫}." //
    @Override public void initialize(URL location, ResourceBundle resources) {
        playerList = FXCollections.observableArrayList();
        playerList2 = FXCollections.observableArrayList();
        gameUpdateList = FXCollections.observableArrayList();
        playersListView.setItems(playerList);
    }
    @FXML private void handleTrue() {
        String val = expressionField.getText();
        if(val.equals("Enter logical expression here")) val = "T";
        else val = expressionField.getText().concat("T");
        expressionField.setText(val);
    }
    @FXML private void handleFalse() {
        String val = expressionField.getText();
        if(val.equals("Enter logical expression here")) val = "F";
        else val = expressionField.getText().concat("F");
        expressionField.setText(val);
    }
    @FXML private void handleAnd() {
        String val = expressionField.getText();
        if(val.equals("Enter logical expression here")) val = "∧";
        else val = expressionField.getText().concat("∧");
        expressionField.setText(val);
    }
    @FXML private void handleOr() {
        String val = expressionField.getText();
        if(val.equals("Enter logical expression here")) val = "∨";
        else val = expressionField.getText().concat("∨");
        expressionField.setText(val);
    }
    @FXML private void handleNand() {
        String val = expressionField.getText();
        if(val.equals("Enter logical expression here")) val = "↑";
        else val = expressionField.getText().concat("↑");
        expressionField.setText(val);
    }
    @FXML private void handleNor() {
        String val = expressionField.getText();
        if(val.equals("Enter logical expression here")) val = "↓";
        else val = expressionField.getText().concat("↓");
        expressionField.setText(val);
    }
    @FXML private void handleIf() {
        String val = expressionField.getText();
        if(val.equals("Enter logical expression here")) val = "→";
        else val = expressionField.getText().concat("→");
        expressionField.setText(val);
    }
    @FXML private void handleIff() {
        String val = expressionField.getText();
        if(val.equals("Enter logical expression here")) val = "⇔";
        else val = expressionField.getText().concat("⇔");
        expressionField.setText(val);
    }
    @FXML private void handleNot() {
        String val = expressionField.getText();
        if(val.equals("Enter logical expression here")) val = "¬";
        else val = expressionField.getText().concat("¬");
        expressionField.setText(val);
    }
    @FXML private void handleXor() {
        String val = expressionField.getText();
        if(val.equals("Enter logical expression here")) val = "⊻";
        else val = expressionField.getText().concat("⊻");
        expressionField.setText(val);
    }
    @FXML private void handleBackSpace() {
        String s = expressionField.getText();
        if (s != null && s.length() != 0)
            s = s.substring(0, s.length()-1);
        expressionField.setText(s);
    }
    @FXML private void handleCloseP() {
        String val = expressionField.getText();
        if(val.equals("Enter logical expression here")) val = ")";
        else val = expressionField.getText().concat(")");
        expressionField.setText(val);
    }
    @FXML private void handleOpenP() {
        String val = expressionField.getText();
        if(val.equals("Enter logical expression here")) val = "(";
        else val = expressionField.getText().concat("(");
        expressionField.setText(val);
    }
    @FXML private void setNextHandButton() throws IOException {
        if(scoreUpdated) {
            goBacktoActualGamePlay();
        }
        else {
            penguMsgPrompt.setText("Oh oh.");
            kryptoWindowMsgPrompt.setText("Please update score first.");
        }
    }
    @FXML private void setPlayersListViewUpdate() {
        playersListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String a = playersListView.getSelectionModel().getSelectedItem().toString();
                kryptoWindowMsgPrompt.setText(a+" is currently selected.");
                penguMsgPrompt.setText("Oh.");
            }
        });

    }
    @FXML private void setUpdateScoreButton() throws NullPointerException {
        if(!isCheck) {
            penguMsgPrompt.setText("Check it");
            kryptoWindowMsgPrompt.setText("Check and see if your answer is correct first!");
            return;
        }
        String a;
        try {
            a = playersListView.getSelectionModel().getSelectedItem().toString();
        }
        catch (NullPointerException e) {
            a = "";
        }
        if(!a.equals("")) {
            /*
             * NOTE: the next if statement checks if the player to be updated
             * is Krypto AI.
             * The AI will call Krypto by himself
             * Users are not allowed to update his score.
             * #noToCheating
             */
            if(a.equals("Krypto AI")) {
                kryptoWindowMsgPrompt.setText("Krypto AI did not called for Krypto!");
                penguMsgPrompt.setText("That's illegal.");
                return;
            }
            /*
             *   The next if statement check if the score is already updated
             *   if so, the player is not allowed to update the score for the current hand
             *   #noToCheating
             */

            if(scoreUpdated) {
                kryptoWindowMsgPrompt.setText("Score for hand " + handCount + " is already updated.");
                penguMsgPrompt.setText("Hey, come on.");
                return;
            }
            checkerPane.setOpacity(.25);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("KryptoLogic NOTICE");
            alert.setHeaderText("This would be final and executory.");
            alert.setContentText("Continue?");
            Optional<ButtonType> userChoice = alert.showAndWait();
            checkerPane.setOpacity(1);
            if(userChoice.get() == (ButtonType.OK)) {
                if(lastScore == -1) {
                    boolean playerCanAnswer = false;
                    for(Player player: playerList2) {
                        if(player.getName().equals(a)) {
                            if(player.isAllowed()) {
                                playerCanAnswer = true;
                                break;
                            }
                        }
                    }
                    if(playerCanAnswer) {
                        if(correct) {
                            playerScored(a,handCount);
                            kryptoWindowMsgPrompt.setText(lastScorer + " scored " + lastScore);
                            penguMsgPrompt.setText("Nice");
                            scoreUpdated = true;
                        }
                        else {
                            playerScored(a,handCount);
                            kryptoWindowMsgPrompt.setText(lastScorer+ " scored " +lastScore);
                            penguMsgPrompt.setText("Aww");
                            scoreUpdated = true;
                        }
                    }
                    else {
                        penguMsgPrompt.setText("No.");
                        kryptoWindowMsgPrompt.setText(a + " is not allowed to answer.");
                    }
                }
                else {
                    if(correct) {
                        playerScored(a,handCount);
                        kryptoWindowMsgPrompt.setText(lastScorer + " scored " + lastScore);
                        penguMsgPrompt.setText("Nice");
                        scoreUpdated = true;
                    }
                    else {
                        playerScored(a,handCount);
                        kryptoWindowMsgPrompt.setText(lastScorer+ " scored " +lastScore);
                        penguMsgPrompt.setText("Aww");
                        scoreUpdated = true;
                    }
                }
            }
        }
        else {
            checkerPane.setOpacity(.25);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("KryptoLogic ERROR");
            alert.setHeaderText("No player selected.");
            alert.setContentText("Please select player who called Krypto.");
            alert.showAndWait();
            checkerPane.setOpacity(1);
            kryptoWindowMsgPrompt.setText("Please select player who called Krypto.");
            penguMsgPrompt.setText("Hmmm?");
            scoreUpdated = false;
        }
    }
    @FXML void kryptoCheckerCaller() throws EmptyStackException {
        String expression = expressionField.getText();
        int count = 0;
        for(int i=0; i<expression.length(); i++) {
            if(Character.isLetter(expression.charAt(i)) || equationTree.isOperation(expression.charAt(i)))
                count++;
        }

        if(negate && count !=10) {
            penguMsgPrompt.setText("Huh.");
            kryptoWindowMsgPrompt.setText("Invalid Expression");
            return;
        }
        if(!negate && count !=9) {
            penguMsgPrompt.setText("Huh.");
            kryptoWindowMsgPrompt.setText("Invalid Expression");
            return;
        }

        if(kryptoChecker(expressionField.getText(),targetResult)) {
            penguMsgPrompt.setText("Nice.");
            kryptoWindowMsgPrompt.setText("Correct Answer");
            correct = true;
            isCheck = true;
        }
        else {
            penguMsgPrompt.setText("Oof.");
            kryptoWindowMsgPrompt.setText("Incorrect Answer");
            correct = false;
            isCheck = true;
        }
    }

    public EquationTree equationTree = new EquationTree();
    public String targetResult, lastScorer, lastScorer2;
    public ObservableList<String> playerList;
    public ObservableList<Player> playerList2;
    public ObservableList<String> gameUpdateList;
    public boolean correct = false, negate = false, scoreUpdated = false, isCheck = false;
    public int lastScore = 1, handCount = 1, lastScore2 = 1;
    public void goBacktoActualGamePlay() throws IOException {
        Parent checkerRoot;
        FXMLLoader x = new FXMLLoader(getClass().getResource("A_GameState.fxml"));
        checkerRoot = (AnchorPane) x.load();

        B4_ActualGamePlay control = x.getController();

        control.getPassableInformation(playerList2,correct,lastScore,lastScorer,scoreUpdated,false,handCount,gameUpdateList,lastScorer2,lastScore2);

        Scene checkerScene = new Scene(checkerRoot);
        checkerScene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        checkerScene.getStylesheets().add(getClass().getResource("GamePlay.css").toExternalForm());
        checkerScene.getStylesheets().add(getClass().getResource("AddPlayer.css").toExternalForm());
        Stage checkerStage = (Stage) checkerPane.getScene().getWindow();
        checkerStage.setScene(checkerScene);
    }
    public boolean kryptoChecker(String expression, String target) {
        if(equationTree.toPostfix(expression).length() == expression.length()) return false;
        return equationTree.kryptoChecker(expression,target);
    }
    public int scoreToBeAcquired(Player player,boolean correct) {
        if (correct) {
            if(lastScore==-1 && lastScorer2.equals(player.getName())) {
                lastScore2 = lastScore;
                lastScore = lastScore2 * 2;
            }
            else if (player.getName().equals(lastScorer)) {
                int temp = lastScore2;
                lastScore2 = lastScore;
                lastScore = temp * 2;
            } else {
                lastScore2 = lastScore;
                lastScore = 1;
            }
        }

        else if (!correct) {
            lastScore2 = lastScore;
            lastScore = -1;
        }

        return lastScore;
    }
    public void playerScored(String playerName, int handNumber) {

        String name2 = playerName;

        for(Player player: playerList2) {

            String name = player.getName();

            if(name == name2) {
                int score = scoreToBeAcquired(player,correct);
                lastScore = score;
                switch(handNumber) {
                    case 1:
                        player.setH1(score);
                        player.sethTotal(player.getTotal());
                        break;
                    case 2:
                        player.setH2(score);
                        player.sethTotal(player.getTotal());
                        break;
                    case 3:
                        player.setH3(score);
                        player.sethTotal(player.getTotal());
                        break;
                    case 4:
                        player.setH4(score);
                        player.sethTotal(player.getTotal());
                        break;
                    case 5:
                        player.setH5(score);
                        player.sethTotal(player.getTotal());
                        break;
                    case 6:
                        player.setH6(score);
                        player.sethTotal(player.getTotal());
                        break;
                    case 7:
                        player.setH7(score);
                        player.sethTotal(player.getTotal());
                        break;
                    case 8:
                        player.setH8(score);
                        player.sethTotal(player.getTotal());
                        break;
                    case 9:
                        player.setH9(score);
                        player.sethTotal(player.getTotal());
                        break;
                    case 10:
                        player.setH10(score);
                        player.sethTotal(player.getTotal());
                        break;
                    default: break;
                }
                break;
            }
        }
        lastScorer2 = lastScorer;
        lastScorer = name2;
        if(correct) handCount++;
        else handCount = handNumber;
    }
    void getPassableInformation(ObservableList<Player> getPlayerList,ObservableList<String> gameUpdateList, String target) {

        this.playerList2 = FXCollections.observableArrayList(getPlayerList);
        this.gameUpdateList.addAll(gameUpdateList);

        for(Player player: getPlayerList) {
            this.playerList.add(player.getName().trim());
        }
        this.targetResult = target;
    }
    void getPassableInformation2(boolean negate, String lastScorer, int lastScore, int handCount, String lastScorer2, int lastScore2) {
        this.negate = negate;
        this.lastScore = lastScore;
        this.lastScorer = lastScorer;
        this.handCount = handCount;
        this.lastScorer2 = lastScorer2;
        this.lastScore2 = lastScore2;
    }
    void getPassableInformation3(Image a, Image b, Image c, Image d, Image e, Image f) {
        flopCard1.setImage(a);
        flopCard2.setImage(b);
        flopCard3.setImage(c);
        turnCard.setImage(d);
        riverCard.setImage(e);
        targetCard.setImage(f);
    }
}
