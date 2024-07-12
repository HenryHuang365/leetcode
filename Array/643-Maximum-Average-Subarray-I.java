class Solution {
    public double findMaxAverage(int[] nums, int k) {
        int maxAverage = 0;

        for (int i = 0; i < k; i++) {
            maxAverage += nums[i];
        }

        int sum = maxAverage;
        System.out.println("This is the sum: " + sum);

        for (int i = k; i < nums.length; i++) {
            sum = sum + nums[i] - nums[i - k];
            maxAverage = Math.max(maxAverage, sum);
        }

        return (double) maxAverage / k;
    }
}