import java.util.*;

/**
 * Graph
 */
public class Graph {
    HashMap<Integer, Node> nodeLookUp = new HashMap<>();

    public class Node {
        int id;
        LinkedList<Node> adjacent = new LinkedList<Node>();

        private Node(int id) {
            this.id = id;
        }
    }

    public Node getNode(int id) {
        return nodeLookUp.get(id);
    }

    public void addNode(int id) {
        nodeLookUp.put(id, new Node(id));
    }

    public void addEdge(int source, int destination) {
        Node s = getNode(source);
        Node d = getNode(destination);
        if (s == null) {
            return;
        }
        if (d == null) {
            return;
        }
        s.adjacent.add(d);
        // d.adjacent.add(s); // If this is a undirected graph
    }
    
    public void displayGraph() {
        for (Node node : nodeLookUp.values()) {
            System.out.print("Node " + node.id + " has adjacent nodes: ");
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
        HashSet<Integer> visited = new HashSet<>();
        return hasPathDFS(s, d, visited);
    }

    public boolean hasPathDFS(Node source, Node destination, HashSet<Integer> visited) {
        if (visited.contains(source.id)) {
            return false;
        }
        
        if (source.id == destination.id) {
            return true;
        }

        visited.add(source.id);

        for (Node child : source.adjacent) {
            if (hasPathDFS(child, destination, visited)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasPathDFSIterative(int source, int destination) {
        Node s = getNode(source);
        Node d = getNode(destination);
        if (s == null || d == null) {
            return false;
        }

        HashSet<Integer> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        stack.push(s);

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            if (node.id == destination) {
                return true;
            }

            if (visited.contains(node.id)) {
                continue;
            }

            visited.add(node.id);

            for (Node child : node.adjacent) {
                stack.push(child);
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
        HashSet<Integer> visited = new HashSet<>();
        return hasPathBFS(s, d, visited);
    }

    public boolean hasPathBFS(Node source, Node destination, HashSet<Integer> visited) {
        LinkedList<Node> nextToVisit = new LinkedList<Node>();
        nextToVisit.add(source);

        while (!nextToVisit.isEmpty()) {
            Node node = nextToVisit.pop();
            if (visited.contains(node.id)) {
                continue;
            }
            if (node.id == destination.id) {
                return true;
            }
            visited.add(node.id);

            for (Node child : node.adjacent) {
                nextToVisit.add(child);
            }
        }

        return false;
    }

    public boolean hasPathBFSRecursive(int source, int destination) {
        Node s = getNode(source);
        Node d = getNode(destination);
        if (s == null || d == null) {
            return false;
        }

        HashSet<Integer> visited = new HashSet<>();
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(s);
        return hasPathBFSRecursive(queue, d, visited);
    }

    private boolean hasPathBFSRecursive(LinkedList<Node> queue, Node destination, HashSet<Integer> visited) {
        if (queue.isEmpty()) {
            return false;
        }

        Node node = queue.removeFirst();
        if (node.id == destination.id) {
            return true;
        }

        if (visited.contains(node.id)) {
            return hasPathBFSRecursive(queue, destination, visited);
        }

        visited.add(node.id);

        for (Node child : node.adjacent) {
            queue.addLast(child);
        }

        return hasPathBFSRecursive(queue, destination, visited);
    }
}
