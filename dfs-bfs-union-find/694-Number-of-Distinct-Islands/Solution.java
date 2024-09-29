import java.util.HashSet;

/**
 * Solution
 */
public class Solution {
    public int numDistinctIslands(int[][] grid) {
        int width = grid.length;
        int length = grid[0].length;
        HashSet<String> paths = new HashSet<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (grid[i][j] == 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    dfs(grid, i, j, sb, "");
                    paths.add(sb.toString());
                }
            }
        }
        return paths.size();
    }

    public void dfs(int[][] grid, int i, int j, StringBuilder sb, String s) {
        if ((i < 0 || i >= grid.length) ||
                (j < 0 || j >= grid[0].length) ||
                grid[i][j] == 0) {
            return;
        }

        grid[i][j] = 0;
        sb.append(s);
        dfs(grid, i + 1, j, sb, "u");
        dfs(grid, i - 1, j, sb, "d");
        dfs(grid, i, j + 1, sb, "r");
        dfs(grid, i, j - 1, sb, "l");
        sb.append("b");
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

        System.out.println("Output: " + solution.numDistinctIslands(input));
        System.out.println("Output: " + solution.numDistinctIslands(input2));
    }
}