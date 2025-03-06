import java.util.*;

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

class Solution {
    int start = 1;

    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> bst = new ArrayList<>();
        if (start > n) {
            bst.add(null);
            return bst;
        }

        if (n == start) {
            bst.add(new TreeNode(start));
            return bst;
        }
        int localStart = start;
        for (int i = start; i <= n; i++) {
            List<TreeNode> left = generateTrees(i - 1);
            start = i + 1;
            List<TreeNode> right = generateTrees(n);
            start = localStart;
            for (TreeNode leftChild : left) {
                for (TreeNode rightChild : right) {
                    TreeNode node = new TreeNode(i, leftChild, rightChild);
                    bst.add(node);
                }
            }
        }
        return bst;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        List<TreeNode> res = solution.generateTrees(3);

        for (TreeNode r : res) {
            r.preorder();
            System.out.println("");
        }
    }
}