import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class LinkedListTest {
    LinkedList linkedList;

    @Before
    public void setUp() {
        linkedList = new LinkedList();
    }

    // Helper function to convert the linked list to an array for easy testing
    public int[] toArray(LinkedListNode head) {
        LinkedListNode curr = head;
        ArrayList<Integer> result = new ArrayList<>();
        while (curr != null) {
            result.add(curr.data);
            curr = curr.next;
        }
        return result.stream().mapToInt(i -> i).toArray();
    }

    // Test case 1: Removing duplicates with deleteDups - Normal case with some duplicates
    @Test
    public void testDeleteDups_NormalCase() {
        linkedList.append(0);
        linkedList.append(1);
        linkedList.append(2);
        linkedList.append(1);
        linkedList.append(3);
        linkedList.append(3);
        linkedList.append(2);

        linkedList.deleteDups(linkedList.head);

        assertArrayEquals(new int[] {0, 1, 2, 3}, toArray(linkedList.head));
    }

    // Test case 2: Removing duplicates with deleteDupsTwoNodes - Normal case with some duplicates
    @Test
    public void testDeleteDupsTwoNodes_NormalCase() {
        linkedList.append(0);
        linkedList.append(1);
        linkedList.append(2);
        linkedList.append(1);
        linkedList.append(3);
        linkedList.append(3);
        linkedList.append(2);

        linkedList.deleteDupsTwoNodes(linkedList.head);

        assertArrayEquals(new int[] {0, 1, 2, 3}, toArray(linkedList.head));
    }

    // Test case 3: Empty list
    @Test
    public void testDeleteDups_EmptyList() {
        linkedList.deleteDups(linkedList.head); // Should handle null head gracefully
        assertNull(linkedList.head); // Head should still be null
    }

    // Test case 4: Empty list with deleteDupsTwoNodes
    @Test
    public void testDeleteDupsTwoNodes_EmptyList() {
        linkedList.deleteDupsTwoNodes(linkedList.head); // Should handle null head gracefully
        assertNull(linkedList.head); // Head should still be null
    }

    // Test case 5: List with one element (no duplicates possible)
    @Test
    public void testDeleteDups_OneElement() {
        linkedList.append(1);
        linkedList.deleteDups(linkedList.head);

        assertArrayEquals(new int[] {1}, toArray(linkedList.head));
    }

    // Test case 6: List with one element with deleteDupsTwoNodes
    @Test
    public void testDeleteDupsTwoNodes_OneElement() {
        linkedList.append(1);
        linkedList.deleteDupsTwoNodes(linkedList.head);

        assertArrayEquals(new int[] {1}, toArray(linkedList.head));
    }

    // Test case 7: List with all unique elements
    @Test
    public void testDeleteDups_UniqueElements() {
        linkedList.append(1);
        linkedList.append(2);
        linkedList.append(3);
        linkedList.append(4);

        linkedList.deleteDups(linkedList.head);

        assertArrayEquals(new int[] {1, 2, 3, 4}, toArray(linkedList.head));
    }

    // Test case 8: List with all unique elements with deleteDupsTwoNodes
    @Test
    public void testDeleteDupsTwoNodes_UniqueElements() {
        linkedList.append(1);
        linkedList.append(2);
        linkedList.append(3);
        linkedList.append(4);

        linkedList.deleteDupsTwoNodes(linkedList.head);

        assertArrayEquals(new int[] {1, 2, 3, 4}, toArray(linkedList.head));
    }

    // Test case 9: List with all elements the same (e.g., [1, 1, 1, 1])
    @Test
    public void testDeleteDups_AllDuplicates() {
        linkedList.append(1);
        linkedList.append(1);
        linkedList.append(1);
        linkedList.append(1);

        linkedList.deleteDups(linkedList.head);

        assertArrayEquals(new int[] {1}, toArray(linkedList.head));
    }

    // Test case 10: List with all elements the same using deleteDupsTwoNodes
    @Test
    public void testDeleteDupsTwoNodes_AllDuplicates() {
        linkedList.append(1);
        linkedList.append(1);
        linkedList.append(1);
        linkedList.append(1);

        linkedList.deleteDupsTwoNodes(linkedList.head);

        assertArrayEquals(new int[] {1}, toArray(linkedList.head));
    }
}
