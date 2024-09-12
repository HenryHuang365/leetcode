class MinHeap {
    private int[] heap;
    private int size;
    private int capacity;

    // Constructor to initialize the heap
    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        heap = new int[capacity];
    }

    // Function to insert a new element into the heap
    public void insert(int key) {
        if (size == capacity) {
            throw new IllegalStateException("Heap is full");
        }

        // Insert the new key at the end of the heap
        size++;
        int index = size - 1;
        heap[index] = key;

        // Fix the heap property (bubble up the new key)
        while (index != 0 && heap[parent(index)] > heap[index]) {
            // Swap the current element with its parent
            swap(index, parent(index));
            index = parent(index); // Move up to the parent node
        }
    }

    // Utility function to return the index of the parent
    private int parent(int i) {
        return (i - 1) / 2;
    }

    // Utility function to swap two elements in the heap
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // Utility function to print the heap
    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }
}