import java.util.*;

public class Grafo
{
    private Validacoes val;
    private ArrayList<Aresta> conjuntoArestas;
    private Vertice conjuntoEtiquetas[];
    private int numVertices;
    private int numArestas;

    public Vertice[] getV() { return conjuntoEtiquetas; }
    public ArrayList<Aresta> getA() { return conjuntoArestas; }
    public Aresta getAresta(int origem, int destino) { return procurarAresta(origem,destino); }
    public int getNumVertices() { return numVertices; }
    public int getnumArestas() { return numArestas; }

    public Grafo(Vertice conjuntoEtiquetas[], ArrayList<Aresta> conjuntoArestas)
    {
        this.conjuntoEtiquetas = conjuntoEtiquetas;
        this.conjuntoArestas = conjuntoArestas;
        this.numVertices = conjuntoEtiquetas.length;
        this.numArestas = conjuntoArestas.size()/2;
    }

    public Grafo(int numVertices)
    { 
        val = new Validacoes();
        this.numVertices = numVertices;

        conjuntoEtiquetas = criarEtiquetas(numVertices);
        conjuntoArestas = criarArestas();
     }

    public Vertice[] criarEtiquetas(int n)
    {
        String label = "";
        Vertice et[] = new Vertice[n];

        for(int i=0; i < n; i++)
        {
            label = val.validarEtiqueta(i);
            et[i] = new Vertice(i, label);
        }

        return et;
    }

    public void addEtiqueta(Vertice novo)
    {
        Vertice et[] = new Vertice[this.conjuntoEtiquetas.length+1];

        for(int i=0; i < this.conjuntoEtiquetas.length; i++)
        {
            et[i] = this.conjuntoEtiquetas[i];
        }

        et[this.conjuntoEtiquetas.length+1] = novo;

        this.conjuntoEtiquetas=et;
    }

    public void addAresta(int origem, int destino, int peso, boolean bidirecional)
    {
        if(!verticeExiste(origem))
            addEtiqueta(null);

        if(!verticeExiste(destino))
            addEtiqueta(null);

        this.conjuntoArestas.add(new Aresta(origem, destino, peso));
        if(bidirecional)
            this.conjuntoArestas.add(new Aresta(destino, origem, peso));
    }

    public ArrayList<Aresta> criarArestas()
    {
        String r;
        int o,d,p;

        ArrayList<Aresta> arestas = new ArrayList<Aresta>();

        do
        {
            o = val.escolherVertice("Introduza a origem da aresta(0-" + (numVertices-1) + "): ", getV());
            d = val.escolherVertice("Introduza o destino da aresta(0-" + (numVertices-1) + "): ", getV());
            p = val.validarInt("Escolha um peso para a aresta: ");
            
            arestas.add(new Aresta(o, d, p));
            
            r = val.desejaContinuar("Deseja adicionar uma nova Aresta? [SIM/NAO]");
        }while(r.equalsIgnoreCase("Sim") || r.equalsIgnoreCase("S"));
        
        return arestas;
    }

    public Vertice[] getVizinhosDe(Vertice root)
    {
        ArrayList<Vertice> vizinhosValidos = new ArrayList<>();
        Vertice proximo;

        for(Aresta o: conjuntoArestas)
        {
            if(o.getDestino() == root.getPosicao())
            {
                proximo = this.conjuntoEtiquetas[o.getOrigem()];
                vizinhosValidos.add(proximo);
            }
            if(o.getOrigem() == root.getPosicao())
            {
                proximo = this.conjuntoEtiquetas[o.getDestino()];
                vizinhosValidos.add(proximo);
            }
        }

            Vertice[] resultado = new Vertice[vizinhosValidos.size()];
            for(int i=0; i<vizinhosValidos.size(); i++)
            { resultado[i] = vizinhosValidos.get(i); }

            return resultado;
    }

    public boolean arestaExiste(int origem, int destino)
    {
        for(int i=0; i < this.conjuntoArestas.size(); i++)
        {
            if(this.conjuntoArestas.get(i).getOrigem() == origem && this.conjuntoArestas.get(i).getDestino() == destino)
            { return true; }
        }

        return false;
    }

    public boolean verticeExiste(int posicao)
    {
        for(int i=0; i < this.conjuntoEtiquetas.length; i++)
        {
            if(this.conjuntoEtiquetas[i].getPosicao() == posicao)
            { return true; }
        }

        return false;
    }

    public boolean verticeExiste(String label)
    {
        for(int i=0; i < this.conjuntoEtiquetas.length; i++)
        {
            if(this.conjuntoEtiquetas[i].getEtiqueta().equalsIgnoreCase(label))
            { return true; }
        }

        return false;
    }

    public Aresta procurarAresta(int origem, int destino)
    {
        for(Aresta x : conjuntoArestas)
        {
            if((x.getOrigem() == origem && x.getDestino() == destino) || (x.getDestino() == origem && x.getOrigem() == destino))
            { return x; }
        }

        return null;
    }


}