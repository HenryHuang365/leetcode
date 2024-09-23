public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();

        // Adding nodes to the graph
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);

        // Attempting to add a duplicate node
        graph.addNode(3); // Should indicate that node 3 already exists

        // Adding edges between nodes
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        // graph.addEdge(5, 1); // Creating a cycle
        graph.addEdge(6, 1); // Attempting to add an edge with a non-existent source node

        // Displaying the graph's adjacency list
        System.out.println("\nAdjacency List of the Graph:");
        graph.displayGraph();

        System.out.println("It is " + graph.hasPathDFS(1, 2) + " that Node 1 has a path to Node 2.");
        System.out.println("It is " + graph.hasPathDFS(5, 3) + " that Node 5 has a path to Node 3.");

        System.out.println("It is " + graph.hasPathBFS(1, 2) + " that Node 1 has a path to Node 2.");
        System.out.println("It is " + graph.hasPathBFS(5, 3) + " that Node 5 has a path to Node 3.");
    }
}
