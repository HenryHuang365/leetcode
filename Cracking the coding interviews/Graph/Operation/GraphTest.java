import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphTest {

    private Graph graph;

    @Before
    public void setUp() {
        graph = new Graph();
    }

    // Helper method to add multiple nodes
    private void addNodes(int... ids) {
        for (int id : ids) {
            graph.addNode(id);
        }
    }

    // Helper method to add multiple edges
    private void addEdges(int[][] edges) {
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }
    }

    @Test
    public void testPathExistsDFSAndBFS() {
        /*
         Graph structure:
         1 -> 2 -> 3
         1 -> 4
         4 -> 5
        */
        addNodes(1, 2, 3, 4, 5);
        addEdges(new int[][]{
            {1, 2},
            {2, 3},
            {1, 4},
            {4, 5}
        });

        assertTrue("DFS should find a path from 1 to 3", graph.hasPathDFS(1, 3));
        assertTrue("DFS should find a path from 1 to 3", graph.hasPathDFSIterative(1, 3));
        assertTrue("BFS should find a path from 1 to 3", graph.hasPathBFS(1, 3));
        assertTrue("BFS should find a path from 1 to 3", graph.hasPathBFSRecursive(1, 3));

        assertTrue("DFS should find a path from 1 to 5", graph.hasPathDFS(1, 5));
        assertTrue("DFS should find a path from 1 to 5", graph.hasPathDFSIterative(1, 5));
        assertTrue("BFS should find a path from 1 to 5", graph.hasPathBFS(1, 5));
        assertTrue("DFS should find a path from 1 to 5", graph.hasPathBFSRecursive(1, 5));
    }

    @Test
    public void testPathDoesNotExistDFSAndBFS() {
        /*
         Graph structure:
         1 -> 2
         3 -> 4
        */
        addNodes(1, 2, 3, 4);
        addEdges(new int[][]{
            {1, 2},
            {3, 4}
        });

        assertFalse("DFS should not find a path from 1 to 4", graph.hasPathDFS(1, 4));
        assertFalse("DFS should not find a path from 1 to 4", graph.hasPathDFSIterative(1, 4));
        assertFalse("BFS should not find a path from 1 to 4", graph.hasPathBFS(1, 4));
        assertFalse("BFS should not find a path from 1 to 4", graph.hasPathBFSRecursive(1, 4));
    }

    @Test
    public void testSameSourceAndDestinationDFSAndBFS() {
        addNodes(1, 2, 3);

        // Even if there are no edges, path from node to itself should exist
        assertTrue("DFS should find a path from node to itself", graph.hasPathDFS(1, 1));
        assertTrue("DFS should find a path from node to itself", graph.hasPathDFSIterative(1, 1));
        assertTrue("BFS should find a path from node to itself", graph.hasPathBFS(1, 1));
        assertTrue("BFS should find a path from node to itself", graph.hasPathBFSRecursive(1, 1));
    }

    @Test
    public void testGraphWithCycleDFSAndBFS() {
        /*
         Graph structure:
         1 -> 2 -> 3
         3 -> 1 (cycle)
         3 -> 4
        */
        addNodes(1, 2, 3, 4);
        addEdges(new int[][]{
            {1, 2},
            {2, 3},
            {3, 1},
            {3, 4}
        });

        assertTrue("DFS should find a path from 1 to 4 even with a cycle", graph.hasPathDFS(1, 4));
        assertTrue("DFS should find a path from 1 to 4 even with a cycle", graph.hasPathDFSIterative(1, 4));
        assertTrue("BFS should find a path from 1 to 4 even with a cycle", graph.hasPathBFS(1, 4));
        assertTrue("BFS should find a path from 1 to 4 even with a cycle", graph.hasPathBFSRecursive(1, 4));
    }

    @Test
    public void testMultiplePathsDFSAndBFS() {
        /*
         Graph structure:
         1 -> 2 -> 4
         1 -> 3 -> 4
         1 -> 4
        */
        addNodes(1, 2, 3, 4);
        addEdges(new int[][]{
            {1, 2},
            {2, 4},
            {1, 3},
            {3, 4},
            {1, 4}
        });

        assertTrue("DFS should find a path from 1 to 4 with multiple paths", graph.hasPathDFS(1, 4));
        assertTrue("DFS should find a path from 1 to 4 with multiple paths", graph.hasPathDFSIterative(1, 4));
        assertTrue("BFS should find a path from 1 to 4 with multiple paths", graph.hasPathBFS(1, 4));
        assertTrue("BFS should find a path from 1 to 4 with multiple paths", graph.hasPathBFSRecursive(1, 4));
    }

    @Test
    public void testEmptyGraphDFSAndBFS() {
        // No nodes added
        assertFalse("DFS should not find a path in an empty graph", graph.hasPathDFS(1, 2));
        assertFalse("DFS should not find a path in an empty graph", graph.hasPathDFSIterative(1, 2));
        assertFalse("BFS should not find a path in an empty graph", graph.hasPathBFS(1, 2));
        assertFalse("BFS should not find a path in an empty graph", graph.hasPathBFSRecursive(1, 2));
    }

    @Test
    public void testNonExistentSourceOrDestinationDFSAndBFS() {
        addNodes(1, 2, 3);

        // Destination node does not exist
        assertFalse("DFS should return false if destination node does not exist", graph.hasPathDFS(1, 4));
        assertFalse("DFS should return false if destination node does not exist", graph.hasPathDFSIterative(1, 4));
        assertFalse("BFS should return false if destination node does not exist", graph.hasPathBFS(1, 4));
        assertFalse("BFS should return false if destination node does not exist", graph.hasPathBFSRecursive(1, 4));

        // Source node does not exist
        assertFalse("DFS should return false if source node does not exist", graph.hasPathDFS(5, 2));
        assertFalse("DFS should return false if source node does not exist", graph.hasPathDFSIterative(5, 2));
        assertFalse("BFS should return false if source node does not exist", graph.hasPathBFS(5, 2));
        assertFalse("BFS should return false if source node does not exist", graph.hasPathBFSRecursive(5, 2));

        // Both nodes do not exist
        assertFalse("DFS should return false if both nodes do not exist", graph.hasPathDFS(6, 7));
        assertFalse("DFS should return false if both nodes do not exist", graph.hasPathDFSIterative(6, 7));
        assertFalse("BFS should return false if both nodes do not exist", graph.hasPathBFS(6, 7));
        assertFalse("BFS should return false if both nodes do not exist", graph.hasPathBFSRecursive(6, 7));
    }

    @Test
    public void testDirectedGraphDFSAndBFS() {
        /*
         Graph structure (Directed):
         1 -> 2
         2 -> 3
         3 -> 4
         4 -> 2 (cycle)
        */
        addNodes(1, 2, 3, 4);
        addEdges(new int[][]{
            {1, 2},
            {2, 3},
            {3, 4},
            {4, 2}
        });

        assertTrue("DFS should find a path from 1 to 4 in a directed graph", graph.hasPathDFS(1, 4));
        assertTrue("DFS should find a path from 1 to 4 in a directed graph", graph.hasPathDFSIterative(1, 4));
        assertTrue("BFS should find a path from 1 to 4 in a directed graph", graph.hasPathBFS(1, 4));
        assertTrue("BFS should find a path from 1 to 4 in a directed graph", graph.hasPathBFSRecursive(1, 4));

        // Reverse path does not exist
        assertFalse("DFS should not find a path from 4 to 1 in a directed graph", graph.hasPathDFS(4, 1));
        assertFalse("DFS should not find a path from 4 to 1 in a directed graph", graph.hasPathDFSIterative(4, 1));
        assertFalse("BFS should not find a path from 4 to 1 in a directed graph", graph.hasPathBFS(4, 1));
        assertFalse("BFS should not find a path from 4 to 1 in a directed graph", graph.hasPathBFSRecursive(4, 1));
    }

    @Test
    public void testUndirectedGraphDFSAndBFS() {
        /*
         To test as undirected, we'll add edges in both directions.
         Graph structure:
         1 -- 2 -- 3
         1 -- 3
        */
        addNodes(1, 2, 3);
        addEdges(new int[][]{
            {1, 2},
            {2, 1},
            {2, 3},
            {3, 2},
            {1, 3},
            {3, 1}
        });

        assertTrue("DFS should find a path from 1 to 3 in an undirected graph", graph.hasPathDFS(1, 3));
        assertTrue("DFS should find a path from 1 to 3 in an undirected graph", graph.hasPathDFSIterative(1, 3));
        assertTrue("BFS should find a path from 1 to 3 in an undirected graph", graph.hasPathBFS(1, 3));
        assertTrue("BFS should find a path from 1 to 3 in an undirected graph", graph.hasPathBFSRecursive(1, 3));

        assertTrue("DFS should find a path from 3 to 1 in an undirected graph", graph.hasPathDFS(3, 1));
        assertTrue("DFS should find a path from 3 to 1 in an undirected graph", graph.hasPathDFSIterative(3, 1));
        assertTrue("BFS should find a path from 3 to 1 in an undirected graph", graph.hasPathBFS(3, 1));
        assertTrue("BFS should find a path from 3 to 1 in an undirected graph", graph.hasPathBFSRecursive(3, 1));
    }
}
