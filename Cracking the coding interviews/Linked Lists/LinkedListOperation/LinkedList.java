class Node {
    int data;
    Node next;

    public Node(int value) {
        this.data = value;
        this.next = null;
    }
}

public class LinkedList {
    Node head;

    // Append value to the end of the list iteratively
    public void appendToTail(int value) {
        if (head == null) {
            head = new Node(value);
            // Note: this return is important as it stops adding one extra node into the
            // linked list
            return;
        }
        Node currNode = head;
        while (currNode.next != null) {
            currNode = currNode.next;
        }
        currNode.next = new Node(value);
    }

    public void prepend(int value) {
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
    }

    public void delete(int value) {
        if (head == null)
            return;

        if (head.data == value) {
            head = head.next;
        }

        Node currNode = head;
        while (currNode.next != null) {
            if (currNode.next.data == value) {
                // This is going to skip the first value appearance
                currNode.next = currNode.next.next;
                // Note: this return here stops the search.
                // Include return if only want to delete the first appearance of the node.
                return;
            }
            currNode = currNode.next;
        }
    }

    // Print the linked list iteratively
    public void printLinkedList() {
        Node n = head;
        while (n != null) {
            System.out.print(n.data + " ");
            n = n.next;
        }
        System.out.println();
    }

    // Append value to the end of the list recursively
    public void appendToTailRecursion(Node node, int value) {
        if (node == null) {
            head = new Node(value); // If head is null, initialize the list
        } else if (node.next == null) {
            node.next = new Node(value);
        } else {
            appendToTailRecursion(node.next, value);
        }
    }

    // Print the linked list recursively
    public void printLinkedListRecursion(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " ");
        printLinkedListRecursion(node.next);
    }
}