package Kryptologic.MainGame;

public class node {
    String data;
    int precedence;
    node right;
    node left;

    node(String item) {
        data = item;
//        precedence = getPrecedence(item);
        right = left = null;
    }

    private int getPrecedence(String C)
    {
        switch(C){
            case "¬": return 1;
            case "∧": return 2;
            case "∨": return 3;
            case "→":
            case "↑":
            case "↓":
                return 4;
            case "⊻": return 5;
            case "⇔": return 6;
            default: return 0;
        }
    }
}
