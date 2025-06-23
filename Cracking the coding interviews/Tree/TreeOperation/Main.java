public class Main {

    public void print(Node root) {
        if (root == null) {
            return;
        }

        print(root.left);
        System.out.print(root.data + " ");
        print(root.right);
    }

    public static void main(String[] args) { // 30, 20, 40, 70, 60, 80
        Main solution = new Main();
        Node root = new Node(50);

        root.insert(30);
        root.insert(20);
        root.insert(40);
        root.insert(70);
        root.insert(60);
        root.insert(80);

        System.out.println("Inorder: ");
        root.inorder();
        System.out.println();

        System.out.println("Inorder Print: ");
        solution.print(root);
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