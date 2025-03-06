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
    TreeNode one = null;
    TreeNode two = null;
    TreeNode prev = new TreeNode(Integer.MIN_VALUE);

    public void recoverTree(TreeNode root) {
        search(root);
        int temp = two.val;
        two.val = one.val;
        one.val = temp;
    }

    public void search(TreeNode root) {
        if (root == null)
            return;

        search(root.left);
        if (one == null && root.val < prev.val) {
            one = prev;
        }

        if (one != null && root.val < prev.val) {
            two = root;
        }
        prev = root;
        search(root.right);
    }

    public void reset() {
        one = null;
        two = null;
        prev = new TreeNode(Integer.MIN_VALUE);
    }

    public static void main(String args[]) {
        // First Tree
        TreeNode right = new TreeNode(1);
        TreeNode left = new TreeNode(3);
        TreeNode root = new TreeNode(2, left, right);

        System.out.print("Before recover: ");
        root.inorder();
        System.out.println("");

        System.out.print("After recover: ");
        Solution solution = new Solution();
        solution.recoverTree(root);
        root.inorder();
        System.out.println("");

        // Second Tree
        solution.reset();
        TreeNode secondLeft = new TreeNode(1);
        TreeNode secondRight = new TreeNode(4, new TreeNode(2), null);
        TreeNode secondRoot = new TreeNode(3, secondLeft, secondRight);

        System.out.print("Before recover: ");
        secondRoot.inorder();
        System.out.println("");

        System.out.print("After recover: ");
        solution.recoverTree(secondRoot);
        secondRoot.inorder();
        System.out.println("");

        // Third Tree
        solution.reset();
        TreeNode thirdLeft = new TreeNode(3, null, new TreeNode(2));
        TreeNode thirdRoot = new TreeNode(1, thirdLeft, null);

        System.out.print("Before recover: ");
        thirdRoot.inorder();
        System.out.println("");

        System.out.print("After recover: ");
        solution.recoverTree(thirdRoot);
        thirdRoot.inorder();
        System.out.println("");
    }
}