import java.util.Stack;

public class AlgoritmoDFS 
{
    private int percurso[];
    private int cont;
    private Visualizacao vis;
    AlgoritmoDFS()
    { vis = new Visualizacao(); }

    
    void dfs_Transversal(Grafo g, int inicio) 
    {
        Stack pilha = new Stack<Integer>();
        boolean[] explored = new boolean[g.getNumVertices()];
        percurso = new int[g.getNumVertices()];
        cont = 0;
        for(int v=0; v < g.getV().length; v++)
            explored[v] = false; 
        dfs_Explore(g, inicio, pilha, explored);
        vis.visualizarPercurso(percurso);
    }

    void dfs_Explore(Grafo g, int v, Stack pilha, boolean[] explored) 
    {
        explored[v] = true;

        Vertice[] vizinhos = g.getVizinhosDe(g.getV()[v]);
        for (int j = 0; j < vizinhos.length; j++) 
        {   pilha.push(vizinhos[j].getPosicao());  }
        pilha = dfs_eliminarTratados(pilha, explored);
    
        if(!pilha.isEmpty())
        {
            int next = (Integer) pilha.pop();
            cont++;
            dfs_Explore(g,next, pilha, explored);  
        }
    }

    Stack dfs_eliminarTratados(Stack pilha, boolean[] explored)
    {
        Stack aux = new Stack<Integer>();

        while (!pilha.empty()) 
        {
            int elemento = (Integer) pilha.pop();

            if(!explored[elemento])
                aux.push(elemento);
        }

        while (!aux.empty()) 
        {
            pilha.push(aux.pop());
        }
        return pilha;
    }

}
