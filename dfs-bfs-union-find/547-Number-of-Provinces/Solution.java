import java.util.LinkedList;
import java.util.Queue;

public class Solution {
    public int findCircleNum(int[][] isConnected) {
        int count = 0;
        int[] visited = new int[isConnected.length];
        for (int i = 0; i < isConnected.length; i++) {
            if (visited[i] == 0) {
                dfs(isConnected, visited, i);
                count++;
            }
        }
        return count;
    }

    public void dfs(int[][] isConnected, int[] visited, int i) {
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[i][j] == 1 && visited[j] == 0) {
                visited[j] = 1;
                dfs(isConnected, visited, j);
            }
        }
    }

    public int findCircleNumBFS(int[][] isConnected) {
        int count = 0;
        int[] visited = new int[isConnected.length];
        for (int i = 0; i < isConnected.length; i++) {
            if (visited[i] == 0) {
                bfs(isConnected, visited, i);
                count++;
            }
        }
        return count;
    }

    public void bfs(int[][] isConnected, int[] visited, int i) {
        Queue<Integer> nextToVisit = new LinkedList<>();
        nextToVisit.offer(i);

        while (!nextToVisit.isEmpty()) {
            int node = nextToVisit.poll();
            if (visited[node] == 1) {
                continue;
            }

            visited[node] = 1;

            for (int j = 0; j < isConnected.length; j++) {
                if (isConnected[node][j] == 1 && visited[j] == 0) {
                    nextToVisit.offer(j);
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
