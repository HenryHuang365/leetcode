import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution {
    public int minJumps(int[] arr) {
        int n = arr.length;
        HashMap<Integer, List<Integer>> indexMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            List<Integer> updateArr = indexMap.getOrDefault(arr[i], new ArrayList<>());
            updateArr.add(i);
            indexMap.put(arr[i], updateArr);
        }

        int count = 0;
        Queue<Integer> nextToVisit = new LinkedList<>();
        nextToVisit.offer(0);
        int[] visited = new int[n];
        while (!nextToVisit.isEmpty()) {
            int size = nextToVisit.size();
            for (int i = 0; i < size; i++) {
                int index = nextToVisit.poll();
                if (visited[index] == 1) {
                    continue;
                }

                visited[index] = 1;
                if (index == n - 1) {
                    return count;
                }

                if (index - 1 >= 0 && visited[index - 1] == 0) {
                    nextToVisit.offer(index - 1);
                }

                if (index + 1 < n && visited[index + 1] == 0) {
                    nextToVisit.offer(index + 1);
                }

                if (indexMap.containsKey(arr[index])) {
                    for (int jumpIndex : indexMap.get(arr[index])) {
                        if (visited[jumpIndex] == 0) {
                            nextToVisit.offer(jumpIndex);
                        }
                    }
                    indexMap.remove(arr[index]);
                }
            }
            count++;
        }

        return count;
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] arr = new int[] {100,-23,-23,404,100,23,23,23,3,404};
        int[] arr1 = new int[] {7,6,9,6,9,6,9,7};
        System.out.println("Output: " + solution.minJumps(arr));
        System.out.println("Output: " + solution.minJumps(arr1));
    }
}
