// Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sum to target.

// Each number in candidates may only be used once in the combination.

// Note: The solution set must not contain duplicate combinations.

// Example 1:

// Input: candidates = [10,1,2,7,6,1,5], target = 8
// Output: 
// [
// [1,1,6],
// [1,2,5],
// [1,7],
// [2,6]
// ]
// Example 2:

// Input: candidates = [2,5,2,1,2], target = 5
// Output: 
// [
// [1,2,2],
// [5]
// ]

// Constraints:

// 1 <= candidates.length <= 100
// 1 <= candidates[i] <= 50
// 1 <= target <= 30
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtracking(candidates, list, path, target, 0, 0);

        return list;
    }

    public void backtracking(int[] candidates, List<List<Integer>> list, List<Integer> path, int target, int sum,
            int start) {
        if (sum > target) {
            return;
        } else if (sum == target) {
            list.add(new ArrayList<>(path));
        } else {
            for (int i = start; i < candidates.length; i++) {
                if (i > start && candidates[i] == candidates[i - 1])
                    continue;
                path.add(candidates[i]);
                backtracking(candidates, list, path, target, sum + candidates[i], i + 1);
                path.remove(path.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] candidates = new int[] { 10, 1, 2, 7, 6, 1, 5 };

        System.out.println("Output: " + solution.combinationSum2(candidates, 8));
    }
}