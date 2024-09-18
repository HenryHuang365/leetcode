public class Main {
    public static void main(String[] args) {
        Queue queue = new Queue();
        System.out.println("isEmpty: " + queue.isEmpty());
        queue.add(0);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        System.out.print("Queue: ");
        queue.printQueue();
        System.out.println("isEmpty: " + queue.isEmpty());
        System.out.println("head: " + queue.peek());
        System.out.println("removed: " + queue.remove());
        System.out.println("head: " + queue.peek());
        System.out.print("Queue: ");
        queue.printQueue();
    }
}
