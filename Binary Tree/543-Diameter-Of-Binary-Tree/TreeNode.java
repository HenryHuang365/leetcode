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
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null)
            return 0;
        int maxDiameterFromThisNode = maxPath(root.left) + maxPath(root.right);

        int maxDiameterFromChildNode = Math.max(diameterOfBinaryTree(root.left), diameterOfBinaryTree(root.right));
        return Math.max(maxDiameterFromThisNode, maxDiameterFromChildNode);
    }

    public int maxPath(TreeNode root) {
        if (root == null)
            return 0;
        int leftLength = maxPath(root.left);
        int rightLength = maxPath(root.right);
        return Math.max(leftLength, rightLength) + 1;
    }

    public static void main(String args[]) {
        // First Tree
        TreeNode twoLeftOne = new TreeNode(4);
        TreeNode twoRightOne = new TreeNode(5);
        TreeNode left = new TreeNode(2, twoLeftOne, twoRightOne);
        TreeNode right = new TreeNode(3);
        TreeNode root = new TreeNode(1, left, right);

        System.out.print("Tree one: ");
        root.inorder();
        System.out.println("");
        Solution solution = new Solution();
        int resultOne = solution.diameterOfBinaryTree(root);
        System.out.println("resultOne: " + resultOne);

        // Second Tree
        TreeNode secondLeft = new TreeNode(2);
        TreeNode secondRoot = new TreeNode(1, secondLeft, null);

        System.out.print("Tree two ");
        secondRoot.inorder();
        System.out.println("");
        int resultTwo = solution.diameterOfBinaryTree(secondRoot);
        System.out.println("resultTwo: " + resultTwo);
    }
}