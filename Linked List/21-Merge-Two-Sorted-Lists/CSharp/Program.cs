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
    public ListNode? MergeTwoLists(ListNode? list1, ListNode? list2)
    {
        if (list1 == null)
        {
            return list2;
        }
        else if (list2 == null)
        {
            return list1;
        }

        ListNode res = new ListNode(0);
        ListNode currNode = res;

        while (list1 != null && list2 != null)
        {
            if (list1.val >= list2.val)
            {
                currNode.next = new ListNode(list2.val);
                list2 = list2.next;
            }
            else
            {
                currNode.next = new ListNode(list1.val);
                list1 = list1.next;
            }

            currNode = currNode.next;
        }

        currNode.next = list1 == null ? list2 : list1;

        return res.next;
    }
    static void Main()
    {
        LinkedList list1 = new LinkedList();
        list1.appendToTail(1);
        list1.appendToTail(2);
        list1.appendToTail(4);

        LinkedList list2 = new LinkedList();
        list2.appendToTail(1);
        list2.appendToTail(3);
        list2.appendToTail(4);

        Console.WriteLine("Before: ");
        list1.printLinkedList();
        Console.WriteLine("");
        list2.printLinkedList();
        Console.WriteLine("");

        Program program = new Program();
        ListNode? newHead = program.MergeTwoLists(list1.head, list2.head);
        Console.Write("Merged: ");
        while (newHead != null)
        {
            Console.Write(newHead.val + " ");
            newHead = newHead.next;
        }
    }
}