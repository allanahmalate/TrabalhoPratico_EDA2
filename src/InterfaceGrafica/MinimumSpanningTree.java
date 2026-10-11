import java.util.*;

public class MinimumSpanningTree {
    private final Graph graph;
    private final String[] cities = {
            "ZHYTOMYR","VINNYTSA","KYIV","KROPYNYTSKYI","ODESSA",
            "CHERNIHIV","SUMMY","POLTAVA","KHARKIV","DNIPRO",
            "DONETSK","LUHANSK","MYKOLAIV","KHERSON","ZAPORIZHZHYA"
    };

    public MinimumSpanningTree(Graph graph) {
        this.graph = graph;
    }

    public List<Edge> getMSTKruskal() {
        List<Kruskal.Aresta> kruskal = Kruskal.getMST();
        return convertToEdges(kruskal);
    }

    public List<Edge> getMSTPrim() {
        List<Prim.Aresta> prim = Prim.getMST();
        return convertToEdges(prim);
    }

    public int getCost(List<Edge> mst) {
        return mst.stream().mapToInt(Edge::getWeight).sum();
    }

    private List<Edge> convertToEdges(List<?> mstList) {
        List<Edge> edges = new ArrayList<>();
        for (Object a : mstList) {
            int origem = 0, destino = 0;
            if (a instanceof Kruskal.Aresta) {
                Kruskal.Aresta k = (Kruskal.Aresta) a;
                origem = k.origem; 
                destino = k.destino;
            } else if (a instanceof Prim.Aresta) {
                Prim.Aresta p = (Prim.Aresta) a;
                origem = p.origem; 
                destino = p.destino;
            } else continue;

            Edge e = graph.getEdge(cities[origem], cities[destino]);
            if (e != null) edges.add(e);
        }
        return edges;
    }

}