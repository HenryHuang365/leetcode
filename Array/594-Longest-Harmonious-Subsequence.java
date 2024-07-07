import java.util.HashMap;

class Solution {
    public int findLHS(int[] nums) {
        int subLength = 0;
        HashMap<Integer, Integer> count = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
        }

        System.out.println("This is count: " + count);

        for (int key : count.keySet()) {
            if (count.containsKey(key + 1)) {
                int length = count.get(key) + count.get(key + 1);
                subLength = Math.max(subLength, length);
            }
        }

        return subLength;
    }
}