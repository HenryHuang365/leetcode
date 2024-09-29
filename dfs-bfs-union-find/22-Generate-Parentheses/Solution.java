/*
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 * Example 1:
 * Input: n = 3
 * Output: ["((()))","(()())","(())()","()(())","()()()"]
 * Example 2:
 * Input: n = 1
 * Output: ["()"]
 * Constraints:
 * 1 <= n <= 8
 * 
 * This question does not explictly state it uses DFS, but it is using the same concept.
*/
import java.util.*;
class Solution {
    public List<String> generateParenthesis(int n) {
        Stack<Character> stack = new Stack<>();
        List<String> res = new ArrayList<>();
        backtracking(n, 0, 0, res, stack);
        return res;
    }

    public void backtracking(int n, int openN, int closeN, List<String> res, Stack<Character> stack) {
        if (openN == n && openN == closeN) {
            StringBuilder sb = new StringBuilder();
            for (char c : stack) {
                sb.append(c);
            }
            res.add(sb.toString());
            return;
        }

        if (openN < n) {
            stack.add('(');
            backtracking(n, openN + 1, closeN, res, stack);
            stack.pop();
        }

        if (closeN < openN) {
            stack.add(')');
            backtracking(n, openN, closeN + 1, res, stack);
            stack.pop();
        }
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("Output: " + solution.generateParenthesis(3).toString());
    }
}
