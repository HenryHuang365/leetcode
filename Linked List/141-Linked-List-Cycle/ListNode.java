// Given head, the head of a linked list, determine if the linked list has a cycle in it.

// There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.

// Return true if there is a cycle in the linked list. Otherwise, return false.

// Example 1:

// Input: head = [3,2,0,-4], pos = 1
// Output: true
// Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
// Example 2:

// Input: head = [1,2], pos = 0
// Output: true
// Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
// Example 3:

// Input: head = [1], pos = -1
// Output: false
// Explanation: There is no cycle in the linked list.

// Constraints:

// The number of the nodes in the list is in the range [0, 104].
// -105 <= Node.val <= 105
// pos is -1 or a valid index in the linked-list.

// Follow up: Can you solve it using O(1) (i.e. constant) memory?\

// Definition for singly-linked list.

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
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        LinkedList list = new LinkedList();
        list.appendToTail(0);
        list.appendToTail(1);
        list.appendToTail(2);
        list.appendToTail(3);
        System.out.println("Output before make cycle: " + solution.hasCycle(list.head));
        list.makeCycle();

        System.out.println("Output after make cycle: " + solution.hasCycle(list.head));
    }
}