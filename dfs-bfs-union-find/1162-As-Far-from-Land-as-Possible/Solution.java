import java.util.*;

/**
 * Solution
 */
public class Solution {
    public int maxDistance(int[][] grid) {
        int n = grid.length;
        Queue<int[]> nextToVisit = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    nextToVisit.offer(new int[] { i, j });
                }
            }
        }

        if (nextToVisit.size() == 0 || nextToVisit.size() == n * n) {
            return -1;
        }

        int result = 0;
        int[][] directions = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        while (!nextToVisit.isEmpty()) {
            int[] cell = nextToVisit.poll();
            int row = cell[0];
            int col = cell[1];

            result = Math.max(result, grid[row][col]);

            for (int[] dir : directions) {
                int r = row + dir[0];
                int c = col + dir[1];
                if (r >= 0 && r < n && c >= 0 && c < n && grid[r][c] == 0) {
                    grid[r][c] = grid[row][col] + 1;
                    nextToVisit.offer(new int[] { r, c });
                }
            }
        }
        return result - 1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] grid = { { 1, 0, 1 }, { 0, 0, 0 }, { 1, 0, 1 } };
        int[][] grid2 = { { 1, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
        System.out.println("Output: " + solution.maxDistance(grid));
        System.out.println("Output: " + solution.maxDistance(grid2));
    }
}