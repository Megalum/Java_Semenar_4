import java.util.Stack;
import java.util.*;
import java.util.logging.Logger;

import static java.lang.Character.isDigit;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getAnonymousLogger();
        LinkedList ll = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            ll.add(i);
        }
        expFirst(ll);
        expSecond();
        String res = convertToPostfix("12+3*(4-7)/(5+1*4)-4");
        logger.info(res);
        int calc = calcPostfix(res);
        logger.info(Integer.toString(calc));
    }

    public static void expFirst(LinkedList text){
        Logger logger = Logger.getAnonymousLogger();
        Collections.reverse(text);
        logger.info(text.toString());
    }

    public static void expSecond(){
        Logger logger = Logger.getAnonymousLogger();
        LinkedList ll = new LinkedList<>();
        while (true){
            System.out.println("enqueue - помещает элемент в конец очереди " +
                    "         \ndequeue - возвращает первый элемент из очереди и удаляет его" +
                    "         \nfirst   - возвращает первый элемент из очереди, не удаляя");
            String text = (new Scanner(System.in)).next();
            if (text.equals("enqueue")){
                enqueue(ll);
            } else if (text.equals("dequeue")) {
                dequeue(ll);
            } else if (text.equals("first")) {
                first(ll);
            } else {
                break;
            }
        }

    }

    private static LinkedList enqueue(LinkedList ll){
        ll.add((new Scanner(System.in)).next());
        return ll;
    }

    private static LinkedList dequeue(LinkedList ll){
        Logger logger = Logger.getAnonymousLogger();
        logger.info(ll.remove(0).toString());
        return ll;
    }

    private static void first(LinkedList ll){
        Logger logger = Logger.getAnonymousLogger();
        logger.info(ll.get(0).toString());
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '(' || c == ')';
    }

    private static int getPrecedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

   private static boolean isOperand(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9');
   }

    private static boolean isOperandCalc(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

   public static String convertToPostfix(String infix) {
        Stack<Character> stack = new Stack<Character>();
        StringBuffer postfix = new StringBuffer(infix.length());
        char c;
        for (int i = 0; i < infix.length(); i++) {
            c = infix.charAt(i);
            if (isOperand(c)) {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(" " + stack.pop() + " ");
                }
                if (!stack.isEmpty() && stack.peek() != '(')
                    return null;
                else if(!stack.isEmpty())
                    stack.pop();
            }
            else if (isOperator(c)) {
                postfix.append(" ");
                if (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())) {
                    postfix.append(stack.pop() + " ");
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            postfix.append(" " + stack.pop());
        }
        return postfix.toString();
   }

   public static int calcPostfix(String postfix){
       char c = '1';
       for (int i = 0; i < postfix.length(); i++) {
           c = postfix.charAt(i);
           if (isOperandCalc(c)) {
               return 0;
           }
       }
       String[] exp = postfix.replaceAll("[\\s]{2,}", " ").split(" ");
       int res = 0;
       Stack<Integer> st = new Stack<>();
       for (int i = 0; i < exp.length; i++) {
           if (isDigit(exp[i].charAt(0))){
               st.push(Integer.parseInt(exp[i]));
           } else {
               switch (exp[i]){
                   case "+":
                       res = st.pop() + st.pop();
                       st.push(res);
                       break;
                   case "-":
                       res = - st.pop() + st.pop();
                       st.push(res);
                       break;
                   case "/":
                       int s = st.pop();
                       res = st.pop() / s;
                       st.push(res);
                       break;
                   case "*":
                       res = st.pop() * st.pop();
                       st.push(res);
                       break;
                   default:
                       break;
               }
           }
       }
       return res;
   }

}