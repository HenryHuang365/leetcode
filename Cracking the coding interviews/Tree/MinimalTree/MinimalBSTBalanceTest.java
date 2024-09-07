import org.junit.Test;
import static org.junit.Assert.*;

public class MinimalBSTBalanceTest {

    @Test
    public void testMinimalBSTHeight() {
        MinimalBST bst = new MinimalBST();
        int[] sortedArray = {1, 2, 3};
        TreeNode root = bst.createMinimalBST(sortedArray);
        
        int height = bst.calculateHeight(root);
        
        // Verify the height of the tree is minimal
        assertEquals(2, height);
    }

    @Test
    public void testBSTIsBalanced() {
        MinimalBST bst = new MinimalBST();
        int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        TreeNode root = bst.createMinimalBST(sortedArray);
        
        int leftHeight = bst.calculateHeight(root.left);
        int rightHeight = bst.calculateHeight(root.right);
        
        // Ensure that the difference between left and right subtrees is at most 1
        assertTrue(Math.abs(leftHeight - rightHeight) <= 1);
    }

    // Add more balance-related tests
}
