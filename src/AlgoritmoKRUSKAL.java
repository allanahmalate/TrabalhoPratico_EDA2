import java.util.*;

public class AlgoritmoKRUSKAL
{
    private Visualizacao vis;
    AlgoritmoKRUSKAL()
    {
        vis = new Visualizacao();
    }

    public void kruskal(Grafo g)
    {
        int totalCusto=0;
        Grafo novoG;
        Sort s = new Sort();
        DeteccaoCiclos ds = new DeteccaoCiclos(g.getNumVertices());
        ArrayList<Aresta> organizado = g.getA();
        
        s.bubbleSort(organizado); //organizar arestas em ordem cescente de custo numa ArrayList Auxiliar
        
        novoG = new Grafo(g.getV(), new ArrayList<Aresta>()); //criar uma floresta de avores parcciais, sem arestas ou particoes
        
        for(Aresta aresta : organizado)
        {
            if(ds.union(aresta.getOrigem(), aresta.getDestino())) //verificar se existira ciclo
            {
                novoG.getA().add(aresta); //ligar as arvores
                totalCusto += aresta.getPeso();
            }    

            //Uma AMC sempre deve conter (V-1) arestas
            if(novoG.getA().size() == novoG.getNumVertices()-1)
                break;
        }

        Lista lista = new Lista();
        lista.visualizarLista(lista.criarLista(novoG), novoG);
        vis.visualizarCusto(totalCusto);
    }

}