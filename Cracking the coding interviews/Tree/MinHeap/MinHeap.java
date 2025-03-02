import java.util.ArrayList;

class MinHeap {
    private ArrayList<Integer> heap;

    public MinHeap() {
        heap = new ArrayList<>();
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void insert(int value) {
        heap.add(value);
        int index = heap.size() - 1;

        // Heapify up (Bubble up)
        while (index > 0 && heap.get(index) < heap.get(parent(index))) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    public int extractMin() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty!");
        }

        if (heap.size() == 1) {
            return heap.remove(0);
        }

        int min = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));

        // Heapify down (Bubble down)
        heapifyDown(0);

        return min;
    }

    private void heapifyDown(int index) {
        int smallest = index;
        int left = leftChild(index);
        int right = rightChild(index);

        if (left < heap.size() && heap.get(left) < heap.get(smallest)) {
            smallest = left;
        }

        if (right < heap.size() && heap.get(right) < heap.get(smallest)) {
            smallest = right;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    public void printHeap() {
        System.out.println(heap);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int peek() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty!");
        }
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }
}
