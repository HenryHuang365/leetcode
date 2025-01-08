import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {
    public int[][] floodFillBFS(int[][] image, int sr, int sc, int color) {
        int originalColor = image[sr][sc];
        Queue<int[]> nextToVisit = new LinkedList<>();
        nextToVisit.offer(new int[] { sr, sc });

        int[][] directions = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        while (!nextToVisit.isEmpty()) {
            int[] cell = nextToVisit.poll();
            int row = cell[0];
            int col = cell[1];

            if (image[row][col] == color || image[row][col] != originalColor) {
                continue;
            }

            image[row][col] = color;

            for (int[] dir : directions) {
                int r = row + dir[0];
                int c = col + dir[1];

                if (r >= 0 && r < image.length && c >= 0 && c < image[0].length && image[r][c] == originalColor) {
                    nextToVisit.offer(new int[] { r, c });
                }
            }
        }
        return image;
    }

    public int[][] floodFillDFS(int[][] image, int sr, int sc, int color) {
        int originalColor = image[sr][sc];
        dfs(image, sr, sc, originalColor, color);
        return image;
    }

    public void dfs(int[][] image, int i, int j, int originalColor, int color) {
        if ((i < 0 || i >= image.length) ||
                (j < 0 || j >= image[0].length) ||
                (image[i][j] == color) ||
                (image[i][j] != originalColor)) {
            return;
        }

        image[i][j] = color;
        dfs(image, i + 1, j, originalColor, color);
        dfs(image, i - 1, j, originalColor, color);
        dfs(image, i, j + 1, originalColor, color);
        dfs(image, i, j - 1, originalColor, color);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] image = new int[][] { { 1, 1, 1 }, { 1, 1, 0 }, { 1, 0, 1 } };
        int[][] image2 = new int[][] { { 1, 1, 1 }, { 1, 1, 0 }, { 1, 0, 1 } };
        int[][] newImageBFS = solution.floodFillBFS(image, 1, 1, 2);
        int[][] newImageDFS = solution.floodFillDFS(image2, 1, 1, 2);
        System.out.println("BFS: ");
        for (int[] row : newImageBFS) {
            System.out.println("Output: " + Arrays.toString(row));
        }
        System.out.println("DFS: ");
        for (int[] row : newImageDFS) {
            System.out.println("Output: " + Arrays.toString(row));
        }
    }
}
