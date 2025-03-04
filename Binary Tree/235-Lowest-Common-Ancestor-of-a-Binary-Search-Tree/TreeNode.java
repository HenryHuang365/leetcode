public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public void insert(int value) {
        if (value <= val) {
            if (left == null) {
                TreeNode node = new TreeNode(value);
                left = node;
            } else {
                left.insert(value);
            }
        } else {
            if (right == null) {
                TreeNode node = new TreeNode(value);
                right = node;
            } else {
                right.insert(value);
            }
        }
    }

    public void inorder() {
        System.out.print(val + " ");

        if (left != null) {
            left.inorder();
        }

        if (right != null) {
            right.inorder();
        }
    }
}

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        } else if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        } else {
            return root;
        }
    }

    public static void main(String args[]) {
        // First Tree
        TreeNode threeLeftOne = new TreeNode(3);
        TreeNode threeRightOne = new TreeNode(5);
        TreeNode twoLeftTwo = new TreeNode(7);
        TreeNode twoRightTwo = new TreeNode(9);
        TreeNode twoLeftOne = new TreeNode(0);
        TreeNode twoRightOne = new TreeNode(4, threeLeftOne, threeRightOne);
        TreeNode left = new TreeNode(2, twoLeftOne, twoRightOne);
        TreeNode right = new TreeNode(8, twoLeftTwo, twoRightTwo);
        TreeNode root = new TreeNode(6, left, right);

        System.out.print("Input: ");
        root.inorder();
        System.out.println("");

        Solution solution = new Solution();
        TreeNode res = solution.lowestCommonAncestor(root, threeLeftOne, threeRightOne);
        System.out.print("Output: " + res.val);
    }
}