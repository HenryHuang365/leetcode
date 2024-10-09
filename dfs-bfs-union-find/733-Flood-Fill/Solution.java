import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int originalColor = image[sr][sc];
        if (originalColor == color) {
            return image;
        }
        Queue<int[]> nextToVisit = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();

        nextToVisit.offer(new int[] { sr, sc });
        int[][] directions = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
        while (!nextToVisit.isEmpty()) {
            int[] cell = nextToVisit.poll();
            int newSr = cell[0];
            int newSc = cell[1];
            String cellString = newSr + "," + newSc;
            if (visited.contains(cellString)) {
                continue;
            }
            visited.add(cellString);
            image[newSr][newSc] = color;
            for (int[] dir : directions) {
                int adjSr = newSr + dir[0];
                int adjSc = newSc + dir[1];
                if (adjSr >= 0 && adjSr < image.length && adjSc >= 0 && adjSc < image[0].length) {
                    if (image[adjSr][adjSc] == originalColor && !visited.contains(adjSr + "," + adjSc)) {
                        nextToVisit.offer(new int[] { adjSr, adjSc });
                    }
                }
            }
        }
        return image;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] image = new int[][] { { 1, 1, 1 }, { 1, 1, 0 }, { 1, 0, 1 } };
        // int[][] image = new int[][] { { 0, 0, 0 }, { 0, 0, 0 } };
        int[][] newImage = solution.floodFill(image, 1, 1, 2);
        for (int[] row : newImage) {
            System.out.println("Output: " + Arrays.toString(row));
        }
    }
}
