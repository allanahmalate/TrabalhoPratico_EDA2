import java.util.*;

public class Menu 
{
    private int op;
    private Grafo g;
	private Validacoes val;

    public Menu() 
    {
        g = criarGrafoMocambique();
        criarArestasMocambique();
        val = new Validacoes();
    }
    
    Grafo criarGrafoMocambique()
    {
        Vertice v[] = 
        {
            new Vertice(0, "Pemba"),
            new Vertice(1, "Lichinga"),
            new Vertice(2, "Tete"),
            new Vertice(3, "Chimoio"),
            new Vertice(4, "Beira"),
            new Vertice(5, "Quelimane"),
            new Vertice(6, "Nampula")
        };
            
        return new Grafo(v, new ArrayList<Aresta>()); 
    }

    void criarArestasMocambique()
    {
        g.addAresta(0, 1, 5, true);
        g.addAresta(0, 6, 3, true);
        g.addAresta(1,2,1, true);
        g.addAresta(1,6,8, true);
        g.addAresta(2,3,9, true);
        g.addAresta(2,5,22, true);
        g.addAresta(3,4,14, true);
        g.addAresta(3,5,23, true);
        g.addAresta(4,5,15, true);
        g.addAresta(5,6,16, true);
    }

    Grafo criarGrafoRussia()
    {
        Vertice v[] = 
        {
            new Vertice(0, "Kyiv"),
           
            new Vertice(1, "Chernihiv"),
            new Vertice(2, "Sumy"),
            new Vertice(3, "Zhytomyr"),
            new Vertice(4, "Poltava"),
            new Vertice(5, "Kharkiv"),

            new Vertice(6, "Luhansk"),
            new Vertice(7, "Dnipro"),
            new Vertice(8, "Donetsk"),
            new Vertice(9, "Zaporizhzhya"),
            new Vertice(10, "Kropyvnytskyi"),

            new Vertice(11, "Mykolaiv"),
            new Vertice(12, "Kherson"),
            new Vertice(13, "Odessa"),
            new Vertice(14, "Vinnytsya"),
            new Vertice(15, "Crimea")

            //new Vertice(15, "A");
            //new Vertice(16, "B");
            //new Vertice(17, "C");
            //new Vertice(18, "D");
            //new Vertice(19, "E");
        };

        ArrayList<Aresta> a = new ArrayList<Aresta>();

        return new Grafo(v,a);
    }

    public void menu()
    {
        final int MAX = 8;
        
        do
        {
            System.out.println("   *** MENU ***  ");
            System.out.println("1. Criar Novo Grafo");
            System.out.println("2. Visualizar o Grafo");
            System.out.println("3. Percorrer o Grafo");
            System.out.println("4. Arvore de Cobertura Minima");
            System.out.println("5. Caminho mais curto");
            System.out.println("6. Alterar Grafo (NAO FEITO!)");
            System.out.println("7. Pesquisar Existencia de Aresta");
            System.out.println("8. Sair do programa");
            System.out.println("NOTA: Grafo pre-existente!");
            
            op = val.validarInt("Introduza a opcao(1-" + MAX  + "): ", 1, MAX );

            System.out.println();
            switch(op)
            {
                case 1:   
                    int n = val.validarInt("Introduza o numero de vertices do novo grafo: ");
                    g = new Grafo(n); 
                    break;
                case 2: menu2(); break;
                case 3: menu3(); break;
                case 4: menu4(); break;
                case 5: menu5(); break;
                case 6: menu6(); break;
                case 7: opcao7(); break;
                default: System.out.println("Volte Sempre!");
            }
        } while(op!=MAX );
    }

    public void menu2()
    {
        final int MAX  = 3;
        do
        {
            System.out.println("   *** MENU ***  ");
            System.out.println("1. Matriz de Adjacencia");
            System.out.println("2. Lista de Adjacencia");
            System.out.println("3. Voltar ao Menu Principal");
            
            op = val.validarInt("Introduza a opcao(1-" + MAX  + "): ", 1, MAX );

            switch(op)
            {
                case 1: 
                    System.out.println("----Matriz de Adjacencia----");  
                    Matriz matriz = new Matriz();
                    matriz.visualMatriz(matriz.criarMatriz(g)); 
                    break;
                case 2: 
                    System.out.println("----Lista de Adjacencia----");  
                    Lista lista = new Lista();
                    lista.visualizarLista(lista.criarLista(g),g); break;
                case 3: menu(); break;
            }
        } while(op!=MAX );
    }

    public void menu3()
    {
        final int MAX  = 3;
        do
        {
            System.out.println("   *** MENU ***  ");
            System.out.println("1. Percurso por Profundidade (DFS)");
            System.out.println("2. Percurso por Largura (BFS)");
            System.out.println("3. Voltar ao Menu Principal");
            
            op = val.validarInt("Introduza a opcao(1-" + MAX  + "): ", 1, MAX );

            switch(op)
            {
                case 1: 
                    System.out.println("----DFS----"); 
                    AlgoritmoDFS dfs = new AlgoritmoDFS();
                    
                    DFSS df = new DFSS();
                    df.executar(g);
                    dfs.dfs_Transversal(g, 0); 
                    break;
                case 2: 
                    System.out.println("----BFS----"); 
                    AlgoritmoBFS bfs = new AlgoritmoBFS();
                    bfs.bfs_Transversal(g, 0); 
                    break;
                case 3: menu(); break;
            }
        } while(op!=MAX );
    }
    
    public void menu4()
    {
        final int MAX  = 3;
        do
        {
            System.out.println("   *** MENU ***  ");
            System.out.println("1. Algoritmo de Prim");
            System.out.println("2. Algoritmo de Kuskal");
            System.out.println("3. Voltar ao Menu Principal");
            
            op = val.validarInt("Introduza a opcao(1-" + MAX  + "): ", 1, MAX );
   
            switch(op)
            {
                case 1: 
                    System.out.println("----Algoritmo de Prim----");  
                    AlgoritmoPRIM algp = new AlgoritmoPRIM();
                    algp.prim_Transversal(g, 0); 
                    break;
                case 2: 
                    System.out.println("----Algoritmo de Kuskal----");  
                    AlgoritmoKRUSKAL algk = new AlgoritmoKRUSKAL();
                    algk.kruskal(g); 
                    break;
                case 3: menu(); break;
            }
        } while(op!=MAX );
    }

    public void menu5()
    {
        final int MAX  = 3;
        do
        {
            System.out.println("   *** MENU ***  ");
            System.out.println("1. Algoritmo de Dijkstra");
            System.out.println("2. Algoritmo de Dijkstra - Alternativo");
            System.out.println("3. Voltar ao Menu Principal");
            
            op = val.validarInt("Introduza a opcao(1-" + MAX  + "): ", 1, MAX );
            
            AlgoritmoDJISKSTRA algd = new AlgoritmoDJISKSTRA();
            int i,f;
            switch(op)
            {
                case 1: 
                    i = val.escolherVertice("Introduza o inicio do caminho(0-" + (g.getNumVertices()-1) + "): ", g.getV());
                    f = val.escolherVertice("Introduza o fim do caminho(0-" + (g.getNumVertices()-1) + "): ", g.getV());
    
                    System.out.println("----Algoritmo de Dijkstra----"); 
                    algd.dijkstra(g, i,f); 
                    break;
                case 2: 
                    i = val.escolherVertice("Introduza o inicio do caminho(0-" + (g.getNumVertices()-1) + "): ", g.getV());
                    f = val.escolherVertice("Introduza o fim do caminho(0-" + (g.getNumVertices()-1) + "): ", g.getV());
                    
                    System.out.println("----Algoritmo de Dijkstra----"); 
                    Matriz m = new Matriz();   
                    algd.caminhosMaisCurtos(m.criarMatriz(g), i, f);
                    break;
                case 3: menu(); break;
            }
        } while(op!=MAX );
    }

    public void menu6()
    {
        final int MAX  = 3;
        do
        {
            System.out.println("   *** MENU ***  ");
            System.out.println("1. ");
            System.out.println("2. ");
            System.out.println("3. Voltar ao Menu Principal");
            
            op = val.validarInt("Introduza a opcao(1-" + MAX  + "): ", 1, MAX );

            switch(op)
            {
                case 1: 
                    break;
                case 2: 
                    break;
                case 3: menu(); break;
            }
        } while(op!=MAX );
    }

    public void opcao7()
    {
        int o,d;
        o = val.escolherVertice("Introduza a origem da aresta(0-" + (g.getNumVertices()-1) + "): ", g.getV());
        d = val.escolherVertice("Introduza o destino da aresta(0-" + (g.getNumVertices()-1) + "): ", g.getV());
        if(g.arestaExiste(o, d))
            System.out.println("A aresta EXISTE!");
        else
            System.out.println("A aresta NAO EXISTE!");
    }
}