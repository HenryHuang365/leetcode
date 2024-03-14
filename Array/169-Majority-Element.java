import java.util.Arrays;

class Solution {
    public int majorityElement(int[] nums) {
        int mid = Math.round(nums.length / 2);
        Arrays.sort(nums);
        return nums[mid];
    }
}