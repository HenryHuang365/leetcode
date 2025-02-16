import java.util.List;
import java.util.ArrayList;

class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> list = new ArrayList<>();
        StringBuilder path = new StringBuilder();

        backtracking(root, list, path);
        return list;
    }

    public void backtracking(TreeNode root, List<String> list, StringBuilder path) {
        int lengthBefore = path.length();

        if (root.left == null && root.right == null) {
            path.append(root.val);
            list.add(path.toString());
        } else {
            path.append(root.val + "->");
        }

        if (root.left != null) {
            backtracking(root.left, list, path);
        }

        if (root.right != null) {
            backtracking(root.right, list, path);
        }

        path.setLength(lengthBefore);
    }

    public static void main(String[] args) {
        TreeNode twoRightNode2 = new TreeNode(5, null, null);
        TreeNode oneLeftNode1 = new TreeNode(2, null, twoRightNode2);
        TreeNode oneRightNode2 = new TreeNode(3, null, null);
        TreeNode root = new TreeNode(1, oneLeftNode1, oneRightNode2);
        root.preOrder();
        System.out.println("");

        Solution solution = new Solution();
        System.out.println("Output: " + solution.binaryTreePaths(root).toString());
    }
}

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public void preOrder() {
        System.out.print(val + " ");

        if (left != null) {
            left.preOrder();
        }

        if (right != null) {
            right.preOrder();
        }
    }
}