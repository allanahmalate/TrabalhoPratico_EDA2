import java.util.*;

public class ShortestPath {
    public static List<Node> getShortestPathByIndex(Graph g, int start, int end) {
        int n = g.getNodes().size();
        int[] dist = new int[n];
        int[] prev = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            if (curr[1] > dist[u]) continue;

            for (Edge e : g.getEdgesOf(u)) {
                int v = g.getNodeIndex(e.getOtherNode(g.getNodes().get(u)).getName());
                int weight = e.getWeight();
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    prev[v] = u;
                    pq.add(new int[]{v, dist[v]});
                }
            }
        }

        List<Node> path = new ArrayList<>();
        for (int at = end; at != -1; at = prev[at]) {
            path.add(g.getNodes().get(at));
        }
        Collections.reverse(path);
        return path.get(0).equals(g.getNodes().get(start)) ? path : new ArrayList<>();
    }
}