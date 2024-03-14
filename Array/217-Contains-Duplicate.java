class Solution {
    public boolean containsDuplicate(int[] nums) {
        int len1 = nums.length;
        for (int i = 0; i < len1 - 1; i++) {
            if (nums[i] == nums[i+1]) {
                return true;
            }
        }
            
        return false;
    }
}