import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    public int calculate(String s) {
        Deque<Integer> stateStack = new ArrayDeque<>();
        int total = 0;
        int currentNumber = 0;
        int operationSign = 1;
        
        for (int index = 0; index < s.length(); index++) {
            char character = s.charAt(index);
            
            if (Character.isDigit(character)) {
                currentNumber = currentNumber * 10 + (character - '0');
                continue;
            }
            
            if (character == '(') {
                stateStack.push(total);
                stateStack.push(operationSign);
                total = 0;
            } else if (character == ')') {
                total += operationSign * currentNumber;
                currentNumber = 0;
                total *= stateStack.pop();
                total += stateStack.pop();
            } else if (character == '+') {
                total += operationSign * currentNumber;
                currentNumber = 0;
                operationSign = 1;
            } else if (character == '-') {
                total += operationSign * currentNumber;
                currentNumber = 0;
                operationSign = -1;
            }
        }
        
        if (currentNumber != 0) {
            total += operationSign * currentNumber;
        }
        
        return total;
    }
}