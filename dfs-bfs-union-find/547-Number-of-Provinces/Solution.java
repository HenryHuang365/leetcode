import java.util.LinkedList;
import java.util.Queue;

public class Solution {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int count = 0;
        int[] visted = new int[n];
        for (int i = 0; i < isConnected.length; i++) {
            if (visted[i] == 0) {
                count++;
                dfs(isConnected, visted, i);
            }
        }
        return count;
    }

    public void dfs(int[][] isConnected, int[] visted, int i) {
        for (int j = 0; j < isConnected.length; j++) {
            if (visted[j] == 0 && isConnected[i][j] == 1) {
                visted[j] = 1;
                dfs(isConnected, visted, j);
            }
        }
    }

    public int findCircleNumBFS(int[][] isConnected) {
        int count = 0;
        int[] visted = new int[isConnected.length];
        for (int i = 0; i < isConnected.length; i++) {
            if (visted[i] == 0) {
                count++;
                bfs(isConnected, visted, i);
            }
        }
        return count;
    }

    public void bfs(int[][] isConnected, int[] visted, int i) {
        Queue<Integer> nextToVisted = new LinkedList<>();
        nextToVisted.offer(i);
        while (!nextToVisted.isEmpty()) {
            int node = nextToVisted.poll();
            if (visted[node] == 1) {
                continue;
            }

            visted[node] = 1;

            for (int j = 0; j < isConnected.length; j++) {
                if (isConnected[node][j] == 1 && visted[j] == 0) {
                    nextToVisted.offer(j);
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] isConnected = new int[][] { { 1, 1, 0 }, { 1, 1, 0 }, { 0, 0, 1 } };
        int[][] isConnectedTwo = new int[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };

        System.out.println("Output: " + solution.findCircleNum(isConnected));
        System.out.println("Second Output: " + solution.findCircleNum(isConnectedTwo));

        System.out.println("Output: " + solution.findCircleNumBFS(isConnected));
        System.out.println("Second Output: " + solution.findCircleNumBFS(isConnectedTwo));
    }
}
