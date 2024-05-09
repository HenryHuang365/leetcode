
// Definition for a binary tree node.

import java.util.List;
import java.util.ArrayList;
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

    public void inorder(TreeNode node, List<Integer> array) {
        if (node == null) {
            return;
        }

        inorder(node.left, array);
        array.add(node.val);
        inorder(node.right, array);
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }
}