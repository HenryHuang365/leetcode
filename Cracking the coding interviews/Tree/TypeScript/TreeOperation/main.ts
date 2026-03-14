import { TreeNode } from "./TreeNode";

function main() {
  let root: TreeNode | null = new TreeNode(50);

  root.insert(30);
  root.insert(20);
  root.insert(40);
  root.insert(70);
  root.insert(60);
  root.insert(80);

  console.log("Inorder: \n");
  root.inorder();
  console.log();

  console.log("Preorder: \n");
  root.preorder();
  console.log();

  console.log("Postorder: \n");
  root.postorder();
  console.log();

  const isExist = root.search(40);
  console.log("40 exists in the tree is " + isExist + "\n");

  root = root.delete(20);
  if (root) {
    console.log("Tree after 20 is deleted: ");
    root.inorder();
    console.log();
  }
}

main();
