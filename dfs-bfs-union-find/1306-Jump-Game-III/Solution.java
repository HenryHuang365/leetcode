import java.util.*;

public class Solution {
    public boolean canReach(int[] arr, int start) {
        int N = arr.length;
        HashSet<Integer> visitedIndex = new HashSet<>();
        Queue<Integer> nextToVisit = new LinkedList<>();

        nextToVisit.offer(start);

        while (!nextToVisit.isEmpty()) {
            int index = nextToVisit.poll();
            if (visitedIndex.contains(index)) {
                continue;
            }
            visitedIndex.add(index);
            int jumpValue = arr[index];
            if (jumpValue == 0) {
                return true;
            } else {
                if (index - jumpValue >= 0 && index - jumpValue < N) {
                    nextToVisit.offer(index - jumpValue);
                }
                
                if (index + jumpValue >= 0 && index + jumpValue < N) {
                    nextToVisit.offer(index + jumpValue);
                }  
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] arr = new int[] {4, 2, 3, 0, 3, 1, 2};
        int[] arr2 = new int[] {3, 0, 2, 1, 2};
        System.out.println("Output: " + solution.canReach(arr, 5));
        System.out.println("Output: " + solution.canReach(arr, 0));
        System.out.println("Output: " + solution.canReach(arr2, 2));
    }
}
