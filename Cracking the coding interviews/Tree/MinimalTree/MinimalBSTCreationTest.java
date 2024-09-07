import org.junit.Test;
import static org.junit.Assert.*;

public class MinimalBSTCreationTest {

    @Test
    public void testMinimalBSTCreation() {
        MinimalBST bst = new MinimalBST();
        int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        TreeNode root = bst.createMinimalBST(sortedArray);
        
        // Check if the root node is correct
        assertEquals(5, root.data);
        
        // Check left and right child of the root
        assertEquals(2, root.left.data);
        assertEquals(7, root.right.data);
    }

    // Add more creation-related tests
}
