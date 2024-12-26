import java.util.*;

public class Solution {
    public boolean canReach(int[] arr, int start) {
        int n = arr.length;
        Queue<Integer> nextToVisit = new LinkedList<>();
        nextToVisit.offer(start);
        HashSet<Integer> visited = new HashSet<>();

        while (!nextToVisit.isEmpty()) {
            int index = nextToVisit.poll();

            if (arr[index] == 0) {
                return true;
            }

            if (visited.contains(index)) {
                continue;
            }

            visited.add(index);
            int leftJump = index - arr[index];
            int rightJump = index + arr[index];
            if (rightJump < n && !visited.contains(rightJump)) {
                nextToVisit.offer(rightJump);
            }

            if (leftJump >= 0 && !visited.contains(leftJump)) {
                nextToVisit.offer(leftJump);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] arr = new int[] { 4, 2, 3, 0, 3, 1, 2 };
        int[] arr2 = new int[] { 3, 0, 2, 1, 2 };
        System.out.println("Output: " + solution.canReach(arr, 5));
        System.out.println("Output: " + solution.canReach(arr, 0));
        System.out.println("Output: " + solution.canReach(arr2, 2));
    }
}
