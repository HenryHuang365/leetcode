public class Main {
    public static void main(String[] args) {
        Node root = new Node(50);

        // Insert nodes
        root.insert(30);
        root.insert(20);
        root.insert(40);
        root.insert(70);
        root.insert(60);
        root.insert(80);

        System.out.println("Inorder: ");
        root.inorder();
        System.out.println();

        System.out.println("Preorder: ");
        root.preorder();
        System.out.println();

        System.out.println("Postorder: ");
        root.postorder();
        System.out.println();

        boolean isExist = root.search(40);
        System.out.println("40 exists in the tree is " + isExist);

        root = root.delete(20);
        System.out.println("Tree after 20 is deleted: ");
        root.inorder();
        System.out.println();
    }
}