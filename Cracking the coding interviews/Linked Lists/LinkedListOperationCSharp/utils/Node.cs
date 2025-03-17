namespace LinkedListOperationCSharp.Node
{
    class Node
    {
        public Node? left { set; get; }
        public Node? right { set; get; }
        public int data { get; set; }

        public Node(int data)
        {
            this.data = data;
            left = null;
            right = null;
        }

        public void insert(int value)
        {
            if (value <= data)
            {
                if (left == null)
                {
                    left = new Node(value);
                }
                else
                {
                    left.insert(value);
                }
            }
            else
            {
                if (right == null)
                {
                    right = new Node(value);
                }
                else
                {
                    right.insert(value);
                }
            }
        }

        public void preorder()
        {
            Console.Write(data + " ");

            if (left != null)
            {
                left.preorder();
            }

            if (right != null)
            {
                right.preorder();
            }
        }

    }
}