import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int originalColor = image[sr][sc];
        Queue<int[]> nextToVisit = new LinkedList<>();
        HashSet<int[]> visited = new HashSet<>();

        nextToVisit.offer(new int[] {sr, sc});
        int[][] directions = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while (!nextToVisit.isEmpty()) {
            int[] cell = nextToVisit.poll();
            int newSr = cell[0];
            int newSc = cell[1];
            if (visited.contains(new int[] {newSr, newSc})) {
                continue;
            }
            visited.add(new int[] {newSr, newSc});
            image[newSr][newSc] = newColor;
            for (int[] dir : directions) {
                int adjSr = newSr + dir[0];
                int adjSc = newSc + dir[1];
                if (adjSr >= 0 && adjSr < image.length && adjSc >= 0 && adjSc < image.length) {
                    if (image[adjSr][adjSc] == originalColor && !visited.contains(new int[] {adjSr, adjSc})) {
                        nextToVisit.offer(new int[] {adjSr, adjSc});
                    }
                }
            }
            
        }
        return image;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        // int[][] image = new int[][] {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}};
        int[][] image = new int[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        int[][] newImage = solution.floodFill(image, 0, 0, 0);
        for (int[] row : newImage) {
            System.out.println("Output: " + Arrays.toString(row));
        }
    }
}
