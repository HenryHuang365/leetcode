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

    public int minSubArrayLenMethodTwo(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int length = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum >= target && left <= right) {
                length = Math.min(length, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return length == Integer.MAX_VALUE ? 0 : length;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int target = 4;
        int[] nums = new int[] {1, 4, 4};

        System.out.println("output: " + solution.minSubArrayLenMethodTwo(target, nums));
    }
}