public class ListNode {
    int val;
    ListNode next;

    public ListNode(int x) {
        val = x;
        next = null;
    }
}

class LinkedList {
    ListNode head;

    public void appendToTail(int value) {
        if (head == null) {
            head = new ListNode(value);
            return;
        }
        ListNode currNode = head;
        while (currNode.next != null) {
            currNode = currNode.next;
        }
        currNode.next = new ListNode(value);
    }

    public void makeCycle() {
        if (head == null || head.next == null) {
            return;
        }

        ListNode currNode = head;
        while (currNode.next != null) {
            currNode = currNode.next;
        }

        currNode.next = head;
    }

    public void prepend(int value) {
        ListNode newNode = new ListNode(value);
        newNode.next = head;
        head = newNode;
    }

    public void delete(int value) {
        if (head == null)
            return;

        if (head.val == value) {
            head = head.next;
            return;
        }

        ListNode currNode = head;
        while (currNode.next != null) {
            if (currNode.next.val == value) {
                currNode.next = currNode.next.next;
                return;
            }
            currNode = currNode.next;
        }
    }

    public void printLinkedList() {
        ListNode n = head;
        while (n != null) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
    }
}

class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null)
            return null;
        ListNode resHead = null;

        ListNode currNode = head;

        while (currNode != null) {
            ListNode newNode = new ListNode(currNode.val);
            newNode.next = resHead;
            resHead = newNode;
            currNode = currNode.next;
        }

        return resHead;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        LinkedList list = new LinkedList();
        list.appendToTail(0);
        list.appendToTail(1);
        list.appendToTail(2);
        list.appendToTail(3);
        System.out.print("Before: ");
        list.printLinkedList();

        ListNode res = solution.reverseList(list.head);
        System.out.print("Reverse: ");
        ListNode currNode = res;
        while (currNode != null) {
            System.out.print(currNode.val + " ");
            currNode = currNode.next;
        }
        System.out.println("");
    }
}