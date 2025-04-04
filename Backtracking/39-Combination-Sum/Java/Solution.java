// Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.

// The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the 
// frequency
//  of at least one of the chosen numbers is different.

// The test cases are generated such that the number of unique combinations that sum up to target is less than 150 combinations for the given input.

// Example 1:

// Input: candidates = [2,3,6,7], target = 7
// Output: [[2,2,3],[7]]
// Explanation:
// 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
// 7 is a candidate, and 7 = 7.
// These are the only two combinations.
// Example 2:

// Input: candidates = [2,3,5], target = 8
// Output: [[2,2,2,2],[2,3,3],[3,5]]
// Example 3:

// Input: candidates = [2], target = 1
// Output: []

// Constraints:

// 1 <= candidates.length <= 30
// 2 <= candidates[i] <= 40
// All elements of candidates are distinct.
// 1 <= target <= 40

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        backtracking(candidates, list, path, 0, target, 0);
        return list;
    }

    public void backtracking(int[] candidates, List<List<Integer>> list,
            List<Integer> path, int sum, int target, int start) {
        if (sum > target) {
            return;
        } else if (sum == target) {
            list.add(new ArrayList<>(path));
        } else {
            for (int i = start; i < candidates.length; i++) {
                path.add(candidates[i]);
                backtracking(candidates, list, path, sum + candidates[i], target, i);
                path.remove(path.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] candidates = new int[] { 2, 3, 6, 7 };
        int target = 7;

        System.out.println("output: " + solution.combinationSum(candidates, target));
        System.out.println("output: " + solution.combinationSum(new int[] { 2, 3, 5 }, 8));
        System.out.println("output: " + solution.combinationSum(new int[] { 2 }, 1));
    }
}