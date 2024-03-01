
// Definition for a binary tree node.

import java.util.ArrayList;
import java.util.List;

class Solution {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> array = new ArrayList<>();
        postOrder(root, array);
        return array;
    }

    public void postOrder(TreeNode root, List<Integer>array) {
        if (root == null) {
            return;
        }

        postOrder(root.left, array);
        postOrder(root.right, array);
        array.add(root.val);
    }
}