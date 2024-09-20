/**
 * Solution
 * 
 * Given a binary array nums, return the maximum number of consecutive 1's in
 * the array if you can flip at most one 0.
 * 
 * Example 1:
 * Input: nums = [1,0,1,1,0]
 * Output:
 * Explanation:
 * - If we flip the first zero, nums becomes [1,1,1,1,0] and we have 4
 * consecutive ones.
 * - If we flip the second zero, nums becomes [1,0,1,1,1] and we have 3
 * consecutive ones.
 * The max number of consecutive ones is 4.
 * 
 * Example 2:
 * Input: nums = [1,0,1,1,0,1]
 * Output: 4
 * Explanation:
 * - If we flip the first zero, nums becomes [1,1,1,1,0,1] and we have 4
 * consecutive ones.
 * - If we flip the second zero, nums becomes [1,0,1,1,1,1] and we have 4
 * consecutive ones.
 * The max number of consecutive ones is 4.
 */

class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int res = 0;
        int zero = 0;
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zero++;
            }
            while (zero > 1) {
                if (nums[left] == 0) {
                    zero--;
                }
                left++;
            }
            res = Math.max(res, right - left + 1);
        }
        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] input = new int[] { 1, 0, 1, 1, 0, 1 };
        System.out.println("Output: " + solution.findMaxConsecutiveOnes(input));
    }
}