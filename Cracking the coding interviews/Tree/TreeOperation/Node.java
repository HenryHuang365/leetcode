class Node {
    Node left, right;
    int data;

    // Constructor for creating an empty tree
    public Node(int value) {
        this.data = value;
        this.left = null;
        this.right = null;
    }

    // Insertion of a new node
    public void insert(int value) {
        if (value <= data) {
            if (left == null) {
                left = new Node(value);
            } else {
                left.insert(value);
            }
        } else {
            if (right == null) {
                right = new Node(value);
            } else {
                right.insert(value);
            }
        }
    }

    // In-order traversal (Left, Root, Right)
    public void inOrder() {
        if (left != null) {
            left.inOrder();
        }
        System.out.print(data + " ");

        if (right != null) {
            right.inOrder();
        }
    }

    // Pre-order traversal (Root, Left, Right)
    public void preOrder() {
        System.out.print(data + " ");

        if (left != null) {
            left.preOrder();
        }

        if (right != null) {
            right.preOrder();
        }
    }

    // Post-order traversal (Left, Right, Root)
    public void postOrder() {
        if (left != null) {
            left.postOrder();
        }

        if (right != null) {
            right.postOrder();
        }

        System.out.print(data + " ");
    }

    // Search for a node in the tree
    public boolean search(int key) {
        if (data == key) {
            return true;
        }
        if (key < data) {
            if (left == null) {
                return false;
            } else {
                return left.search(key);
            }
        } else {
            if (right == null) {
                return false;
            } else {
                return right.search(key);
            }
        }
    }

    // Get the minimum value node (used for deletion)
    Node minValueNode(Node node) {
        Node current = node;

        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Deletion of a node
    public Node delete(int key) {
        if (key < data) {
            if (left != null) {
                left = left.delete(key);
            }
        } else if (key > data) {
            if (right != null) {
                right = right.delete(key);
            }
        } else {
            // Node to be deleted is found
            if (left == null) {
                return right;
            } else if (right == null) {
                return left;
            }

            // Node with two children: Get the inorder successor
            data = minValueNode(right).data;
            right = right.delete(data);
        }
        return this;
    }
}