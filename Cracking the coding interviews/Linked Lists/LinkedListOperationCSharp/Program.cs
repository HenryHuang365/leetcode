using LinkedListOperationCSharp.CleanEmpty;
using LinkedListOperationCSharp.LinkedList;
using LinkedListOperationCSharp.Node;

class Program
{
    static void Main()
    {
        Node root = new Node(50);

        root.insert(30);
        root.insert(20);
        root.insert(40);
        root.insert(70);
        root.insert(60);
        root.insert(80);
        Console.WriteLine("Inorder Tree: ");
        root.preorder();
        int res = maxProfit([7, 6, 4, 3, 1]);
        Console.WriteLine("");

        Console.WriteLine("\nMax profit: " + res);

        LinkedList linkedList = new LinkedList();
        linkedList.appendToTail(0);
        linkedList.appendToTail(1);
        linkedList.appendToTail(4);
        linkedList.prepend(5);
        linkedList.delete(1);
        linkedList.appendToTailRecursion(linkedList.head, 6);

        LinkedList listTwo = new LinkedList();
        listTwo.appendToTailRecursion(listTwo.head, 0);
        listTwo.appendToTailRecursion(listTwo.head, 1);
        listTwo.appendToTailRecursion(listTwo.head, 2);
        listTwo.appendToTailRecursion(listTwo.head, 3);
        listTwo.delete(1);
        listTwo.prepend(4);
        listTwo.appendToTailRecursion(listTwo.head, 5);

        Console.Write("\nLinked list: ");
        linkedList.printLinkedList();
        linkedList.makeCycle(); // connect the tail to head to make a cycle
        Console.WriteLine("\nhas cycle: " + linkedList.hasCycle()); // Expected true

        Console.WriteLine("\nLinked list two recursion: ");
        listTwo.printLinkedListRecursion(listTwo.head);
        Console.WriteLine("");

        Console.Write("\nClean json: ");
        CleanEmpty.cleanEmptyFunc();
    }

    static int maxProfit(int[] prices)
    {
        int max = 0;
        int start = 0;
        int end = 1;
        while (end < prices.Length)
        {
            int diff = prices[end] - prices[start];
            if (diff < 0)
            {
                start = end;
            }

            max = Math.Max(max, diff);
            end++;
        }
        return max;
    }
}
