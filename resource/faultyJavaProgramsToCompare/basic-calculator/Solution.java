import java.util.Stack;

public class Solution {
    public int calculate(String s) {
        int length = s.length();
        int sign = 0; // buggy
        int ans = 0;
        int currNo = 0;
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < length; i++) {
            if (Character.isDigit(s.charAt(i))) {
                currNo = s.charAt(i) - '0';
                while (i + 1 < length && Character.isDigit(s.charAt(i + 1))) {
                    currNo = currNo * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
                currNo = currNo * sign;
                ans += currNo;
                currNo = 0;
                sign = 1;
            }
            else if (s.charAt(i) == '+') {
                sign = 1;
            }
            else if (s.charAt(i) == '-') {
                sign = -1;
            }
            else if (s.charAt(i) == '(') {
                stack.push(ans);
                stack.push(sign);
                ans = 0;
                sign = 1;
            }
            else if (s.charAt(i) == ')') {
                int prevSign = stack.pop();
                ans = prevSign * ans;
                int precAns = stack.pop();
                ans = precAns + ans;
            }
        }
        return ans;
    }
}