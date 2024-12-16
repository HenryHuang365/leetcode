import java.util.LinkedList;
import java.util.Queue;

/**
 * Solution
 * 
 * Note:
 * 1. This is a dfs question.
 * Each first '1' in the grid is the beginnig of an island. We mark all the
 * adjancent '1's using dfs to connect the entire island.
 * Starting a direction, update all the adjacent '1' to '0' through this path.
 * 
 * 2. Since this is a 2-D grid, so we mark the visited '1' as '0'.
 * Normally, we add the coordinate/index into a set called visited. In this
 * case, making the visited '1's as '0' is another way to mark them as visted.
 */
public class Solution {
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    // dfs(grid, i, j);
                    bfs(grid, i, j);
                    count++;
                }
            }
        }

        return count;
    }

    public void dfs(char[][] grid, int i, int j) {
        if (
            (i < 0 || i >= grid.length) ||
            (j < 0 || j >= grid[0].length) ||
            (grid[i][j] == '0')
        ) {
            return;
        }
        grid[i][j] = '0';
        dfs(grid, i + 1, j);
        dfs(grid, i - 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
    }

    public void bfs(char[][] grid, int i, int j) {
        Queue<int[]> nextToVisit = new LinkedList<>();
        nextToVisit.offer(new int[] {i, j});

        while (!nextToVisit.isEmpty()) {
            int[] cell = nextToVisit.poll();
            int row = cell[0];
            int col = cell[1];
            
            if (grid[row][col] == '0') {
                continue;
            }

            grid[row][col] = '0';
            int[][] directions = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            for (int[] dir : directions) {
                int r = row + dir[0];
                int c = col + dir[1];
                if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && grid[r][c] != '0') {
                    nextToVisit.offer(new int[] {r, c});
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        char[][] input = new char[][] {
                { '1', '1', '1', '1', '0' },
                { '1', '1', '0', '1', '0' },
                { '1', '1', '0', '0', '0' },
                { '0', '0', '0', '0', '0' },
        };

        char[][] input2 = new char[][] {
                { '1', '1', '0', '0', '0' },
                { '1', '1', '0', '0', '0' },
                { '0', '0', '1', '0', '0' },
                { '0', '0', '0', '1', '1' },
        };
        System.out.println("output: " + solution.numIslands(input));
        System.out.println("output: " + solution.numIslands(input2));
    }
}