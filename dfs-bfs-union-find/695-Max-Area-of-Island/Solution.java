import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    int area = dfs(grid, i, j);
                    res = Math.max(res, area);
                }
            }
        }
        return res;
    }

    public int dfs(int[][] grid, int i, int j) {
        if (
            (i < 0 || i >= grid.length) ||
            (j < 0 || j >= grid[0].length) ||
            grid[i][j] == 0
        ) {
            return 0;
        }
        grid[i][j] = 0;
        int area = 1;
        area += dfs(grid, i + 1, j);
        area += dfs(grid, i - 1, j);
        area += dfs(grid, i, j + 1);
        area += dfs(grid, i, j - 1);

        return area;
    }

    public int maxAreaOfIslandBFS(int[][] grid) {
        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    int count = bfs(grid, i, j);
                    result = Math.max(result, count);
                }
            }
        }
        return result;
    }

    public int bfs(int[][] grid, int i, int j) {
        Queue<int[]> nextToVisit = new LinkedList<>();
        nextToVisit.offer(new int[] { i, j });
        int count = 0;

        int[][] directions = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!nextToVisit.isEmpty()) {
            int[] cell = nextToVisit.poll();
            int row = cell[0];
            int col = cell[1];

            if (grid[row][col] == 0) {
                continue;
            }

            grid[row][col] = 0;
            count++;

            for (int[] dir : directions) {
                int r = row + dir[0];
                int c = col + dir[1];
                if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && grid[r][c] == 1) {
                    nextToVisit.offer(new int[] { r, c });
                }
            }
        }

        return count;
    }

    public void increment() {
        int sum = helper(0);
        System.out.println(sum);
    }

    public int helper(int n) {
        if (n >= 5) {
            return 0;
        }
        int sum = 2;
        sum += helper(n + 1);
        return sum;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] input = new int[][] {
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 0, 0 },
                { 0, 0, 0, 1, 1 },
                { 0, 0, 0, 1, 1 },
        };

        int[][] input2 = new int[][] {
                { 1, 1, 0, 1, 1 },
                { 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1 },
                { 1, 1, 0, 1, 1 },
        };

        System.out.println("Output: " + solution.maxAreaOfIsland(input));
        System.out.println("Output: " + solution.maxAreaOfIsland(input2));
        solution.increment();
        int[][] input3 = new int[][] {
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 0, 0 },
                { 0, 0, 0, 1, 1 },
                { 0, 0, 0, 1, 1 },
        };

        int[][] input4 = new int[][] {
                { 1, 1, 0, 1, 1 },
                { 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1 },
                { 1, 1, 0, 1, 1 },
        };
        System.out.println("Output: " + solution.maxAreaOfIslandBFS(input3));
        System.out.println("Output: " + solution.maxAreaOfIslandBFS(input4));
    }
}