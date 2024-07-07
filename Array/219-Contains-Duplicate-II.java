import java.util.Set;
import java.util.HashSet;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> windowSet = new HashSet<>();
        int l = 0;

        for (int r = 0; r < nums.length; r++) {
            if (Math.abs(l - r) > k) {
                windowSet.remove(nums[l]);
                l++;
            }
            if (windowSet.contains(nums[r])) {
                return true;
            }

            windowSet.add(nums[r]);
        }
        return false;
    }
}