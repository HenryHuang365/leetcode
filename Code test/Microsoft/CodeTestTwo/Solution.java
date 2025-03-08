import java.util.*;

public class Solution {

    public int solution(String[] A) {
        int N = A.length;
        int M = A[0].length();

        // Handle special case: if there is only one row
        if (N == 1) {
            String[] zeros = A[0].split("1");

            // Convert each segment into its length
            int[] lengths = new int[zeros.length];
            for (int i = 0; i < zeros.length; i++) {
                lengths[i] = zeros[i].length();
            }

            // Sort the lengths in descending order
            Arrays.sort(lengths);

            // Find the two longest segments
            int largest = lengths.length >= 1 ? lengths[lengths.length - 1] : 0;
            int secondLargest = lengths.length >= 2 ? lengths[lengths.length - 2] : 0;

            // Return the sum of the two longest segments
            return largest + secondLargest;
        }     

        int[][] dp = new int[N][M];  // DP array to store the maximum width of '0's up to each cell

        // Step 1: Fill the DP table with the maximum width of '0's at each cell
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (A[i].charAt(j) == '0') {
                    dp[i][j] = (j == 0) ? 1 : dp[i][j - 1] + 1;  // Number of continuous '0's to the left
                }
            }
        }

        // Step 2: Calculate maximum rectangular areas using heights of continuous '0's
        List<int[]> rectangles = new ArrayList<>();  // Store each rectangle as {top-left-row, top-left-col, bottom-right-row, bottom-right-col}

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (dp[i][j] > 0) {
                    int width = dp[i][j];
                    for (int k = i; k >= 0; k--) {
                        width = Math.min(width, dp[k][j]);  // Shrink the width as we go up
                        if (width == 0) break;                        
                        rectangles.add(new int[]{k, j - width + 1, i, j});
                    }
                }
            }
        }

        // Step 3: Calculate maximum area for two non-overlapping rectangles
        int maxTwoRectanglesArea = 0;
        for (int i = 0; i < rectangles.size(); i++) {
            int[] rect1 = rectangles.get(i);
            int area1 = getArea(rect1);

            for (int j = i + 1; j < rectangles.size(); j++) {
                int[] rect2 = rectangles.get(j);
                if (!overlap(rect1, rect2)) {
                    int area2 = getArea(rect2);
                    maxTwoRectanglesArea = Math.max(maxTwoRectanglesArea, area1 + area2);
                }
            }
        }

        return maxTwoRectanglesArea;
    }

    // Helper function to calculate area of a rectangle
    private int getArea(int[] rect) {
        int topLeftRow = rect[0];
        int topLeftCol = rect[1];
        int bottomRightRow = rect[2];
        int bottomRightCol = rect[3];
        return (bottomRightRow - topLeftRow + 1) * (bottomRightCol - topLeftCol + 1);
    }

    // Helper function to check if two rectangles overlap
    private boolean overlap(int[] rect1, int[] rect2) {
        int topLeftRow1 = rect1[0];
        int topLeftCol1 = rect1[1];
        int bottomRightRow1 = rect1[2];
        int bottomRightCol1 = rect1[3];

        int topLeftRow2 = rect2[0];
        int topLeftCol2 = rect2[1];
        int bottomRightRow2 = rect2[2];
        int bottomRightCol2 = rect2[3];

        // Check if the rectangles overlap
        return !(topLeftRow1 > bottomRightRow2 || bottomRightRow1 < topLeftRow2 || topLeftCol1 > bottomRightCol2 || bottomRightCol1 < topLeftCol2);
    }

    // Main function to test the examples
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test case 1: A = ["0100", "0000", "0001", "1011", "1000", "1100"]
        String[] testCase1 = {"0100", "0000", "0001", "1011", "1000", "1100"};
        System.out.println("Test case 1 result: " + solution.solution(testCase1));  // Expected: 10

        // Test case 2: A = ["1000", "1000"]
        String[] testCase2 = {"1000", "1000"};
        System.out.println("Test case 2 result: " + solution.solution(testCase2));  // Expected: 6

        // Test case 3: A = ["01000", "00000", "00000", "00010"]
        String[] testCase3 = {"00000000"};
        System.out.println("Test case 3 result: " + solution.solution(testCase3));  // Expected: 15
    }
}
