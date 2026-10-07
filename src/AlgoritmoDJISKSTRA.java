
public class AlgoritmoDJISKSTRA 
{
    private int[] percurso;
    private int cont;
    
    private Visualizacao vis;
    private Sort s;

    AlgoritmoDJISKSTRA()
    {
        vis = new Visualizacao();
        s = new Sort();
    }
    public void dijkstra(Grafo g, int inicio, int fim)
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

        custo = dijkstra_Explore(g, selecionado, custo, via, inicio, fim);
        vis.visualizarPercurso(percurso);
        vis.visualizarCusto(custo);
    }

    int[] dijkstra_Explore(Grafo g, boolean[] selecionado, int[] custo, int[] via, int escolhido, int fim)
    {
        int total=0;
        while(cont < g.getNumVertices())
        {
            selecionado[escolhido]=true;

            Vertice[] vizinhos = g.getVizinhosDe(g.getV()[escolhido]);
            for(Vertice i : vizinhos)
            {
                int posVizinho = i.getPosicao();
                int pesoI = total + g.getAresta(escolhido, i.getPosicao()).getPeso();

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
            cont++;
        }

        for (int i = 0; i < g.getNumVertices(); i++)
			{
				if (i == escolhido) continue; // nao imprime a origem para ela propria
				else if (i == fim)
				{
					System.out.print("Vertice " + escolhido + " -- Vertice " + i + " : ");
					if (custo[i] == -1)
					{
						System.out.println("inalcancavel");
					}
					else
					{
						System.out.println("distancia = " + custo[i] + " (via Vertice " + via[i] + ")");
					}
				}	
			}

        return custo;
    }

    	public void caminhosMaisCurtos(int[][] matriz, int origem, int fim)
		{
            int numVertices = matriz.length;

			boolean[] visitado = new boolean[numVertices];        // marca que vertices ja tem distancia final fechada
			int[] distancia = new int[numVertices];               // menor distancia conhecida desde a origem ate cada vertice
			int[] verticeAnterior = new int[numVertices];         // de que vertice viria a aresta ate cada vertice (para reconstruir o caminho)

			for (int i = 0; i < numVertices; i++)
			{
				distancia[i] = Integer.MAX_VALUE; // comeca tudo a "infinito" (ainda nao sabemos como la chegar)
				verticeAnterior[i] = -1;
			}
			distancia[origem] = 0; // a origem tem distancia 0 a ela propria, e o ponto de partida

			for (int cont = 0; cont < numVertices; cont++) // repete uma vez por vertice a fechar
			{
				int u = -1;
				int menor = Integer.MAX_VALUE;

				// procura, entre os vertices FORA da arvore, aquele com menor distancia acumulada
				for (int i = 0; i < numVertices; i++)
				{
					if (!visitado[i] && distancia[i] < menor)
					{
						menor = distancia[i];
						u = i;
					}
				}

				if (u == -1) break; // os restantes vertices sao inalcancaveis a partir da origem

				visitado[u] = true; // esse vertice fica com a distancia definitivamente fechada

				// "relaxamento": depois de u fechado, verifica se passar por u encurta o caminho
				// ate cada vizinho v ainda fora da arvore (diferenca-chave face ao Prim: aqui soma-se
				// a distancia acumulada, nao se compara so o peso da aresta isolada)
				for (int v = 0; v < numVertices; v++)
				{
					if (matriz[u][v] != 0 && !visitado[v] && distancia[u] != Integer.MAX_VALUE)
					{
						int novaDistancia = distancia[u] + matriz[u][v];
						if (novaDistancia < distancia[v])
						{
							distancia[v] = novaDistancia;
							verticeAnterior[v] = u;
						}
					}
				}
			}

			for (int i = 0; i < numVertices; i++)
			{
				if (i == origem) continue; // nao imprime a origem para ela propria
				else if (i == fim)
				{
					System.out.print("Vertice " + origem + " -- Vertice " + i + " : ");
					if (distancia[i] == Integer.MAX_VALUE)
					{
						System.out.println("inalcancavel");
					}
					else
					{
						System.out.println("distancia = " + distancia[i] + " (via Vertice " + verticeAnterior[i] + ")");
					}
				}	
			}
		}

}
