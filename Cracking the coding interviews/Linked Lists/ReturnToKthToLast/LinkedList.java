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

    public int printKthToLastRecursion(LinkedListNode head, int k) {
        // The recursive solution for this question is a deviation of recursively
        // counting the number of nodes in a linked list.
        // Without the printout, the output of this function is just the number of nodes
        // in the linked list.
        if (head == null) {
            return 0;
        }
        int index = printKthToLastRecursion(head.next, k) + 1;
        if (index == k) {
            System.out.println(k + " th to the last node is " + head.data);
        }
        return index;
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
        System.out.print("LinkedList: ");
        linkedList.printLinkedList();
        System.out.println(linkedList.printKthToLastRecursion(linkedList.head, 4));
    }
}