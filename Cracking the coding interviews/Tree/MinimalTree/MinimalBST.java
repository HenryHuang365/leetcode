/**
 * Solution
 * 
 * Given a sorted (increasing order) array with unique integer elements, write
 * an algorithm
 * to create a binary search tree with minimal height.
 */
// Class representing a node in the binary search tree

/**
 * InnerMinimalBST
 */
class TreeNode {
    int data;
    TreeNode left, right;

    public TreeNode(int value) {
        this.data = value;
        this.left = null;
        this.right = null;
    }

}

public class MinimalBST {

    public TreeNode createMinimalBST(int[] array) {
        return helper(array, 0, array.length - 1);
    }

    public TreeNode helper(int[] array, int start, int end) {
        if (end < start)
            return null;

        int mid = (start + end) / 2;
        TreeNode rootNode = new TreeNode(array[mid]);

        rootNode.left = helper(array, start, mid - 1);
        rootNode.right = helper(array, mid + 1, end);

        return rootNode;
    }

    public void inOrderTraversal(TreeNode node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.print(node.data + " ");
            inOrderTraversal(node.right);
        }
    }

    public int calculateHeight(TreeNode node) {
        if (node == null)
            return 0;

        int leftHeight = calculateHeight(node.left);
        int rightHeight = calculateHeight(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) {
        MinimalBST bst = new MinimalBST();

        int[] sortedArray = { 1, 2, 3 };

        // Create a minimal height BST
        TreeNode root = bst.createMinimalBST(sortedArray);

        // In-order traversal to check the correctness of the BST
        System.out.println("In-order traversal of the created BST:");
        bst.inOrderTraversal(root);
        System.out.println();

        // Calculate the height of the created BST
        int height = bst.calculateHeight(root);
        System.out.println("Height of the created BST: " + height);
    }
}
