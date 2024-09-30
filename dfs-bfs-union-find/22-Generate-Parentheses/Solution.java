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
        List<String> res = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        backtracking(n, res, stack, 0, 0);
        return res;
    }

    public void backtracking(int n, List<String> res, Stack<Character> stack, int openN, int closeN) {
        if (openN == n && openN == closeN) {
            StringBuilder sb = new StringBuilder();
            for (Character c : stack) {
                sb.append(c);
            }
            res.add(sb.toString());
        }

        if (openN < n) {
            stack.add('(');
            backtracking(n, res, stack, openN + 1, closeN);
            stack.pop();
        }

        if (closeN < openN) {
            stack.add(')');
            backtracking(n, res, stack, openN, closeN + 1);
            stack.pop();
        }
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("Output: " + solution.generateParenthesis(3).toString());
    }
}
