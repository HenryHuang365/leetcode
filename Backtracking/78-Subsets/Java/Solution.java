import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        backtracking(nums, list, path, 0);
        return list;
    }

    public void backtracking(int[] nums, List<List<Integer>> list, List<Integer> path, int start) {
        list.add(new ArrayList<>(path));
        for (int i = start; i < nums.length; i++) {
            path.add(nums[i]);
            backtracking(nums, list, path, i + 1);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = new int[] { 1, 2, 3 };
        System.out.println("Output: " + solution.subsets(nums));
    }
}