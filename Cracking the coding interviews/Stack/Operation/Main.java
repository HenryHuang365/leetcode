public class Main {
    public static void main(String[] args) {
        Stack stack = new Stack();
        System.out.println("isEmpty: " + stack.isEmpty());
        stack.push(0);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        System.out.println("isEmpty: " + stack.isEmpty());
        System.out.print("stack: ");
        stack.printStack();
        System.out.println("top: " + stack.peek());
        stack.pop();
        System.out.print("stack: ");
        stack.printStack();
        System.out.println("top: " + stack.peek());
    }
}
