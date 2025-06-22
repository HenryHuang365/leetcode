export class TreeNode {
  private data: number;
  private left: TreeNode | null;
  private right: TreeNode | null;

  constructor(value: number) {
    this.data = value;
    this.left = null;
    this.right = null;
  }

  public insert(value: number) {
    if (value <= this.data) {
      if (this.left == null) {
        this.left = new TreeNode(value);
      } else {
        this.left.insert(value);
      }
    } else {
      if (this.right == null) {
        this.right = new TreeNode(value);
      } else {
        this.right.insert(value);
      }
    }
  }

  public search(value: number): boolean {
    if (this.data == value) {
      return true;
    } else if (value < this.data) {
      if (this.left == null) {
        return false;
      } else {
        return this.left.search(value);
      }
    } else {
      if (this.right == null) {
        return false;
      } else {
        return this.right.search(value);
      }
    }
  }

  public inorder() {
    if (this.left !== null) {
      this.left.inorder();
    }

    console.log(this.data + " ");

    if (this.right !== null) {
      this.right.inorder();
    }
  }

  public preorder() {
    console.log(this.data + " ");

    if (this.left !== null) {
      this.left.preorder();
    }

    if (this.right !== null) {
      this.right.preorder();
    }
  }

  public postorder() {
    if (this.left !== null) {
      this.left.postorder();
    }

    if (this.right !== null) {
      this.right.postorder();
    }

    console.log(this.data + " ");
  }

  public minValue(node: TreeNode): TreeNode {
    let currNode: TreeNode = node;
    while (currNode.left !== null) {
      currNode = currNode.left;
    }
    return currNode;
  }

  public delete(value: number): TreeNode | null {
    if (value < this.data) {
      if (this.left !== null) {
        this.left = this.left.delete(value);
      }
    } else if (value > this.data) {
      if (this.right !== null) {
        this.right = this.right.delete(value);
      }
    } else {
      if (this.left == null) {
        return this.right;
      } else if (this.right == null) {
        return this.left;
      }
      this.data = this.minValue(this.right).data;
      this.right = this.delete(this.data);
    }
    return this;
  }
}
