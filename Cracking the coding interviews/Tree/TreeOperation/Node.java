class Node {
    Node left, right;
    int data;

    // Constructor for creating an empty tree
    public Node(int value) {
        this.data = value;
        this.left = null;
        this.right = null;
    }

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

    public void inorder() {
        if (left != null) {
            left.inorder();
        }

        System.out.print(data + " ");

        if (right != null) {
            right.inorder();
        }
    }

    public void preorder() {
        System.out.print(data + " ");

        if (left != null) {
            left.preorder();
        }

        if (right != null) {
            right.preorder();
        }
    }

    public void postorder() {
        if (left != null) {
            left.postorder();
        }

        if (right != null) {
            right.postorder();
        }

        System.out.print(data + " ");
    }

    public Node minValue(Node node) {
        Node min = node;
        while (min.left != null) {
            min = min.left;
        }
        return min;
    }

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
            if (left == null) {
                return right;
            } else if (right == null) {
                return left;
            }
            // the deleted node is replaced with a new value from the right subtree.
            data = minValue(right).data;
            // Since the min value in the right subtree is used, we will need to delete the
            // used node in the right subtree.
            right = right.delete(data);
        }
        return this;
    }
}