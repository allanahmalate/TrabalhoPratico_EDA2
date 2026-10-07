public class AlgoritmoPRIM 
{   
    private int percurso[];
    private int cont;
    private Visualizacao vis;
    private Sort s;

    AlgoritmoPRIM() 
    { 
        vis = new Visualizacao(); 
        s = new Sort();
    }

    void prim_Transversal(Grafo g, int inicio)
    {
        boolean[] selecionado = new boolean[g.getNumVertices()];
        int custo[] = new int[g.getNumVertices()];
        int via[] = new int[g.getNumVertices()];

        percurso = new int[g.getNumVertices()];
        cont = 0;

        for(int v=0; v < g.getV().length; v++)
        {
            selecionado[v] = false;
            custo[v] = -1;
            via[v] = -1;
        }     

        selecionado[inicio] = true;
        custo[inicio] = 0;
        via[inicio] = -1;

        custo = prim_Explore(g, selecionado, custo, via, inicio);
        vis.visualizarPercurso(percurso);
        vis.visualizarCusto(custo);
    }

    int[] prim_Explore(Grafo g, boolean[] selecionado, int[] custo, int[] via, int escolhido)
    {
        while(cont < g.getNumVertices())
        {
            selecionado[escolhido]=true;

            Vertice[] vizinhos = g.getVizinhosDe(g.getV()[escolhido]);
            for(Vertice i : vizinhos)
            {
                int posVizinho = i.getPosicao();
                int pesoI = g.getAresta(escolhido, i.getPosicao()).getPeso();

                if(selecionado[posVizinho] == false && (custo[posVizinho] == -1 || pesoI < custo[posVizinho]))
                {
                    custo[posVizinho] = pesoI;
                    via[posVizinho] = escolhido;
                }
            }

            //tabela(g, selecionado, custo, via, escolhido);

            percurso[cont] = escolhido;
            escolhido = s.acharMenor(custo, selecionado);
            //visualizarMenor(escolhido);

            if(escolhido == -1)
            {
                break;
            }
            else
            {
                ///ultimo = escolhido;
            }
            cont++;
        }

        vis.visualizarPercurso(via);

        return custo;
    }
}