import java.util.HashMap;

class Solution {
    public int findLHS(int[] nums) {
        HashMap<Integer, Integer> count = new HashMap<>();
        int maxSubstring = 0;

        for (int i = 0; i < nums.length; i++) {
            count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
        }

        for (int key : count.keySet()) {
            if (count.containsKey(key + 1)) {
                int length = count.get(key) + count.get(key + 1);
                maxSubstring = Math.max(maxSubstring, length);
            }
        }

        return maxSubstring;
    }
}