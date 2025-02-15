import java.util.ArrayList;
import java.util.List;

class Solution {
    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    List<String> list = new ArrayList<>();
                    StringBuilder path = new StringBuilder();
                    Character letter = board[i][j];
                    path.append(letter);
                    board[i][j] = '0';
                    dfs(board, i, j, list, path, word);
                    System.out.println("list in loop: " + list.toString());
                    if (list.size() > 0)
                        return true;
                    board[i][j] = letter;
                }
            }
        }
        return false;
    }

    public void dfs(char[][] board, int i, int j, List<String> list, StringBuilder path, String word) {
        if (path.length() == word.length()) {
            if (path.toString().equals(word)) {
                list.add(path.toString());
            }
        } else {
            int[][] directions = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
            for (int[] direction : directions) {
                int r = i + direction[0];
                int c = j + direction[1];
                if ((r < 0 || r >= board.length) || (c < 0 || c >= board[0].length))
                    continue;
                if (board[r][c] == '0')
                    continue;
                Character letter = board[r][c];
                path.append(letter);
                board[r][c] = '0';
                dfs(board, r, c, list, path, word);
                path.delete(path.length() - 1, path.length());
                board[r][c] = letter;
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        char[][] board = new char[][] { { 'A', 'B', 'C', 'E' }, { 'S', 'F', 'C', 'S' }, { 'A', 'D', 'E', 'E' } };
        System.out.println("Output: " + solution.exist(board, "SEE"));
    }
}