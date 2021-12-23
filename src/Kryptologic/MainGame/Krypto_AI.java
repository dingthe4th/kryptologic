package Kryptologic.MainGame;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Krypto_AI {


    private EquationTree sample = new EquationTree();
    public String sol1;
    public String sol2;
    public String sol3;
    public String sol4;
    public String sol5;
    public String infix;
    String[] OPERATORS = new String[4];
    String[] OPERANDS = new String[6];

    private String[] randomizeOperators(String[] operators) {
        String[] temp = new String[operators.length];
        Collections.shuffle(Arrays.asList(operators));

        for(int i = 0; i<operators.length; i++)
        {
            temp[i] = operators[i];
        }

        return temp;
    }
    private String[] randomizeOperands(String[] operands) {
        String[] temp = new String[operands.length];
        Collections.shuffle(Arrays.asList(operands));

        for(int i = 0; i<operands.length; i++)
        {
            temp[i] = operands[i];
        }

        return temp;
    }
    private String evaluateExpression(String[] operators, String[] operands) {

        sample.root =  new node(operators[0]);                                  // 1
        sample.root.left = new node(operators[1]);                   // 2
        sample.root.right = new node(operators[2]);                  // 3

        sample.root.left.left = new node(operators[3]);              // 4
        sample.root.left.right = new node(operands[0]) ;            // 5

        sample.root.right.left = new node(operands[1]);             // 6
        sample.root.right.right = new node(operands[2]);            // 7

        sample.root.left.left.left = new node(operands[3]);         // 8
        sample.root.left.left.right = new node(operands[4]);        // 9

        return sample.evaluateTree(sample.root);
    }
    public String getExpression(String[] operators, String[] operands, boolean negate, String target) {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        long maxTime = 20000;
        while(elapsedTime < maxTime) {
            elapsedTime = (new Date()).getTime() - startTime;

            operands = randomizeOperands(operands);
            operators = randomizeOperators(operators);
            if(!negate) {
                evaluateExpression(operators,operands);
            }
            else if(negate) {
                sample.NOT(evaluateExpression(operators,operands));
            }

            String infix = sample.inorder();
            infix = getInfix(infix,negate,target);
            if(!sample.kryptoChecker(infix,target)) continue;
            break;
        }
        this.infix = sample.inorder();
        System.out.println("INFIX FINAL " + infix);
        getInfix(infix,negate,target);
        if(elapsedTime == maxTime) this.infix = "CANNOT SOLVE";
        return infix;
    }

    private String getInfix(String infix,boolean negate,String target) {
        String a = String.valueOf(infix.charAt(0));
        String b = String.valueOf(infix.charAt(1));
        String c = String.valueOf(infix.charAt(2));
        String d = String.valueOf(infix.charAt(3));
        String e = String.valueOf(infix.charAt(4));
        String w = String.valueOf(infix.charAt(5));
        String x = String.valueOf(infix.charAt(6));
        String y = String.valueOf(infix.charAt(7));
        String z = String.valueOf(infix.charAt(8));

        /*      1 2 3 4 5 6 7 8 9
        *       T v F v F v T v F
        *       TvF = T    substring 0-3
        *       T vF = T    substring 3 5
        *       T vT = T    substring 5 7
        *       T vF = T    substring 7 9
         */
            String s1 = sample.solveMiniEquation(infix.substring(0,3));
            sol1 = infix.substring(0,3) + " = " + s1;

            String s2 = sample.solveMiniEquation(s1+infix.substring(3,5));
            sol2 = s1+infix.substring(3,5) + " = " + s2;

            String s3 = sample.solveMiniEquation(s2+infix.substring(5,7));
            sol3 = s2+infix.substring(5,7) + " = " + s3;

            String s4 = sample.solveMiniEquation(s3+infix.substring(7,9));
            sol4 = s3+infix.substring(7,9) + " = " + s4;

            if(!negate) sol5 = "";
            else sol5 = "¬"+s4 + " = " + target;

            if(!negate) infix = "(((("+a+b+c+")"+d+e+")"+w+x+")"+y+z+")";
            else infix = "¬(((("+a+b+c+")"+d+e+")"+w+x+")"+y+z+")";
            this.infix = infix;

        System.out.println(sol1);
        System.out.println(sol2);
        System.out.println(sol3);
        System.out.println(sol4);
        System.out.println(sol5);

        return infix;

    }

    public long elapsedTime(String[] operators, String[] operands, boolean negate, String target) {
        Instant start = Instant.now();
        getExpression(operators,operands,negate,target);
        Instant end = Instant.now();
        Duration t= Duration.between(start,end);
        return t.toMinutes()*60;
    }
    public static void main(String[] args) {
        Krypto_AI sample = new Krypto_AI();
        // "G = {∧,∨,↑,↓,→,¬,⇔,⊻}."
        // FFFFF↑↑↓⇔
        String[] x = {"F","T","T","F","F"};
        String[] y = {"→","↑","↑","⇔"};
//        System.out.println(sample.)
        System.out.println(sample.getExpression(y,x,true,"T"));

    }
}
