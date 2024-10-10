import java.util.*;

/**
 * Solution
 */
public class Solution {
    public int maxDistance(int[][] grid) {
        int N = grid.length;
        Queue<int[]> nextToVisited = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 1) {
                    nextToVisited.offer(new int[]{i, j});
                }
            }
        }

        if (nextToVisited.size() == N * N || nextToVisited.size() == 0) {
            return -1;
        }

        int[][] directions = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int res = -1; // The initial value of res will not affect the result

        while (!nextToVisited.isEmpty()) {
            int[] cell = nextToVisited.poll(); 
            int x = cell[0];
            int y = cell[1];
            res = grid[x][y];
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                if (newX >= 0 && newX < N && newY >= 0 && newY < N && grid[newX][newY] == 0) {
                    grid[newX][newY] = grid[x][y] + 1;
                    nextToVisited.offer(new int[]{newX, newY});
                }
            }
        }
        return res - 1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] grid = {{1, 0, 1}, {0, 0, 0}, {1, 0, 1}};
        int[][] grid2 = {{1, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        System.out.println("Output: " + solution.maxDistance(grid));
        System.out.println("Output: " + solution.maxDistance(grid2));
    }
}