import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> generateParenthesis(int n) {
        String[] parentheses = new String[2 * n];
        for (int i = 0; i < n; i++) {
            parentheses[i] = "(";
        }
        for (int i = n; i < 2 * n; i++) {
            parentheses[i] = ")";
        }

        List<String> list = new ArrayList<>();
        StringBuilder path = new StringBuilder();
        boolean[] used = new boolean[2 * n];
        backtracking(parentheses, list, path, used, 0, 0);

        return list;
    }

    public void backtracking(String[] parentheses, List<String> list, StringBuilder path, boolean[] used, int open,
            int close) {
        if (path.length() == parentheses.length) {
            list.add(path.toString());
        } else {
            for (int i = 0; i < parentheses.length; i++) {
                if (used[i] || i > 0 && parentheses[i] == parentheses[i - 1] && !used[i - 1] || (close > open))
                    continue;
                int newOpen = open;
                int newClose = close;
                if (parentheses[i].equals("(")) {
                    newOpen = open + 1;
                } else {
                    newClose = close + 1;
                }
                path.append(parentheses[i]);
                used[i] = true;
                backtracking(parentheses, list, path, used, newOpen, newClose);
                path.delete(path.length() - 1, path.length());
                used[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("Output: " + solution.generateParenthesis(3).toString());
    }
}