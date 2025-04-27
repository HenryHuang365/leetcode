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
    public ListNode MergeTwoLists(ListNode list1, ListNode list2)
    {
        return list1;
    }
    static void Main()
    {
        LinkedList list = new LinkedList();
        list.appendToTail(0);
        list.appendToTail(1);
        list.appendToTail(2);
        list.appendToTail(3);
        Console.Write("Before: ");
        list.printLinkedList();

        Console.WriteLine("");

        Program program = new Program();
        ListNode? newHead = program.MergeTwoLists(list.head ?? new ListNode(0), list.head ?? new ListNode(0));
        Console.Write("After: ");
        while (newHead != null)
        {
            Console.Write(newHead.val + " ");
            newHead = newHead.next;
        }
    }
}