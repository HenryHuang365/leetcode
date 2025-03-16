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

        LinkedList listTwo = new LinkedList();
        listTwo.appendToTailRecursion(listTwo.head, 0);
        listTwo.appendToTailRecursion(listTwo.head, 1);
        listTwo.appendToTailRecursion(listTwo.head, 2);
        listTwo.appendToTailRecursion(listTwo.head, 3);
        System.out.print("Linked list two before: ");
        list.printLinkedListRecursion(listTwo.head); // Recursive print

        listTwo.delete(3);
        listTwo.appendToTailRecursion(listTwo.head, 4);
        System.out.println("");
        System.out.print("Linked list two after deletion: ");
        list.printLinkedListRecursion(listTwo.head); // Recursive print
    }
}
