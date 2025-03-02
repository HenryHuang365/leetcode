class Node {
    Node left, right;
    int data;

    public Node(int value) {
        this.data = value;
        this.left = null;
        this.right = null;
    }

    public void insert(int value) {
        if (value <= data) {
            if (left == null) {
                Node node = new Node(value);
                left = node;
            } else {
                left.insert(value);
            }
        } else {
            if (right == null) {
                Node node = new Node(value);
                right = node;
            } else {
                right.insert(value);
            }
        }
    }

    public boolean search(int key) {
        if (data == key) {
            return true;
        } else if (data > key) {
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
            data = minValue(right).data;
            right = right.delete(data);
        }
        return this;
    }
}