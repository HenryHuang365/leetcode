import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public char[][] updateBoardDFS(char[][] board, int[] click) {
        int row = click[0];
        int col = click[1];
        char clickOn = board[row][col];

        if (clickOn == 'M') {
            board[row][col] = 'X';
        } else {
            int count = 0;
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i == 0 && j == 0) continue;
                    int r = row + i;
                    int c = col + j;
                    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) continue;
                    if (board[r][c] == 'M' || board[r][c] == 'X') count++;
                }
            }

            if (count > 0) {
                board[row][col] = (char)(count + '0');
            } else {
                board[row][col] = 'B';
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (i == 0 && j == 0) continue;
                        int r = row + i;
                        int c = col + j;
                        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) continue;
                        if (board[r][c] == 'E') updateBoardDFS(board, new int[] {r, c});
                    }
                }
            }
        }
        return board;
    }

    public char[][] updateBoardBFS(char[][] board, int[] click) {
        Queue<int[]> nextToVisit = new LinkedList<>();
        nextToVisit.add(click);
        
        while (!nextToVisit.isEmpty()) {
            int[] cell = nextToVisit.poll();
            int row = cell[0];
            int col = cell[1];
            int clickOn = board[row][col];

            if (clickOn == 'M') {
                board[row][col] = 'X';
            } else {
                int count = 0;
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (i == 0 && j == 0) continue;
                        int r = row + i;
                        int c = col + j;
                        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) continue;
                        if (board[r][c] == 'M' || board[r][c] == 'X') count++;
                    }
                }

                if (count > 0) {
                    board[row][col] = (char)(count + '0');
                } else {
                    board[row][col] = 'B';
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            if (i == 0 && j == 0) continue;
                            int r = row + i;
                            int c = col + j;
                            if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) continue;
                            if (board[r][c] == 'E') {
                                nextToVisit.add(new int[] {r, c});
                                board[r][c] = 'B';
                            }
                        }
                    }
                }
            }
        }
        return board;
    }

    public static void main(String[] args) {
        char[][] solution = new Solution().updateBoardDFS(new char[][]{{'E','E','E','E','E'}, {'E','E','M','E','E'}, {'E','E','E','E','E'}, {'E','E','E','E','E'}}, new int[] {3, 0});
        char[][] solution1 = new Solution().updateBoardBFS(new char[][]{{'E','E','E','E','E'}, {'E','E','M','E','E'}, {'E','E','E','E','E'}, {'E','E','E','E','E'}}, new int[] {3, 0});
        for (char[] s : solution) {
            System.out.println("Output: " + new String(s));
        }

        for (char[] s : solution1) {
            System.out.println("Output: " + new String(s));
        }
    }
}