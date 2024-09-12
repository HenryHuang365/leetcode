import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NodeTest {

    private Node root;

    // Set up a sample tree before each test
    @Before
    public void setUp() {
        root = new Node(50);
        root.insert(30);
        root.insert(20);
        root.insert(40);
        root.insert(70);
        root.insert(60);
        root.insert(80);
    }

    @Test
    public void testInsert() {
        // Inserting a new value and checking if it's added
        root.insert(25);
        assertTrue(root.search(25)); // Should find the new value

        // Check if the tree structure is valid via in-order traversal
        StringBuilder result = new StringBuilder();
        captureInOrder(root, result);
        assertEquals("20 25 30 40 50 60 70 80", result.toString().trim());
    }

    @Test
    public void testInOrderTraversal() {
        StringBuilder result = new StringBuilder();
        captureInOrder(root, result);
        assertEquals("20 30 40 50 60 70 80", result.toString().trim());
    }

    @Test
    public void testPreOrderTraversal() {
        StringBuilder result = new StringBuilder();
        capturePreOrder(root, result);
        assertEquals("50 30 20 40 70 60 80", result.toString().trim());
    }

    @Test
    public void testPostOrderTraversal() {
        StringBuilder result = new StringBuilder();
        capturePostOrder(root, result);
        assertEquals("20 40 30 60 80 70 50", result.toString().trim());
    }

    @Test
    public void testSearch() {
        // Positive cases
        assertTrue(root.search(50));
        assertTrue(root.search(30));
        assertTrue(root.search(70));

        // Negative cases
        assertFalse(root.search(100)); // Non-existing value
        assertFalse(root.search(10)); // Non-existing value
    }

    @Test
    public void testDeleteLeafNode() {
        // Delete a leaf node (no children)
        root = root.delete(20); // 20 is a leaf
        assertFalse(root.search(20)); // Should not find 20

        // Check if tree structure is valid
        StringBuilder result = new StringBuilder();
        captureInOrder(root, result);
        assertEquals("30 40 50 60 70 80", result.toString().trim());
    }

    @Test
    public void testDeleteNodeWithOneChild() {
        // Delete a node with one child (30 has child 40)
        root = root.delete(30);
        assertFalse(root.search(30)); // Should not find 30

        // Check if the structure is still correct
        StringBuilder result = new StringBuilder();
        captureInOrder(root, result);
        assertEquals("20 40 50 60 70 80", result.toString().trim());
    }

    @Test
    public void testDeleteNodeWithTwoChildren() {
        // Delete a node with two children (50 has children 30 and 70)
        root = root.delete(50);
        assertFalse(root.search(50)); // Should not find 50

        // Check if the structure is still valid
        StringBuilder result = new StringBuilder();
        captureInOrder(root, result);
        assertEquals("20 30 40 60 70 80", result.toString().trim());
    }

    // Helper method to capture in-order traversal as a string
    private void captureInOrder(Node node, StringBuilder result) {
        if (node != null) {
            captureInOrder(node.left, result);
            result.append(node.data).append(" ");
            captureInOrder(node.right, result);
        }
    }

    // Helper method to capture pre-order traversal as a string
    private void capturePreOrder(Node node, StringBuilder result) {
        if (node != null) {
            result.append(node.data).append(" ");
            capturePreOrder(node.left, result);
            capturePreOrder(node.right, result);
        }
    }

    // Helper method to capture post-order traversal as a string
    private void capturePostOrder(Node node, StringBuilder result) {
        if (node != null) {
            capturePostOrder(node.left, result);
            capturePostOrder(node.right, result);
            result.append(node.data).append(" ");
        }
    }
}