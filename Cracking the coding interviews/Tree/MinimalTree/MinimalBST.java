/**
 * Solution
 * 
 * Given a sorted (increasing order) array with unique integer elements, write an algorithm
 * to create a binary search tree with minimal height.
 */
// Class representing a node in the binary search tree
class TreeNode {
    int data;
    TreeNode left, right;

    // Constructor to create a new node
    TreeNode(int data) {
        this.data = data;
        left = right = null;
    }
}

public class MinimalBST {

    // Function to create a minimal height BST from a sorted array
    TreeNode createMinimalBST(int array[]) {
        return createMinimalBST(array, 0, array.length - 1);
    }

    // Helper function to recursively create the BST
    TreeNode createMinimalBST(int arr[], int start, int end) {
        if (end < start) {
            return null;
        }

        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(arr[mid]);

        node.left = createMinimalBST(arr, start, mid - 1);
        node.right = createMinimalBST(arr, mid + 1, end);

        return node;
    }

    // Function to calculate the height of the BST
    int calculateHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = calculateHeight(node.left);
        int rightHeight = calculateHeight(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    // In-order traversal to display the tree
    void inOrderTraversal(TreeNode node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.print(node.data + " ");
            inOrderTraversal(node.right);
        }
    }

    public static void main(String[] args) {
        MinimalBST bst = new MinimalBST();

        int[] sortedArray = {1, 2, 3};

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
