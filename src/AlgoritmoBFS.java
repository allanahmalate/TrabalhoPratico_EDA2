import java.util.*;

public class AlgoritmoBFS 
{
    private Visualizacao vis;
    private Validacoes val;

    AlgoritmoBFS()
    { 
        vis = new Visualizacao(); 
        val = new Validacoes();
    }

    public void executarBFS(Grafo g) 
    {
        int inicio = val.escolherVertice("Introduza o inicio do caminho(0-" + (g.getNumVertices()-1) + "): ", g.getV());
        ArrayList<Integer> percurso = bfs_Transversal(g, inicio);
        vis.visualizarLista(percurso, g);
    }

    ArrayList<Integer> bfs_Transversal(Grafo g, int inicio) 
    {
        ArrayList<Integer> ordem = new ArrayList<>();
        Queue<Integer> fila = new LinkedList<>();
        boolean[] visitado = new boolean[g.getNumVertices()];

        for(int v=0; v < g.getV().length; v++)
            visitado[v] = false; 

        return bfsExplore(g, inicio, ordem, fila, visitado);
    }

    ArrayList<Integer> bfsExplore(Grafo g, int inicio, ArrayList<Integer> ordem, Queue<Integer> fila, boolean[] visitado) 
    {
        visitado[inicio] = true;
        fila.add(inicio);

        while (!fila.isEmpty()) 
        {
            int v = (Integer) fila.poll();
            ordem.add(v);

            Vertice[] vizinhos = g.getVizinhosDe(g.getV()[v]);
            for (int j = 0; j < vizinhos.length; j++)
            {
                int i = vizinhos[j].getPosicao();
                if (!visitado[i]) 
                {
                    visitado[i] = true;
                    fila.add(i);
                }
            }
        }

        return ordem;
    }

}
