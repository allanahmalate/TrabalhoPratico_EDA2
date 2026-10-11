import java.util.*;

public class Graph {
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private Map<String, Integer> nameToIndex = new HashMap<>();

    public void addNode(String name, double x, double y) {
        Node n = new Node(name, x, y);
        nodes.add(n);
        nameToIndex.put(name, nodes.size() - 1);
    }

    public void addEdge(String from, String to, int weight) {
        Node f = nodes.get(nameToIndex.get(from));
        Node t = nodes.get(nameToIndex.get(to));
        edges.add(new Edge(f, t, weight));
    }

    public List<Node> getNodes() { return nodes; }
    public List<Edge> getEdges() { return edges; }
    public int getNodeIndex(String name) { return nameToIndex.getOrDefault(name, -1); }

    public Edge getEdge(String a, String b) {
        for (Edge e : edges) {
            if ((e.getFrom().getName().equals(a) && e.getTo().getName().equals(b)) ||
                    (e.getFrom().getName().equals(b) && e.getTo().getName().equals(a))) {
                return e;
            }
        }
        return null;
    }

    public List<Edge> getEdgesOf(int idx) {
        List<Edge> list = new ArrayList<>();
        Node n = nodes.get(idx);
        for (Edge e : edges) {
            if (e.getFrom().equals(n) || e.getTo().equals(n)) {
                list.add(e);
            }
        }
        return list;
    }

    public int[][] getAdjacencyMatrix() {
        int n = nodes.size();
        int[][] m = new int[n][n];
        for (Edge e : edges) {
            int i = nameToIndex.get(e.getFrom().getName());
            int j = nameToIndex.get(e.getTo().getName());
            m[i][j] = m[j][i] = e.getWeight();
        }
        return m;
    }
}