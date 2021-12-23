package Kryptologic.MainGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Cards {
    private Random rand = new Random();
    private ArrayList<String> operators = new ArrayList<>();
    private ArrayList<String> operands = new ArrayList<>();
    private String[] operatorStack = new String[4];
    private String[] operandStack = new String[6];

    // "G = {∧,∨,↑,↓,→,¬,⇔,⊻,⌫}." //

    private void init() {
        for(int i=0; i<8; i++) {
            operators.add("∧");
            operators.add("∨");
            operators.add("↑");
            operators.add("↓");
            operators.add("⇔");
            operators.add("→");
            operators.add("⊻");
        }
        for(int i=0; i<28; i++) {
            operands.add("T");
            operands.add("F");
        }
    }
    private String[] generateOperators() {
        init();
        ArrayList<Integer> alreadyPushed = new ArrayList<>();
        for(int i = 0; i<4; i++) {
            int index = rand.nextInt(56);
            while(alreadyPushed.contains(index)) {
                index = rand.nextInt(56);
            }
            operatorStack[i] = (operators.get(index));
            alreadyPushed.add(index);
        }
        return operatorStack;
    }
    private String[] generateOperands() {
        init();
        ArrayList<Integer> alreadyPushed = new ArrayList<>();
        for(int i = 0; i<6; i++) {
            int index = rand.nextInt(56);
            while(alreadyPushed.contains(index)) {
                index = rand.nextInt(56);
            }
            operandStack[i] = (operands.get(index));
            alreadyPushed.add(index);
        }
        return operandStack;
    }
    public String[] getOperatorStack() { return generateOperators(); }
    public String[] getOperandStack() { return generateOperands(); }
}
