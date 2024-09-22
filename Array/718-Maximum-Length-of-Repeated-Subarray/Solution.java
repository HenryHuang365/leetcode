/**
 * Solution
 * 
 * Solution1 DP
 * 
 * Solution2 KMP
 * 
 * Solution3 rolling hash
 */
public class Solution {
    public int findLength(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int res = 0;
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                }
                res = Math.max(res, dp[i][j]);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums1 = new int[] { 1, 2, 3, 2, 1 };
        int[] nums2 = new int[] { 3, 2, 1, 4, 7 };
        System.out.println("Output: " + solution.findLength(nums1, nums2));
    }
}