import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        Arrays.sort(nums);

        backtracking(nums, list, path, 0);
        return list;
    }

    public void backtracking(int[] nums, List<List<Integer>> list, List<Integer> path, int start) {
        list.add(new ArrayList<>(path));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            path.add(nums[i]);
            backtracking(nums, list, path, i + 1);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = new int[] { 1, 2, 2 };
        System.out.println("Output: " + solution.subsetsWithDup(nums));
    }
}