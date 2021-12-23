package Kryptologic.MainGame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class B4_ActualGamePlay implements Initializable {

    @FXML AnchorPane actualGamePlayPane;
    @FXML public TableView<Player> scoreBoard;
    @FXML public TableColumn<Player,String> playerName;
    @FXML public TableColumn<Player,Integer> hand1;
    @FXML public TableColumn<Player,Integer> hand2;
    @FXML public TableColumn<Player,Integer> hand3;
    @FXML public TableColumn<Player,Integer> hand4;
    @FXML public TableColumn<Player,Integer> hand5;
    @FXML public TableColumn<Player,Integer> hand6;
    @FXML public TableColumn<Player,Integer> hand7;
    @FXML public TableColumn<Player,Integer> hand8;
    @FXML public TableColumn<Player,Integer> hand9;
    @FXML public TableColumn<Player,Integer> hand10;
    @FXML public TableColumn<Player,Integer> handTotal;
    @FXML ImageView flopCard1;
    @FXML ImageView flopCard2;
    @FXML ImageView flopCard3;
    @FXML ImageView turnCard;
    @FXML ImageView riverCard;
    @FXML ImageView targetCard;
    @FXML Label gameStatus;
    @FXML JFXButton dealCard;
    @FXML JFXListView gameCommentatorListView;
    @FXML Label handNumberDisplay;
    @FXML Label handNumberDisplay2;
    static final int MAX_HAND_COUNT = 10 + 1;            // 10 HANDS in GAME
    static final TableColumn.SortType DESCENDING = TableColumn.SortType.DESCENDING;
    private static final long AI_CALCULATION_TIME = 1200;       // 86.8 SECONDS AI CALCULATION // 1.2 SECONDS
    ObservableList<String> gameUpdateList;
    ObservableList<Player> playerList;
    ObservableList<String> playerListString;
    Krypto_AI ai = new Krypto_AI();
    public Cards generateCard = new Cards();
    public boolean negate = false, handDealt = false;
    public String[] operators = new String[4];
    public String[] operands = new String[6];
    public int operatorIndex = 0;
    public int operandIndex = 0;
    public String lastScorer = "DEFAULT_PLAYER", targetResult = "DEFAULT_RESULT";
    public String lastScorer2 = "DEFAULT_PLAYER2";
    public int lastScore = 1;
    public int lastScore2 = 1;
    public int handCount = 1;
    public boolean correct = false, scoreUpdated = false, gameStart = true, interruptAI = false;
    @FXML public void startAI() {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {

                long startTime = System.currentTimeMillis();
                long elapsedTime = 0L;
                long aveTime = AI_CALCULATION_TIME;

                while(elapsedTime < aveTime) {
                    elapsedTime = (new Date()).getTime() - startTime;
                    if(interruptAI) {
                        this.cancel();
                        break;
                    }

                    if (handCount == MAX_HAND_COUNT) {
                        this.cancel();
                        break;
                    }
                    System.out.println(elapsedTime);
                }
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            try {
                kryptoAI();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        task.setOnCancelled(event -> {
            try {
                if(handCount==MAX_HAND_COUNT)
                    goToShowScoreBoard();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    @FXML public void goToShowScoreBoard() throws IOException {
        Parent checkerRoot;
        FXMLLoader x = new FXMLLoader(getClass().getResource("A_ShowHighScore.fxml"));
        checkerRoot = (AnchorPane) x.load();

        B7_ShowScoreBoard control = x.getController();
        control.getPassableInformation(this.playerList);

        Scene Scene = new Scene(checkerRoot);
        Scene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        Scene.getStylesheets().add(getClass().getResource("GamePlay.css").toExternalForm());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(Scene);
        stage.showAndWait();
    }
    @FXML public void backToMainMenuCaller() throws IOException {
        actualGamePlayPane.setOpacity(.25);
        Alert exitPrompt = new Alert(Alert.AlertType.CONFIRMATION);
        exitPrompt.setHeaderText("Game is currently in play.");
        exitPrompt.setContentText("Back to main menu? \nAll progress will be lost.");
        Optional<ButtonType> userChoice = exitPrompt.showAndWait();
        if(userChoice.get() == (ButtonType.OK)) {
            backToMainMenu();
        }
        else actualGamePlayPane.setOpacity(1);
    }
    @FXML public void goToKryptoChecker() throws IOException {
        boolean humanCanAnswer = false;
        if(handDealt) {
            for(Player player : playerList) {
                if(player.isAllowed() && !player.getName().equals("Krypto AI")) {
                    humanCanAnswer = true;
                    break;
                }
            }

            if(humanCanAnswer) {
                goToKryptoChecker0();
                interruptAI = true;
            }

            else {
                gameStatus.setText("No one is allowed to play this hand.");
            }

        }
        else if (handCount > MAX_HAND_COUNT) {
            gameStatus.setText("Game over. See the results.");
        }
        else {
            gameStatus.setText("Geez. Wait for the hand to be dealt first.");
        }
    }
    public void kryptoAI() throws IOException {
        for(int i=0; i<4; i++) ai.OPERATORS[i] = operators[i];
        for(int i=0; i<6; i++) ai.OPERANDS[i] = operands[i];
        String answer = ai.getExpression(operators,operands,negate,targetResult);

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        long aveTime = 1000;
        long maxTime = 60000;


        while(elapsedTime < aveTime) {
            elapsedTime = (new Date()).getTime() - startTime;
        }
        if(!answer.equals("NO ANSWER")) updateScoreByAI();

        if(!interruptAI) {
            if(!answer.equals("NO ANSWER")) {
                goToKryptoSolution();
            }
            else {
                long startElapsedTime = elapsedTime;
                while(elapsedTime < maxTime) {
                    elapsedTime = (new Date()).getTime() - startElapsedTime;
                }
                goToKryptoSolution();
            }
        }

    }
    public void goToKryptoSolution() throws IOException {
        Parent checkerRoot;
        FXMLLoader x = new FXMLLoader(getClass().getResource("A_KryptoAI.fxml"));
        checkerRoot = (AnchorPane) x.load();

        B6_KryptoSolution control =  x.getController();
        control.getPassableInformation(ai.infix,ai.sol1,ai.sol2,ai.sol3,ai.sol4,ai.sol5);
        control.getPassableInformation2(ai.OPERATORS,ai.OPERANDS,playerList);
        control.getPassableInformation3(this.operandIndex,this.operatorIndex);
        Scene Scene = new Scene(checkerRoot);
        Scene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(Scene);
        stage.showAndWait();
    }
    public void dealCards() {
        while(operandIndex<6 && handCount < MAX_HAND_COUNT) dealOneHand();
        if(handCount < MAX_HAND_COUNT) {
            handDealt = true;
            gameStart = false;
            handNumberDisplay.setText(String.valueOf(handCount));
            handNumberDisplay2.setText(String.valueOf(handCount));
            gameStatus.setText("Hand " + handCount + " currently in play.");
        }
    }
    public void dealOneHand() {
        String OPERATOR = "";
        String OPERAND = operands[operandIndex];
        if(operatorIndex<4)  OPERATOR = operators[operatorIndex];

        /*river card*/ if(operandIndex == 4) {
            if(OPERAND.equals("T")) {
                riverCard.setImage(new Image("Kryptologic/MainGame/cards/t not.png"));
                negate = true;
            }
            else {
                riverCard.setImage(new Image("Kryptologic/MainGame/cards/F.png"));
                negate = false;
            }
        }
        /*target card*/ if(operandIndex == 5) {
            if(OPERAND.equals("T")) {
                targetCard.setImage(new Image("Kryptologic/MainGame/cards/T_Target.png"));
                targetResult = "T";
            }
            else {
                targetCard.setImage(new Image("Kryptologic/MainGame/cards/F_Target.png"));
                targetResult = "F";
            }
        }
        /*flop card 1*/ if(operandIndex == 0) {
            // "G = {∧,∨,↑,↓,→,¬,⇔,⊻}." //
            if(OPERAND.equals("T")) {
                switch(OPERATOR) {
                    case "∧":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/t and.png"));
                        break;
                    case "∨":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/t or.png"));
                        break;
                    case "↑":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/t nand.png"));
                        break;
                    case "↓":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/t nor.png"));
                        break;
                    case "→":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/t if.png"));
                        break;
                    case "⇔":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/t iff.png"));
                        break;
                    case "⊻":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/t xor.png"));
                        break;
                }
            }
            else {
                switch(OPERATOR) {
                    case "∧":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/f and.png"));
                        break;
                    case "∨":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/f or.png"));
                        break;
                    case "↑":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/f nand.png"));
                        break;
                    case "↓":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/f nor.png"));
                        break;
                    case "→":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/f if.png"));
                        break;
                    case "⇔":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/f iff.png"));
                        break;
                    case "⊻":
                        flopCard1.setImage(new Image("Kryptologic/MainGame/cards/f xor.png"));
                        break;
                }
            }
        }
        /*flop card 2*/ if(operandIndex == 1) {
            // "G = {∧,∨,↑,↓,→,¬,⇔,⊻}." //
            if(OPERAND.equals("T")) {
                switch(OPERATOR) {
                    case "∧":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/t and.png"));
                        break;
                    case "∨":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/t or.png"));
                        break;
                    case "↑":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/t nand.png"));
                        break;
                    case "↓":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/t nor.png"));
                        break;
                    case "→":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/t if.png"));
                        break;
                    case "⇔":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/t iff.png"));
                        break;
                    case "⊻":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/t xor.png"));
                        break;
                }
            }
            else {
                switch(OPERATOR) {
                    case "∧":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/f and.png"));
                        break;
                    case "∨":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/f or.png"));
                        break;
                    case "↑":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/f nand.png"));
                        break;
                    case "↓":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/f nor.png"));
                        break;
                    case "→":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/f if.png"));
                        break;
                    case "⇔":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/f iff.png"));
                        break;
                    case "⊻":
                        flopCard2.setImage(new Image("Kryptologic/MainGame/cards/f xor.png"));
                        break;
                }
            }
        }
        /*flop card 3*/ if(operandIndex == 2) {
            // "G = {∧,∨,↑,↓,→,¬,⇔,⊻}." //
            if(OPERAND.equals("T")) {
                switch(OPERATOR) {
                    case "∧":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/t and.png"));
                        break;
                    case "∨":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/t or.png"));
                        break;
                    case "↑":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/t nand.png"));
                        break;
                    case "↓":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/t nor.png"));
                        break;
                    case "→":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/t if.png"));
                        break;
                    case "⇔":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/t iff.png"));
                        break;
                    case "⊻":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/t xor.png"));
                        break;
                }
            }
            else {
                switch(OPERATOR) {
                    case "∧":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/f and.png"));
                        break;
                    case "∨":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/f or.png"));
                        break;
                    case "↑":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/f nand.png"));
                        break;
                    case "↓":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/f nor.png"));
                        break;
                    case "→":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/f if.png"));
                        break;
                    case "⇔":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/f iff.png"));
                        break;
                    case "⊻":
                        flopCard3.setImage(new Image("Kryptologic/MainGame/cards/f xor.png"));
                        break;
                }
            }
        }
        /*turn card */ if(operandIndex == 3) {
            // "G = {∧,∨,↑,↓,→,¬,⇔,⊻}." //
            if(OPERAND.equals("T")) {
                switch(OPERATOR) {
                    case "∧":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/t and.png"));
                        break;
                    case "∨":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/t or.png"));
                        break;
                    case "↑":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/t nand.png"));
                        break;
                    case "↓":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/t nor.png"));
                        break;
                    case "→":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/t if.png"));
                        break;
                    case "⇔":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/t iff.png"));
                        break;
                    case "⊻":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/t xor.png"));
                        break;
                }
            }
            else {
                switch(OPERATOR) {
                    case "∧":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/f and.png"));
                        break;
                    case "∨":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/f or.png"));
                        break;
                    case "↑":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/f nand.png"));
                        break;
                    case "↓":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/f nor.png"));
                        break;
                    case "→":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/f if.png"));
                        break;
                    case "⇔":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/f iff.png"));
                        break;
                    case "⊻":
                        turnCard.setImage(new Image("Kryptologic/MainGame/cards/f xor.png"));
                        break;
                }
            }
        }

        operandIndex++;
        operatorIndex++;
    }
    public void goToKryptoChecker0() throws IOException{
        Parent checkerRoot;
        FXMLLoader x = new FXMLLoader(getClass().getResource("A_PlayerCallKrypto.fxml"));
        checkerRoot = (AnchorPane) x.load();

        B5_KryptoChecker control = x.getController();
        control.getPassableInformation(playerList, gameUpdateList, targetResult);
        control.getPassableInformation2(negate,lastScorer,lastScore,this.handCount,lastScorer2,lastScore2);
        control.getPassableInformation3(flopCard1.getImage(),flopCard2.getImage(),flopCard3.getImage(),
                turnCard.getImage(),riverCard.getImage(),targetCard.getImage());

        Scene checkerScene = new Scene(checkerRoot);
        checkerScene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        checkerScene.getStylesheets().add(getClass().getResource("AddPlayer.css").toExternalForm());
        Stage checkerStage = (Stage) actualGamePlayPane.getScene().getWindow();
        checkerStage.setScene(checkerScene);
    }
    public void getPlayerList(ObservableList<String> getPlayerList) {
        playerListString = getPlayerList;
        playerList.add(new Player("Krypto AI"));
        for(String player: getPlayerList) {
            playerList.add(new Player(player));
        }
    }
    public void gameCommentary() {
        if(scoreUpdated) {
            if(correct) {
                gameUpdateList.add(lastScorer.toUpperCase() + " scored " + lastScore + " in hand " + handCount);
                gameUpdateList.add("Proceeding to hand #" + (handCount+1));
            }
            else {
                gameUpdateList.add(lastScorer.toUpperCase() + " scored " + lastScore + " in hand " + handCount);
                gameUpdateList.add("Hand " + (handCount) + " is reset.");
                gameUpdateList.add(lastScorer.toUpperCase() + " is not allowed to answer in this round.");
            }
        }
    }
    public void getPassableInformation(ObservableList<Player> playerList, boolean correct,
                                       int lastScore, String lastScorer, boolean scoreUpdated,boolean gameStart,int handCount,
                                       ObservableList<String> gameUpdateList, String lastScorer2, int lastScore2) throws IOException {
        this.handCount = handCount;
        if(handCount==MAX_HAND_COUNT) {
            goToShowScoreBoard();
        }
        handNumberDisplay.setText(String.valueOf(handCount));
        handNumberDisplay2.setText(String.valueOf(handCount));
        if(correct) {
            handNumberDisplay.setText(String.valueOf(handCount));
            handNumberDisplay2.setText(String.valueOf(handCount));
            gameStatus.setText(lastScorer.toUpperCase() + " scored " + lastScore);
        }
        if(lastScore == -1) {
            gameStatus.setText("Hand " + handCount + " is reset.");
        }
        this.playerList.removeAll();
        this.playerList.addAll(playerList);
        this.correct = correct;
        this.lastScorer = lastScorer;
        this.lastScorer2 = lastScorer2;
        this.lastScore = lastScore;
        this.lastScore2 = lastScore2;
        this.scoreUpdated = scoreUpdated;
        this.gameStart = gameStart;
        this.gameUpdateList.addAll(gameUpdateList);
        gameCommentary();
    }
    public void backToMainMenu() throws IOException {
        Parent mainMenu = (AnchorPane) FXMLLoader.load(getClass().getResource("A_Welcome_2.fxml"));
        Scene mainScene = new Scene(mainMenu);
        mainScene.getStylesheets().add(getClass().getResource("Button.css").toExternalForm());
        Stage mainStage = (Stage) actualGamePlayPane.getScene().getWindow();
        mainStage.setScene(mainScene);

    }
    public int scoreToBeAcquired(String playerName,boolean correct) {
        if (correct) {
            if(gameStart) {
                lastScore = 1;
                lastScore2 = 1;
            }

            else if(lastScore==-1 && lastScorer2.equals(playerName)) {
                int temp = lastScore2;
                lastScore2 = lastScore;
                lastScore = temp * 2;
            }

            else if (playerName.equals(lastScorer)) {
                lastScore2 = lastScore;
                lastScore = lastScore * 2;
            } else lastScore = 1;
        }

        else if (!correct) {
            lastScore2 = lastScore;
            lastScore = -1;
        }

        return lastScore;
    }
    public void updateScoreByAI() throws IOException {
        String a = "Krypto AI";
        if (!interruptAI) {
            flopCard1.setImage(new Image("Kryptologic/MainGame/cards/blankCard.png"));
            flopCard2.setImage(new Image("Kryptologic/MainGame/cards/blankCard.png"));
            flopCard3.setImage(new Image("Kryptologic/MainGame/cards/blankCard.png"));
            turnCard.setImage(new Image("Kryptologic/MainGame/cards/blankCard.png"));
            riverCard.setImage(new Image("Kryptologic/MainGame/cards/blankCard.png"));
            targetCard.setImage(new Image("Kryptologic/MainGame/cards/blankOrange.png"));
            operandIndex = 0;
            operatorIndex = 0;
            handDealt = false;
            operators = generateCard.getOperatorStack();
            operands = generateCard.getOperandStack();
            interruptAI = false;
            correct = true;
            for (Player player : playerList) {
                String b = player.getName();
                System.out.println(b + " :D ");
                if (b.equals(a)) {
                    int score = scoreToBeAcquired(a, correct);
                    lastScore = score;
                    switch (handCount) {
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
                        default:
                            break;
                    }
                    scoreBoard.getSortOrder().add(handTotal);
                    handTotal.setSortType(DESCENDING);
                    scoreBoard.sort();
                    break;
                }
            }
            lastScorer = a;
            gameStatus.setText("Krypto AI scored " + lastScore + " in hand " + handCount);
            gameUpdateList.add("Krypto AI scored " + lastScore + " in hand " + handCount);
            gameUpdateList.add("Proceeding to hand #" + (handCount+1));
            handCount++;
            if(handCount==MAX_HAND_COUNT) {
                dealCard.setText("RESULT");
            }
        }
    }
    @Override public void initialize(URL location, ResourceBundle resources) {
        playerList = FXCollections.observableArrayList();
        gameUpdateList = FXCollections.observableArrayList();
        playerListString = FXCollections.observableArrayList();
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
        operands = generateCard.getOperandStack();
        operators = generateCard.getOperatorStack();
        gameCommentatorListView.setItems(gameUpdateList);
        for(int i=0; i<4; i++) ai.OPERATORS[i] = operators[i];
        for(int i=0; i<6; i++) ai.OPERANDS[i] = operands[i];

        scoreBoard.getSortOrder().add(handTotal);
        handTotal.setSortType(DESCENDING);
        scoreBoard.sort();

        if(handCount == MAX_HAND_COUNT) {
            try {
                goToShowScoreBoard();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
