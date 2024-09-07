import org.junit.Test;
import static org.junit.Assert.*;

public class MinimalBSTTraversalTest {

    // Utility method to capture in-order traversal output as a string
    private void captureInOrderTraversal(TreeNode node, StringBuilder result) {
        if (node != null) {
            captureInOrderTraversal(node.left, result);
            result.append(node.data).append(" ");
            captureInOrderTraversal(node.right, result);
        }
    }

    @Test
    public void testInOrderTraversal() {
        MinimalBST bst = new MinimalBST();
        int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        TreeNode root = bst.createMinimalBST(sortedArray);
        
        StringBuilder result = new StringBuilder();
        captureInOrderTraversal(root, result);
        
        assertEquals("1 2 3 4 5 6 7 8 9", result.toString().trim());
    }

    // Add more traversal-related tests
}
