import java.util.Set;
import java.util.HashSet;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> subSets = new HashSet<>();
        int l = 0;
        for (int r = 0; r < nums.length; r++) {
            if (Math.abs(l - r) > k) {
                subSets.remove(nums[l]);
                l++;
            }

            // Mistake: I used nums[l] == nums[r] to determine if we can return true.
            // Note that i, j can be any number in the window, while l and r are the start
            // and end of the window.
            if (subSets.contains(nums[r])) {
                return true;
            }

            subSets.add(nums[r]);
        }
        return false;
    }
}