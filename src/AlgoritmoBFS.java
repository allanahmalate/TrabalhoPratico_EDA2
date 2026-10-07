import java.util.*;

public class AlgoritmoBFS 
{
    private int percurso[];
    private int cont;
    private Visualizacao vis;
    AlgoritmoBFS()
    { vis = new Visualizacao(); }

    void bfs_Transversal(Grafo g, int inicio) 
    {
        Queue<Integer> fila = new LinkedList<>();
        boolean[] visitado = new boolean[g.getNumVertices()];

        percurso = new int[g.getNumVertices()];
        cont = 0;

        for(int v=0; v < g.getV().length; v++)
            visitado[v] = false; 
        bfsExplore(g, inicio, fila, visitado);
        vis.visualizarPercurso(percurso);
    }

    void bfsExplore(Grafo g, int inicio, Queue fila, boolean[] visitado) 
    {
        visitado[inicio] = true;
        fila.add(inicio);

        while (!fila.isEmpty()) 
        {
            int v = (Integer) fila.poll();
            percurso[cont] = v;
            
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
            cont++;
        }
    }

}
