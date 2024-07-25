class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int l = 0;
        int r = 0;
        int sum = 0;
        int minLen = Integer.MAX_VALUE;

        while (r < nums.length) {
            if (sum < target) {
                sum += nums[r];
                r++;
            }

            while (sum >= target) {
                minLen = Math.min(minLen, r - l);
                sum -= nums[l];
                l++;
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}