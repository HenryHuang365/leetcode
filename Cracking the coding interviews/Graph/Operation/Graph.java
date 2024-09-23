import java.util.*;

/**
 * Graph
 */
public class Graph {
    private HashMap<Integer, Node> nodeLookup = new HashMap<>();

    public class Node {
        int id;
        LinkedList<Node> adjacent = new LinkedList<Node>();

        private Node(int id) {
            this.id = id;
        }

    }

    public Node getNode(int id) {
        return nodeLookup.get(id);
    }

    public void addNode(int id) {
        if (nodeLookup.containsKey(id)) {
            return;
        }
        nodeLookup.put(id, new Node(id));
    }

    // add edge in a directed graph
    public void addEdge(int source, int destination) {
        Node s = getNode(source);
        Node d = getNode(destination);
        if (s == null) {
            System.out.println("Source node " + source + " does not exist.");
            return;
        }
        if (d == null) {
            System.out.println("Destination node " + destination + " does not exist.");
            return;
        }
        s.adjacent.add(d);
        // d.adjacent.add(s); // If this is an undirected graph, add this line.
    }

    public void displayGraph() {
        for (Integer id : nodeLookup.keySet()) {
            Node node = getNode(id);
            System.out.print("Node " + id + " is connected to: ");
            for (Node adj : node.adjacent) {
                System.out.print(adj.id + " ");
            }
            System.out.println();
        }
    }

    public boolean hasPathDFS(int source, int destination) {
        Node s = getNode(source);
        Node d = getNode(destination);
        if (s == null || d == null) {
            return false;
        }
        HashSet<Integer> visted = new HashSet<>();
        return hasPathDFS(s, d, visted);
    }

    public boolean hasPathDFS(Node source, Node destination, HashSet<Integer> visted) {
        if (visted.contains(source.id)) {
            return false;
        }
        visted.add(source.id);
        if (source.id == destination.id) {
            return true;
        }

        for (Node child : source.adjacent) {
            if (hasPathDFS(child, destination, visted)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPathBFS(int source, int destination) {
        Node s = getNode(source);
        Node d = getNode(destination);
        if (s == null || d == null) {
            return false;
        }
        return hasPathBFS(s, d);
    }

    public boolean hasPathBFS(Node source, Node destination) {
        LinkedList<Node> nextToVisit = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();
        nextToVisit.add(source);

        while (!nextToVisit.isEmpty()) {
            Node node = nextToVisit.remove();
            if (node == destination) {
                return true;
            }

            if (visited.contains(node.id)) {
                continue;
            }

            visited.add(node.id);

            for (Node child : node.adjacent) {
                nextToVisit.add(child);
            }
        }

        return false;
    }

}
