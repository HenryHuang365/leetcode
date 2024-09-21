/**
 * InnerLinkedList
 */
class LinkedListNode {
    int data;
    LinkedListNode next;

    public LinkedListNode(int value) {
        this.data = value;
        this.next = null;
    }
}

public class LinkedList {
    LinkedListNode head;

    public void append(int value) {
        if (head == null) {
            head = new LinkedListNode(value);
            return;
        }
        LinkedListNode currNode = head;
        while (currNode.next != null) {
            currNode = currNode.next;
        }
        currNode.next = new LinkedListNode(value);
    }

    public void printLinkedList() {
        LinkedListNode n = head;
        while (n != null) {
            System.out.print(n.data + " ");
            n = n.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();

        linkedList.append(0);
        linkedList.append(1);
        linkedList.append(2);
        linkedList.append(1);
        linkedList.append(3);
        linkedList.append(3);
        linkedList.append(2);
        System.out.print("Before: ");
        linkedList.printLinkedList();
        // linkedList.deleteDups(linkedList.head);
        System.out.print("After: ");
        linkedList.printLinkedList();

        LinkedList linkedListTwo = new LinkedList();

        linkedListTwo.append(0);
        linkedListTwo.append(1);
        linkedListTwo.append(2);
        linkedListTwo.append(1);
        linkedListTwo.append(3);
        linkedListTwo.append(3);
        linkedListTwo.append(2);
        System.out.print("Before: ");
        linkedListTwo.printLinkedList();
        // linkedListTwo.deleteDupsTwoNodes(linkedListTwo.head);
        System.out.print("After: ");
        linkedListTwo.printLinkedList();
    }
}