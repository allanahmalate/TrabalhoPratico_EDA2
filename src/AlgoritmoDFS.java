import java.util.*;

public class AlgoritmoDFS 
{
    private Visualizacao vis;
    private Validacoes val;
    AlgoritmoDFS()
    { 
        vis = new Visualizacao(); 
        val = new Validacoes();
    }

    public void executarDFS(Grafo g) 
    {
        int inicio = val.escolherVertice("Introduza o inicio do caminho(0-" + (g.getNumVertices()-1) + "): ", g.getV());
        ArrayList<Integer> percurso = dfs_Transversal(g, inicio);
        vis.visualizarLista(percurso, g);
    }

    public ArrayList<Integer> dfs_Transversal(Grafo g, int inicio) {
        ArrayList<Integer> percurso = new ArrayList<>();
        boolean[] visitado = new boolean[g.getV().length];
        dfs_Explore(g, inicio, visitado, percurso);
        return percurso;
    }

    private void dfs_Explore(Grafo g, int u, boolean[] visitado, ArrayList<Integer> percurso) {
        visitado[u] = true;
        percurso.add(u);
        for (Vertice e : g.getVizinhosDe(g.getV()[u])) {
            int v = g.getAresta(u, e.getPosicao()).getOutroVertice(u);
            if (!visitado[v]) {
                dfs_Explore(g, v, visitado, percurso);
            }
        }
    }
}
