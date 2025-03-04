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
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode temp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(temp);
        return root;
    }

    public static void main(String args[]) {
        // First Tree
        TreeNode twoLeftTwo = new TreeNode(6);
        TreeNode twoRightTwo = new TreeNode(9);
        TreeNode twoLeftOne = new TreeNode(1);
        TreeNode twoRightOne = new TreeNode(3);
        TreeNode left = new TreeNode(2, twoLeftOne, twoRightOne);
        TreeNode right = new TreeNode(7, twoLeftTwo, twoRightTwo);
        TreeNode root = new TreeNode(4, left, right);

        System.out.print("Before invert: ");
        root.inorder();
        System.out.println("");

        System.out.print("After invert: ");
        Solution solution = new Solution();
        solution.invertTree(root);
        root.inorder();
        System.out.println("");

        // Second Tree
        TreeNode secondLeft = new TreeNode(1);
        TreeNode secondRight = new TreeNode(3);
        TreeNode secondRoot = new TreeNode(2, secondLeft, secondRight);

        System.out.print("Before invert: ");
        secondRoot.inorder();
        System.out.println("");

        System.out.print("After invert: ");
        solution.invertTree(secondRoot);
        secondRoot.inorder();
    }
}