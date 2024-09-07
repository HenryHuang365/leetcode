import org.junit.Test;
import static org.junit.Assert.*;

public class MinimalBSTEdgeCasesTest {

    @Test
    public void testEmptyArray() {
        MinimalBST bst = new MinimalBST();
        int[] emptyArray = {};
        TreeNode root = bst.createMinimalBST(emptyArray);
        
        assertNull(root);  // The root should be null for an empty array
    }

    @Test
    public void testSingleElementArray() {
        MinimalBST bst = new MinimalBST();
        int[] singleElementArray = {5};
        TreeNode root = bst.createMinimalBST(singleElementArray);
        
        assertNotNull(root);
        assertEquals(5, root.data);
        assertNull(root.left);
        assertNull(root.right);
    }

    // Add more edge case tests
}
