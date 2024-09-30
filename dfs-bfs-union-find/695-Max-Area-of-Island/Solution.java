class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        int res = 0;
        int width = grid.length;
        int length = grid[0].length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
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
    }
}