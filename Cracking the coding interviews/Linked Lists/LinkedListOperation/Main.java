/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.appendToTail(1);
        list.appendToTail(2);
        list.appendToTail(3);
        list.prepend(0);
        list.delete(2);
        System.out.print("Iterative print: ");
        list.printLinkedList(); // Iterative print

        System.out.print("Recursive append and print: ");
        list.appendToTailRecursion(list.head, 4);
        list.printLinkedListRecursion(list.head); // Recursive print
        System.out.println();
        System.out.println("Has Cycled: " + list.hasCycle(list.head));
    }
}
