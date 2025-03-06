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
    public List<TreeNode> generateTrees(int n) {
        return insert(1, n);
    }

    public List<TreeNode> insert(int start, int end) {
        List<TreeNode> bst = new ArrayList<>();
        if (start > end) {
            bst.add(null);
            return bst;
        }

        for (int i = start; i <= end; i++) {
            List<TreeNode> left = insert(start, i - 1);
            List<TreeNode> right = insert(i + 1, end);
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
        List<TreeNode> res = solution.generateTrees(4);

        for (TreeNode r : res) {
            r.preorder();
            System.out.println("");
        }
    }
}