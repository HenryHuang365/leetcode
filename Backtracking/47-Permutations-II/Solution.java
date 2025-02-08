import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];

        backtracking(nums, list, path, used);
        return list;
    }

    public void backtracking(int[] nums, List<List<Integer>> list, List<Integer> path, boolean[] used) {
        if (path.size() == nums.length) {
            // if (!list.contains(new ArrayList<>(path))) {
            // list.add(new ArrayList<>(path));
            // } // This is a way to avoid duplicates but takes more time
            list.add(new ArrayList<>(path));
        } else {
            for (int i = 0; i < nums.length; i++) {
                if (used[i])
                    continue;
                if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])
                    continue;
                path.add(nums[i]);
                used[i] = true;
                backtracking(nums, list, path, used);
                path.remove(path.size() - 1);
                used[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = new int[] { 1, 1, 2 };
        System.out.println("Output: " + solution.permuteUnique(nums));
    }
}