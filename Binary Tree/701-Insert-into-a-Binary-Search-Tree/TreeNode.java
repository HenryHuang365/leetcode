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
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);
        if (val <= root.val) {
            if (root.left == null) {
                TreeNode node = new TreeNode(val);
                root.left = node;
            } else {
                root.left = insertIntoBST(root.left, val);
            }
        } else {
            if (root.right == null) {
                TreeNode node = new TreeNode(val);
                System.out.println("node.val: " + node.val);
                root.right = node;
            } else {
                root.right = insertIntoBST(root.right, val);
            }
        }
        return root;
    }

    public static void main(String args[]) {
        // First Tree
        TreeNode twoLeftOne = new TreeNode(1);
        TreeNode twoRightOne = new TreeNode(3);
        TreeNode left = new TreeNode(2, twoLeftOne, twoRightOne);
        TreeNode right = new TreeNode(7);
        TreeNode root = new TreeNode(4, left, right);

        System.out.print("Before insert: ");
        root.inorder();
        System.out.println("");

        System.out.print("After insert: ");
        Solution solution = new Solution();
        solution.insertIntoBST(root, 5);
        root.inorder();
        System.out.println("");
    }
}