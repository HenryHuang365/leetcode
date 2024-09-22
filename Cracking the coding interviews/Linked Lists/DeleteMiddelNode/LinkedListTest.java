import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class LinkedListTest {
    LinkedList linkedList;

    @Before
    public void setUp() {
        linkedList = new LinkedList();
    }

    @Test
    public void testDeleteNodeOnEmptyList() {
        // Case 1: Delete a node from an empty list
        assertFalse("Should return false when trying to delete a node from an empty list",
                linkedList.deleteNode(linkedList.head));
    }

    @Test
    public void testDeleteOnlyNode() {
        // Case 2: Delete the head node when there's only one node
        linkedList.append(1);
        assertFalse("Should return false when trying to delete the only node in the list",
                linkedList.deleteNode(linkedList.head));
    }

    @Test
    public void testDeleteNullNode() {
        // Case 3: Delete a null node
        assertFalse("Should return false when trying to delete a null node", linkedList.deleteNode(null));
    }

    @Test
    public void testDeleteLastNode() {
        // Case 4: Delete the last node
        linkedList.append(1);
        linkedList.append(2);
        linkedList.append(3);
        LinkedListNode lastNode = linkedList.head.next.next; // This is the last node (with value 3)
        assertFalse("Should return false when trying to delete the last node", linkedList.deleteNode(lastNode));
    }

    @Test
    public void testDeleteMiddleNode() {
        // Case 5: Delete a valid middle node
        linkedList.append(1);
        linkedList.append(2);
        linkedList.append(3);
        linkedList.append(4);

        LinkedListNode middleNode = linkedList.head.next.next; // Node with value 3
        assertTrue("Should return true when deleting a middle node", linkedList.deleteNode(middleNode));
        assertEquals(Arrays.asList(1, 2, 4), linkedList.outputLinkedList());
    }

    @Test
    public void testDeleteNodeWithTwoNodes() {
        // Case 6: Delete the first node when there are two nodes
        linkedList.append(1);
        linkedList.append(2);

        assertTrue("Should return true when deleting the first node of a two-node list",
                linkedList.deleteNode(linkedList.head));

        assertEquals(Arrays.asList(2), linkedList.outputLinkedList());
    }
}
