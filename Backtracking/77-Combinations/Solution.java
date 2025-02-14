import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<List<Integer>> combine(int n, int k) {
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = i + 1;
        }

        List<List<Integer>> list = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtracking(nums, list, path, k, 0);
        return list;
    }

    public void backtracking(int[] nums, List<List<Integer>> list, List<Integer> path, int k, int start) {
        if (path.size() == k) {
            list.add(new ArrayList<>(path));
        } else {
            for (int i = start; i < nums.length; i++) {
                path.add(nums[i]);
                backtracking(nums, list, path, k, i + 1);
                path.remove(path.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("Output: " + solution.combine(4, 2));
    }
}