import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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

    public boolean containsNearbyDuplicateTwo(int[] nums, int k) {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {

            if (!map.containsKey(nums[i])) {
                map.put(nums[i], new ArrayList<>());
            }
            map.get(nums[i]).add(i);
        }

        for (int key : map.keySet()) {
            List<Integer> arr = map.get(key);

            if (arr.size() > 1) {
                for (int i = 0; i < arr.size() - 1; i++) {
                    if (Math.abs(arr.get(i) - arr.get(i + 1)) <= k) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums = new int[] {1,2,3,1,2,3};
        int k = 2;

        System.out.println("output: " + solution.containsNearbyDuplicateTwo(nums, k));
    }
}