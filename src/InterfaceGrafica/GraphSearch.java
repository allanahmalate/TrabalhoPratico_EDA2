import java.util.*;

public class GraphSearch {
    public static List<Node> bfs(Graph g, int startIdx) {
        List<Node> order = new ArrayList<>();
        boolean[] visited = new boolean[g.getNodes().size()];
        Queue<Integer> q = new LinkedList<>();
        q.add(startIdx);
        visited[startIdx] = true;

        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(g.getNodes().get(u));
            for (Edge e : g.getEdgesOf(u)) {
                int v = g.getNodeIndex(e.getOtherNode(g.getNodes().get(u)).getName());
                if (!visited[v]) {
                    visited[v] = true;
                    q.add(v);
                }
            }
        }
        return order;
    }

    public static List<Node> dfs(Graph g, int startIdx) {
        List<Node> order = new ArrayList<>();
        boolean[] visited = new boolean[g.getNodes().size()];
        dfsHelper(g, startIdx, visited, order);
        return order;
    }

    private static void dfsHelper(Graph g, int u, boolean[] visited, List<Node> order) {
        visited[u] = true;
        order.add(g.getNodes().get(u));
        for (Edge e : g.getEdgesOf(u)) {
            int v = g.getNodeIndex(e.getOtherNode(g.getNodes().get(u)).getName());
            if (!visited[v]) {
                dfsHelper(g, v, visited, order);
            }
        }
    }
}