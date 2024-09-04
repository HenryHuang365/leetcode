class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return new int[] {};
    }
}

// Learning points:
// 1. Java initialise array of intergers:
// myArray = new int[]{1, 2, 3, 4}
// int[] myArray = {1, 2 ,3, 4}
// int myArray[] = {1, 2, 3, 4}
// int myArray = new int[4]