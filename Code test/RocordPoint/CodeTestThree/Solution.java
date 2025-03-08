import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public static String ArrayChallenge(int[] arr) {
        int totalSum = Arrays.stream(arr).sum();

        // If the total sum is odd, it's impossible to split into two equal parts
        if (totalSum % 2 != 0) {
            return "-1";
        }

        int targetSum = totalSum / 2;

        // Use dynamic programming to check if a subset with sum equal to targetSum exists
        boolean[][] dp = new boolean[arr.length + 1][targetSum + 1];
        for (int i = 0; i <= arr.length; i++) {
            dp[i][0] = true; // Subset with sum 0 is always possible
        }

        for (int i = 1; i <= arr.length; i++) {
            for (int j = 1; j <= targetSum; j++) {
                dp[i][j] = dp[i - 1][j]; // If not including arr[i - 1]
                if (j >= arr[i - 1]) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - arr[i - 1]]; // Including arr[i - 1]
                }
            }
        }

        // If no subset with sum equal to targetSum is found, return -1
        if (!dp[arr.length][targetSum]) {
            return "-1";
        }

        // Backtrack to find the elements in one subset
        List<Integer> set1 = new ArrayList<>();
        List<Integer> set2 = new ArrayList<>();

        int w = targetSum;
        for (int i = arr.length; i > 0 && w > 0; i--) {
            if (!dp[i - 1][w]) {
                set1.add(arr[i - 1]); // This element is in the first set
                w -= arr[i - 1];
            } else {
                set2.add(arr[i - 1]); // This element is in the second set
            }
        }

        // Sort both sets to meet the requirement
        Integer[] sortedSet1 = set1.toArray(new Integer[0]);
        Integer[] sortedSet2 = set2.toArray(new Integer[0]);
        Arrays.sort(sortedSet1);
        Arrays.sort(sortedSet2);

        // Determine which set goes first by comparing the smallest first element
        StringBuilder result = new StringBuilder();
        if (sortedSet1[0] < sortedSet2[0]) {
            result.append(Arrays.toString(sortedSet1).replaceAll("[\\[\\] ]", ""))
                  .append(",")
                  .append(Arrays.toString(sortedSet2).replaceAll("[\\[\\] ]", ""));
        } else {
            result.append(Arrays.toString(sortedSet2).replaceAll("[\\[\\] ]", ""))
                  .append(",")
                  .append(Arrays.toString(sortedSet1).replaceAll("[\\[\\] ]", ""));
        }

        // Replace characters that are in the ChallengeToken with the format --[CHAR]--
        return replaceWithChallengeToken(result.toString(), "7vq3d0ubc5e");
    }

    // Method to replace characters from ChallengeToken with --[CHAR]--
    private static String replaceWithChallengeToken(String result, String token) {
        StringBuilder finalOutput = new StringBuilder();
        for (char ch : result.toCharArray()) {
            if (token.indexOf(ch) != -1) {
                finalOutput.append("--").append(ch).append("--");
            } else {
                finalOutput.append(ch);
            }
        }
        return finalOutput.toString();
    }

    public static void main(String[] args) {
        // Test case
        int[] arr = {16, 22, 35, 8, 20, 1, 21, 11};
        System.out.println(ArrayChallenge(arr));  // Expected output: 1,11,20,35,8,16,21,22
    }
}
