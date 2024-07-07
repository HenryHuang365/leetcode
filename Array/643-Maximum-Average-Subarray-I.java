class Solution {
    public double findMaxAverage(int[] nums, int k) {
        int[] subArray = new int[k];
        double maxAverage = 0;
        int sum = 0;
        for (int i = 0; i < k; i++) {
            subArray[i] = nums[i];
            sum += nums[i];
        }

        maxAverage = (double) sum / k;

        int l = 1;
        int r = k;
        while (r < nums.length) {
            sum = sum - nums[l - 1] + nums[r];
            maxAverage = Math.max(maxAverage, (double) sum / k);
            l++;
            r++;
        }

        return maxAverage;
    }
}