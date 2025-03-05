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
        if (root == null) {
            return null;
        }
        if (p.val == root.val) {
            return p;
        } else if (q.val == root.val) {
            return q;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) {
            return root;
        } else if (left != null) {
            return left;
        } else {
            return right;
        }
    }

    public static void main(String args[]) {
        // First Tree
        TreeNode threeLeftOne = new TreeNode(7);
        TreeNode threeRightOne = new TreeNode(4);
        TreeNode twoLeftTwo = new TreeNode(0);
        TreeNode twoRightTwo = new TreeNode(8);
        TreeNode twoLeftOne = new TreeNode(6);
        TreeNode twoRightOne = new TreeNode(2, threeLeftOne, threeRightOne);
        TreeNode left = new TreeNode(5, twoLeftOne, twoRightOne);
        TreeNode right = new TreeNode(1, twoLeftTwo, twoRightTwo);
        TreeNode root = new TreeNode(3, left, right);

        System.out.print("Input: ");
        root.inorder();
        System.out.println("");

        Solution solution = new Solution();
        TreeNode res = solution.lowestCommonAncestor(root, left, threeRightOne);
        System.out.print("Output: " + res.val);
    }
}