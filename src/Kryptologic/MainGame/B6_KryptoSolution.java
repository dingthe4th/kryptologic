package Kryptologic.MainGame;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Kryptologic.MainGame.B4_ActualGamePlay.MAX_HAND_COUNT;

public class B6_KryptoSolution implements Initializable {
    @FXML  AnchorPane solutionPane;
    @FXML private JFXButton confirmButton;
    @FXML Label Answer;
    @FXML Label Solution1;
    @FXML Label Solution2;
    @FXML Label Solution3;
    @FXML Label Solution4;
    @FXML Label Solution5;
    @FXML ImageView flopCard1;
    @FXML ImageView flopCard2;
    @FXML ImageView flopCard3;
    @FXML ImageView turnCard;
    @FXML ImageView riverCard;
    @FXML ImageView targetCard;
    Stage stage;
    String[] operators = new String[4];
    String[] operands = new String[6];
    ObservableList<Player> playerList;
    int operandIndex = 0, operatorIndex = 0;

    @FXML private void goBacktoActualGamePlay() {
        stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    void getPassableInformation(String expression, String s1, String s2, String s3,
                                String s4, String s5) {
        Answer.setText(expression);
        Solution1.setText(s1);
        Solution2.setText(s2);
        Solution3.setText(s3);
        Solution4.setText(s4);
        Solution5.setText(s5);
    }

    void getPassableInformation2(String[] operators, String[] operands, ObservableList<Player> playerList) {
        for(int i=0; i<4; i++) this.operators[i] = operators[i];
        for(int i=0; i<6; i++) this.operands[i] = operands[i];
        dealCards();
        this.playerList.addAll(playerList);
    }

    void getPassableInformation3(int operatorIndex, int operandIndex) {
        this.operandIndex = operandIndex;
        this.operatorIndex = operatorIndex;
    }

    public void dealCards() {
        while(operandIndex<6) dealOneHand();
    }

    public void dealOneHand() {
        String OPERATOR = "";
        String OPERAND = operands[operandIndex];
        if(operatorIndex<4)  OPERATOR = operators[operatorIndex];

        /*river card*/ if(operandIndex == 4)  {
            if(OPERAND.equals("T")) {
                riverCard.setImage(new Image("Kryptologic/MainGame/cards/t not.png"));

            }
            else {
                riverCard.setImage(new Image("Kryptologic/MainGame/cards/F.png"));

            }
        }
        /*target card*/ if(operandIndex == 5) {
            if(OPERAND.equals("T")) {
                targetCard.setImage(new Image("Kryptologic/MainGame/cards/T_Target.png"));
            }
            else {
                targetCard.setImage(new Image("Kryptologic/MainGame/cards/F_Target.png"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerList = FXCollections.observableArrayList();
    }
}
