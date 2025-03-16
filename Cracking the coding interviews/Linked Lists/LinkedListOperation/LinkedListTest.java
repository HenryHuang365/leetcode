import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LinkedListTest {
    private LinkedList list;

    @Before
    public void setUp() {
        list = new LinkedList();
    }

    @Test
    public void testAppendToTail() {
        list.appendToTail(10);
        list.appendToTail(20);
        list.appendToTail(30);

        assertEquals(10, list.head.data);
        assertEquals(20, list.head.next.data);
        assertEquals(30, list.head.next.next.data);
    }

    @Test
    public void testPrepend() {
        list.prepend(30);
        list.prepend(20);
        list.prepend(10);

        assertEquals(10, list.head.data);
        assertEquals(20, list.head.next.data);
        assertEquals(30, list.head.next.next.data);
    }

    @Test
    public void testDelete() {
        list.appendToTail(10);
        list.appendToTail(20);
        list.appendToTail(30);
        list.delete(20);

        assertEquals(10, list.head.data);
        assertEquals(30, list.head.next.data);
    }

    @Test
    public void testDeleteHead() {
        list.appendToTail(10);
        list.appendToTail(20);
        list.delete(10);

        assertEquals(20, list.head.data);
    }

    @Test
    public void testDeleteNonExistentElement() {
        list.appendToTail(10);
        list.appendToTail(20);
        list.appendToTail(30);
        list.delete(40); // 40 is not in the list

        assertEquals(10, list.head.data);
        assertEquals(20, list.head.next.data);
        assertEquals(30, list.head.next.next.data);
    }

    @Test
    public void testHasCycle() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.head = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node1; // Creating a cycle

        assertTrue(list.hasCycle(list.head));
    }

    @Test
    public void testHasNoCycle() {
        list.appendToTail(10);
        list.appendToTail(20);
        list.appendToTail(30);

        assertFalse(list.hasCycle(list.head));
    }

    @Test
    public void testAppendToTailRecursively() {
        list.appendToTailRecursion(list.head, 10);
        list.appendToTailRecursion(list.head, 20);
        list.appendToTailRecursion(list.head, 30);

        assertEquals(10, list.head.data);
        assertEquals(20, list.head.next.data);
        assertEquals(30, list.head.next.next.data);
    }
}
