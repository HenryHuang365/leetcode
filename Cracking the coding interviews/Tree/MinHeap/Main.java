public class Main {
    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap();

        minHeap.insert(10);
        minHeap.insert(20);
        minHeap.insert(5);
        minHeap.insert(7);
        minHeap.insert(3);
        minHeap.insert(15);

        System.out.println("MinHeap:");
        minHeap.printHeap();

        System.out.println("Extracting Min: " + minHeap.extractMin());
        System.out.println("MinHeap after extraction:");
        minHeap.printHeap();

        System.out.println("Extracting Min: " + minHeap.extractMin());
        System.out.println("MinHeap after extraction:");
        minHeap.printHeap();
    }
}
