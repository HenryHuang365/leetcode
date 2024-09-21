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

    public boolean deleteNode(LinkedListNode n) {
        if (n == null || n.next == null) {
            return false;
        }
        // LinkedListNode nextNode = n.next;
        n.data = n.next.data;
        n.next = n.next.next;
        return true;
    }

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        Boolean isDetelted = linkedList.deleteNode(linkedList.head);
        if (isDetelted) {
            System.out.print("After LinkedList: ");
            linkedList.printLinkedList();
        }
        linkedList.append(0);
        linkedList.append(1);
        linkedList.append(2);
        linkedList.append(1);
        linkedList.append(3);
        linkedList.append(3);
        linkedList.append(2);
        System.out.print("Before LinkedList: ");
        linkedList.printLinkedList();
        isDetelted = linkedList.deleteNode(linkedList.head.next);
        if (isDetelted) {
            System.out.print("After LinkedList: ");
            linkedList.printLinkedList();
        }
    }
}