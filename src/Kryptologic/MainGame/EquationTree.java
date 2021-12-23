package Kryptologic.MainGame;
import java.util.EmptyStackException;
import java.util.Stack;

// "G = {∧,∨,↑,↓,→,¬,⇔,⊻}." //
public class EquationTree {

    private String x = "";
    public node root;
    public EquationTree() {
        this.root = null;
    }

    public String evaluateTree(node root) {
        if (root == null) return "";
        if (root.left == null && root.right == null) return root.data;
        String left_total = evaluateTree(root.left);
        String right_total = evaluateTree(root.right);
        if (root.data.equals("∧")) return AND(left_total, right_total);
        if (root.data.equals("∨")) return OR(left_total, right_total);
        if (root.data.equals("↑")) return NAND(left_total, right_total);
        if (root.data.equals("↓")) return NOR(left_total, right_total);
        if (root.data.equals("→")) return IFT(left_total, right_total);
        if (root.data.equals("⊻")) return XOR(left_total, right_total);
        if (root.data.equals("⇔")) return IFF(left_total, right_total);
        return "";
    }
    // returns postOrder of the root
    public String postOrder() {
        postOrder(this.root);
        String a = x;
        x = "";
        return a;
    }
    // recursive function of postOrder
    private void postOrder(node root) {
        if(!empty()) {
            if(root.left != null) postOrder(root.left);
            if(root.right != null) postOrder(root.right);
            x = x + root.data;
        }
    }

    // infix recursion
    private void inorder(node root) {
        if(!empty()) {
            if(root.left != null) inorder(root.left);
            x = x + root.data;
            if(root.right != null) inorder(root.right);
        }
    }

    // infix
    public String inorder() {
        inorder(root);
        String a = x;
        x = "";
        return a;
    }

    // inserts an item to the tree
    public void insert(String item) {
        this.root = insertItem(root,item);
    }
    // recursive function for this.class insert function

    private node insertItem(node root, String item) {
        node temp = new node(item);
        // creates a starter tree
        if(root == null) {
            root = temp;
            return root;
        }
        // traverse the last root
        if(root.precedence > temp.precedence) {
            root.left = insertItem(root.left,item) ;
        }
        else {
            root.right = insertItem(root.right,item);
        }
        return root;
    }

    // checks if tree is empty
    public boolean empty() {
        return root == null;
    }

    // returns the size of the tree
    public int size() { return getSize(root);}
    // recursive function of getting the size of tree
    public int getSize(node root) {
        int s = 1;
        if(empty()) return 0;
        if(root.left != null) {
            s = s + getSize(root.left);
        }
        if(root.right != null) {
            s = s + getSize(root.right);
        }
        return s;
    }
    public int getPrecedence(String C) {
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
    public boolean isOperation(char C){
        return !Character.isLetter(C) && C != ')' && C!= '(';
    }

    public String solveMiniEquation(String abc) {
        String a = String.valueOf(abc.charAt(0));
        String b = String.valueOf(abc.charAt(1));
        String c = "";
        if (abc.length() == 3)  c = String.valueOf(abc.charAt(2));
        switch(b.charAt(0)) {
            case '¬':
                return NOT(b);
            case '∧':
                return AND(a,c);
            case '∨':
                return OR(a,c);
            case '↑':
                return NAND(a,c);
            case '↓':
                return NOR(a,c);
            case '→':
                return IFT(a,c);
            case '⇔':
                return IFF(a,c);
            case '⊻':
                return XOR(a,c);
        }
        return "";
    }
    public boolean kryptoChecker(String expression, String target) throws EmptyStackException {
        String postfix = toPostfix(expression);
        Stack<String> operation = new Stack<>();

        for(int i=0; i<postfix.length(); i++) {
            if(!isOperation(postfix.charAt(i)) && Character.isLetter(postfix.charAt(i))) {
                String a = Character.toString(postfix.charAt(i));
                operation.push(a);

            }
            else if(isOperation(postfix.charAt(i)) && !operation.isEmpty()){
                switch(postfix.charAt(i)){
                    case '¬':
                        String aa = "¬";
                        String a = operation.peek();
                        String a2 = NOT(a);
                        operation.pop();
                        operation.push(a2);
                        break;
                    case '∧':
                        String t = "∧";
                        String b = operation.peek();
                        operation.pop();
                        String c = operation.peek();
                        operation.pop();
                        operation.push(AND(c,b));
                        break;
                    case '∨':
                        String t2 = "∨";
                        String d = operation.peek();
                        operation.pop();
                        String e = operation.peek();
                        operation.pop();
                        operation.push(OR(e,d));
                        break;
                    case '↑':
                        String t3 = "↑";
                        String f = operation.peek();
                        operation.pop();
                        String g = operation.peek();
                        operation.pop();
                        operation.push(NAND(g,f));
                        break;
                    case '↓':
                        String t4 = "↓";
                        String h = operation.peek();
                        operation.pop();
                        String k = operation.peek();
                        operation.pop();
                        operation.push(NOR(k,h));
                        break;
                    case '→':
                        String t5 = "→";
                        String l = operation.peek();
                        operation.pop();
                        String m = operation.peek();
                        operation.pop();
                        operation.push(IFT(m,l));
                        break;
                    case '⇔':
                        String t6 = "⇔";
                        String n = operation.peek();
                        operation.pop();
                        String o = operation.peek();
                        operation.pop();
                        operation.push(IFF(o,n));
                        break;
                    case '⊻':
                        String t7 = "⊻";
                        String tt = operation.peek();
                        operation.pop();
                        String u = operation.peek();
                        operation.pop();
                        operation.push(XOR(u,tt));
                        break;
                }
            }
        }
        return operation.peek().equals(target);
    }

    public String toPostfix(String infix){
        infix = "(" + infix + ")";
        int infix_L = infix.length();

        Stack<String> operation = new Stack<>();
        String postfix = "";

        for(int i=0; i< infix_L; i++){

            // check if variable
            if(Character.isLetter(infix.charAt(i))) {
                postfix = postfix.concat(Character.toString(infix.charAt(i)));
            }

            // check if (
            else if(infix.charAt(i) == '('){
                operation.push(Character.toString(infix.charAt(i)));
            }

            // check if )
            else if(infix.charAt(i) == ')'){
                while(!operation.peek().equals("(") && !operation.isEmpty()){
                    postfix = postfix.concat(operation.peek());
                    operation.pop();
                }
                operation.pop();
            }

            // check if operator
            else {
                String a = operation.peek();
                if(!Character.isLetter(a.charAt(0)))
                {
                    while(getPrecedence(infix.charAt(i)) <= getPrecedence(operation.peek())){
                        if(infix.charAt(i) == '¬' && Character.isLetter(infix.charAt(i+1))){
                            break;
                        }
                        postfix = postfix.concat((operation.peek()));
                        operation.pop();
                    }
                }
                operation.push(Character.toString(infix.charAt(i)));
            }
        }
        return postfix;
    }
    public int getPrecedence(char C){
        switch(C){
//            '↑','↓','→','⇔','⊻'
            // "G = {∧,∨,↑,↓,→,¬,⇔,⊻}."
            case '¬': return 1;
            case '∧': return 2;
            case '∨': return 3;
            case '→': return 4;
            case '⊻':
            case '↑':
            case '↓':
                return 5;
            case '⇔': return 6;
            default: return 0;
        }
    }
    public String NOT(String a){
        if(a.equals("T")) return "F";
        return "T";
    }
    public String AND(String a, String b) {
        if (a.equals("T") && b.equals("T")) return "T";
        return "F";
    }
    public String OR(String a, String b) {
        if (a.equals("T") && b.equals("T")) return "T";
        else if (a.equals("T") && b.equals("F")) return "T";
        else if (a.equals("F") && b.equals("T")) return "T";
        else if (a.equals("F") && b.equals("F")) return "F";
        return "F";
    }
    public String NAND(String a, String b){
        if (a.equals("T") && b.equals("T")) return "F";
        else if (a.equals("T") && b.equals("F")) return "T";
        else if (a.equals("F") && b.equals("T")) return "T";
        else return "T";
    }
    public String NOR(String a, String b){
        if (a.equals("T") && b.equals("T")) return "F";
        else if (a.equals("T") && b.equals("F")) return "F";
        else if (a.equals("F") && b.equals("T")) return "F";
        else return "T";
    }
    public String IFT(String a, String b){
        if (a.equals("T") && b.equals("T")) return "T";
        else if (a.equals("T") && b.equals("F")) return "F";
        else if (a.equals("F") && b.equals("T")) return "T";
        else return "T";
    }
    public String IFF(String a, String b){
        if (a.equals("T") && b.equals("T")) return "T";
        else if (a.equals("T") && b.equals("F")) return "F";
        else if (a.equals("F") && b.equals("T")) return "F";
        else return "T";
    }
    public String XOR(String a, String b){
        if (a.equals("T") && b.equals("T")) return "F";
        else if (a.equals("T") && b.equals("F")) return "T";
        else if (a.equals("F") && b.equals("T")) return "T";
        else return "F";
    }

    public static void main(String[] args) {
        EquationTree s = new EquationTree();
        System.out.println(s.kryptoChecker("¬((((T→F)⇔F)↑T)↑F)","T"));
        // (F∨((F↓F)↑F))⇔T T
    }
}
