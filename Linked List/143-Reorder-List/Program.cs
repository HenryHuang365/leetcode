public class ListNode
{
    public int val;
    public ListNode? next;
    public ListNode(int val, ListNode? next = null)
    {
        this.val = val;
        this.next = next;
    }
}

public class LinkedList
{
    public ListNode? head;

    public void appendToTail(int value)
    {
        if (head == null)
        {
            head = new ListNode(value);
            return;
        }

        ListNode currNode = head;

        while (currNode.next != null)
        {
            currNode = currNode.next;
        }

        currNode.next = new ListNode(value);
    }

    public void printLinkedList()
    {
        if (head == null)
        {
            return;
        }

        ListNode? currNode = head;
        while (currNode != null)
        {
            Console.Write(currNode.val + " ");
            currNode = currNode.next;
        }
    }
}

class Program
{
    public void ReorderList(ListNode head)
    {
        if (head == null || head.next == null || head.next.next == null)
        {
            return;
        }

        ListNode newHead = head.next;
        ListNode currNode = newHead;
        while (currNode.next.next != null)
        {
            currNode = currNode.next;
        }

        ListNode lastNode = currNode.next;
        currNode.next = null;

        lastNode.next = newHead;
        head.next = lastNode;
        ReorderList(lastNode.next);
    }

    public ListNode LastNode(ListNode node)
    {
        return node;
    }

    static void Main()
    {
        LinkedList list = new LinkedList();
        list.appendToTail(1);
        list.appendToTail(2);
        list.appendToTail(3);
        list.appendToTail(4);
        list.appendToTail(5);

        Console.WriteLine("Before: ");
        list.printLinkedList();
        Console.WriteLine("");

        Program program = new Program();
        ListNode? newHead = list.head;
        program.ReorderList(newHead ?? new ListNode(0));

        Console.Write("After: ");
        while (newHead != null)
        {
            Console.Write(newHead.val + " ");
            newHead = newHead.next;
        }
    }
}