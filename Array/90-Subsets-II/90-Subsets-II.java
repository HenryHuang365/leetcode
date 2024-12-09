// Given an integer array nums that may contain duplicates, return all possible 
// subsets
//  (the power set).

// The solution set must not contain duplicate subsets. Return the solution in any order.

// Example 1:

// Input: nums = [1,2,2]
// Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
// Example 2:

// Input: nums = [0]
// Output: [[],[0]]

// Constraints:

// 1 <= nums.length <= 10
// -10 <= nums[i] <= 10

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }

    public void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, int start) {
        // if (list.contains(new ArrayList<>(tempList))) return; // This way works, but not good for performance
        // checking the duplicates take O(n), and it is filtering not preventing. This means I am doing extra work, but I am just simply filtering out the expected results.
        list.add(new ArrayList<>(tempList));
        
        for (int i = start; i < nums.length; i++) {
            // This is a better way to check duplicates as it is preventing from doing redudant work and comparison is simple
            // skips the duplicates
            if (i > start && nums[i] == nums[i - 1])
                continue;
            tempList.add(nums[i]);
            backtrack(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums = new int[] {1, 2, 1};

        System.out.println("output: " + solution.subsetsWithDup(nums));
    }
}
