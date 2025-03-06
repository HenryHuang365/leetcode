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
    public boolean isValidBST(TreeNode root) {
        if (root.left == null && root.right == null) {
            return true;
        }

        if (root.left != null && root.right != null) {
            return root.left.val < root.val && isValidBST(root.left) && root.right.val > root.val
                    && isValidBST(root.right);
        } else if (root.left != null) {
            return root.left.val < root.val && isValidBST(root.left);
        } else {
            return root.right.val > root.val && isValidBST(root.right);
        }
    }

    public static void main(String args[]) {
        // First Tree
        TreeNode twoLeftTwo = new TreeNode(3);
        TreeNode twoRightTwo = new TreeNode(6);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(4, twoLeftTwo, twoRightTwo);
        TreeNode root = new TreeNode(5, left, right);

        System.out.print("First tree: ");
        root.inorder();
        System.out.println("");

        Solution solution = new Solution();
        System.out.println("Output: " + solution.isValidBST(root));

        // Second Tree
        TreeNode secondLeft = new TreeNode(1);
        TreeNode secondRight = new TreeNode(3);
        TreeNode secondRoot = new TreeNode(2, secondLeft, secondRight);

        System.out.print("Second tree: ");
        secondRoot.inorder();
        System.out.println("");

        System.out.print("After invert: ");
        System.out.println("Output: " + solution.isValidBST(secondRoot));
    }
}