import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public void insert(int value) {
        if (value <= val) {
            if (left == null) {
                TreeNode node = new TreeNode(value);
                left = node;
            } else {
                left.insert(value);
            }
        } else {
            if (right == null) {
                TreeNode node = new TreeNode(value);
                right = node;
            } else {
                right.insert(value);
            }
        }
    }

    public void preorder() {
        System.out.print(val + " ");

        if (left != null) {
            left.preorder();
        }

        if (right != null) {
            right.preorder();
        }
    }
}

class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        traversal(root, list, 0);
        return list;
    }

    public void traversal(TreeNode root, List<List<Integer>> list, int level) {
        if (root == null) {
            return;
        }

        if (list.size() - 1 < level) {
            List<Integer> path = new ArrayList<>();
            path.add(root.val);
            list.add(path);
        } else {
            list.get(level).add(root.val);
        }

        traversal(root.left, list, level + 1);
        traversal(root.right, list, level + 1);
    }

    public static void main(String args[]) {
        // First Tree
        TreeNode twoLeftTwo = new TreeNode(15);
        TreeNode twoRightTwo = new TreeNode(7);
        TreeNode left = new TreeNode(9, new TreeNode(1), new TreeNode(2));
        TreeNode right = new TreeNode(20, twoLeftTwo, twoRightTwo);
        TreeNode root = new TreeNode(3, left, right);

        System.out.print("Tree: ");
        root.preorder();
        System.out.println("");

        Solution solution = new Solution();
        System.out.println("Output: " + solution.levelOrder(root).toString());

        // Second Tree
        TreeNode secondRoot = new TreeNode(1);

        System.out.print("Second tree: ");
        secondRoot.preorder();
        System.out.println("");

        System.out.print("Output: " + solution.levelOrder(secondRoot).toString());
    }
}