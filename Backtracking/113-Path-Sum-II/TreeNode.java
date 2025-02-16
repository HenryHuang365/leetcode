import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if (root == null)
            return new ArrayList<>();
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        backtracking(root, list, path, 0, targetSum);
        return list;
    }

    public void backtracking(TreeNode root, List<List<Integer>> list, List<Integer> path, int sum, int targetSum) {
        path.add(root.val);
        if (sum + root.val == targetSum) {
            if (root.left == null && root.right == null) {
                list.add(new ArrayList<>(path));
            }
        }

        if (root.left != null) {
            backtracking(root.left, list, path, sum + root.val, targetSum);
        }
        if (root.right != null) {
            backtracking(root.right, list, path, sum + root.val, targetSum);
        }

        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {
        TreeNode threeRightNode4 = new TreeNode(1);
        TreeNode threeLeftNode3 = new TreeNode(5);
        TreeNode threeRightNode2 = new TreeNode(2);
        TreeNode threeLeftNode1 = new TreeNode(7);
        TreeNode twoRightNode4 = new TreeNode(4, threeLeftNode3, threeRightNode4);
        TreeNode twoLeftNode1 = new TreeNode(11, threeLeftNode1, threeRightNode2);
        TreeNode twoLeftNode3 = new TreeNode(13);
        TreeNode oneLeftNode1 = new TreeNode(4, twoLeftNode1, null);
        TreeNode oneRightNode2 = new TreeNode(8, twoLeftNode3, twoRightNode4);
        TreeNode root = new TreeNode(5, oneLeftNode1, oneRightNode2);

        root.preorder();
        System.out.println();

        Solution solution = new Solution();
        System.out.println("Output: " + solution.pathSum(root, 22));
    }
}

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    // Two constructors
    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public void preorder() {
        System.out.print(val + " ");

        if (left != null) {
            left.preorder();
        }

        if (right != null) {
            right.preorder();
        }
    }
}
