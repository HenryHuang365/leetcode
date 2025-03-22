import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        backtracking(nums, list, path);
        return list;
    }

    public void backtracking(int[] candidates, List<List<Integer>> list, List<Integer> path) {
        if (path.size() == candidates.length) {
            list.add(new ArrayList<>(path));
        } else {
            for (int i = 0; i < candidates.length; i++) {
                if (path.contains(candidates[i]))
                    continue;
                path.add(candidates[i]);
                backtracking(candidates, list, path);
                path.remove(path.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = new int[] { 1, 2, 3 };
        System.out.println("Output: " + solution.permute(nums));
    }
}