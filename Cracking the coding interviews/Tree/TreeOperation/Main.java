public class Main {
    public static void main(String[] args) {
        // create a new root
        Node root = new Node(50);

        // Insert nodes
        root.insert(30);
        root.insert(20);
        root.insert(40);
        root.insert(70);
        root.insert(60);
        root.insert(80);

        // In-order traversal
        System.out.print("In-order traversal: ");
        root.inOrder();
        System.out.println();

        // Pre-order traversal
        System.out.print("Pre-order traversal: ");
        root.preOrder();
        System.out.println();

        // Post-order traversal
        System.out.print("Post-order traversal: ");
        root.postOrder();
        System.out.println();

        // Search for a value
        int key = 40;
        if (root.search(key))
            System.out.println(key + " is present in the tree.");
        else
            System.out.println(key + " is not present in the tree.");

        // Delete a node
        root = root.delete(20);
        System.out.print("In-order traversal after deleting 20: ");
        root.inOrder();
        System.out.println();

        MinHeap minHeap = new MinHeap(10);

        minHeap.insert(10);
        minHeap.insert(15);
        minHeap.insert(30);
        minHeap.insert(40);
        minHeap.insert(50);
        minHeap.insert(5);

        // Print the heap array after insertion
        System.out.println("\nMinHeap Tree:");
        minHeap.printHeap();
    }
}