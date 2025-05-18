namespace LinkedListOperationCSharp.LinkedList
{
    public class LinkedListNode
    {
        public int data { get; set; }
        public LinkedListNode? next { get; set; }

        public LinkedListNode(int value)
        {
            data = value;
            next = null;
        }
    }

    public class LinkedList
    {
        public LinkedListNode? head;

        public void appendToTail(int value)
        {
            if (head == null)
            {
                head = new LinkedListNode(value);
                return;
            }

            LinkedListNode currNode = head;

            while (currNode.next != null)
            {
                currNode = currNode.next;
            }

            currNode.next = new LinkedListNode(value);
        }

        public void prepend(int value)
        {
            LinkedListNode newNode = new LinkedListNode(value);
            newNode.next = head;
            head = newNode;
        }

        public void delete(int value)
        {
            if (head == null) return;

            if (head.data == value)
            {
                head = head.next;
                return;
            }

            LinkedListNode currNode = head;
            while (currNode.next != null)
            {
                if (currNode.next.data == value)
                {
                    currNode.next = currNode.next.next;
                    return;
                }

                currNode = currNode.next;
            }
        }

        public bool hasCycle()
        {
            if (head == null || head.next == null) return false;

            LinkedListNode? slow = head;
            LinkedListNode? fast = head;

            while (slow != null && fast != null && fast.next != null)
            {
                slow = slow.next;
                fast = fast.next.next;

                if (slow == fast)
                {
                    return true;
                }
            }
            return false;
        }

        public void makeCycle()
        {
            if (head == null) return;

            LinkedListNode currNode = head;
            while (currNode.next != null)
            {
                currNode = currNode.next;
            }

            currNode.next = head;
        }

        public void printLinkedList()
        {
            if (head == null) return;

            LinkedListNode? currNode = head;
            while (currNode != null)
            {
                Console.Write(currNode.data + " ");
                currNode = currNode.next;
            }
        }

        public void appendToTailRecursion(LinkedListNode? node, int value)
        {
            if (node == null)
            {
                head = new LinkedListNode(value);
                return;
            }

            if (node.next == null)
            {
                node.next = new LinkedListNode(value);
                return;
            }

            appendToTailRecursion(node.next, value);
        }

        public void printLinkedListRecursion(LinkedListNode? node)
        {
            if (node == null)
            {
                return;
            }

            Console.Write(node.data + " ");
            printLinkedListRecursion(node.next);
        }
    }
}