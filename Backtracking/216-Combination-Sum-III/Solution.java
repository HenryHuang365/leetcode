import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        int[] nums = new int[9];
        int max = 0;
        for (int i = 0; i < 9; i++) {
            nums[i] = i + 1;
            if (i < k) {
                max += i + 1;
            }
        }
        System.out.println("nums: " + Arrays.toString(nums));
        System.out.println("Max: " + max);
        if (n < max)
            return new ArrayList<>();

        List<List<Integer>> list = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtracking(nums, list, path, 0, 0, k, n);
        return list;
    }

    public void backtracking(int[] nums, List<List<Integer>> list, List<Integer> path,
            int sum, int start, int k, int target) {
        if (sum > target) {
            return;
        } else if (sum == target && path.size() == k) {
            list.add(new ArrayList<>(path));
        } else {
            for (int i = start; i < nums.length; i++) {
                path.add(nums[i]);
                backtracking(nums, list, path, sum + nums[i], i + 1, k, target);
                path.remove(path.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("Output: " + solution.combinationSum3(3, 7).toString());
    }
}