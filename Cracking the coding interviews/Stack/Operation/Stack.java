import java.util.EmptyStackException;

/**
 * Stack
 */
public class Stack {
    public static class Node {
        int data;
        Node next;

        public Node(int value) {
            this.data = value;
            this.next = null;
        }
    }

    private Node top;

    public int pop() {
        if (top == null)
            throw new EmptyStackException();
        int value = top.data;
        top = top.next;
        return value;
    }

    public void push(int value) {
        Node newNode = new Node(value);
        if (top != null) {
            newNode.next = top;
        }
        top = newNode;
    }

    public int peek() {
        if (top == null)
            throw new EmptyStackException();
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void printStack() {
        Node n = top;
        while (n != null) {
            System.out.print(n.data + " ");
            n = n.next;
        }

        System.out.println();
    }
}