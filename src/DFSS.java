import java.util.*;

public class DFSS 
{
    private boolean[] visitado;

    private Grafo g;
    private Validacoes val;

    public DFSS() 
    {
        this.visitado = new boolean[21];
        val = new Validacoes();
    }

    public void executar(Grafo g) 
    {
        this.g = g;
        int inicio = val.escolherVertice("Por que cidade deseja iniciar?", g.getV());

        // Reiniciar os vértices visitados
        visitado = new boolean[g.getNumVertices()];

        System.out.print("\n===== DFS - BUSCA EM PROFUNDIDADE =====\n");

        dfs(inicio);
        System.out.println(" ");
    }

    private void dfs(int vertice) {

        // Marcar o vértice como visitado
        visitado[vertice] = true;

        System.out.print(vertice + " ");

        // Percorrer os vizinhos
        //for (String aresta : lista.getLista().get(vertice)) {
        Vertice[] dados = g.getVizinhosDe(g.getV()[vertice]);
        for (int i = 0; i < dados.length; i++) {

            //String[] dados = aresta.split(":");

            int destino = dados[i].getPosicao(); // Converter para inteiro

            // Se ainda não foi visitado
            if (!visitado[destino]) {

                dfs(destino);
            }
        }
    }
}