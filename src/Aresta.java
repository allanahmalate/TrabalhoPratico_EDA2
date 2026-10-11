public class Aresta 
{
    private int peso, origem, destino;
    private boolean bidirecional;

    public Aresta(int origem, int destino, int peso, boolean bidirecional)
    {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
        this.bidirecional = bidirecional;
    }

    public int getOrigem() { return origem; }
    public int getDestino() { return destino; }
    public int getPeso() { return peso; }
    public boolean getBidirecional() { return bidirecional; }
    public int getOutroVertice(int vertice) 
    {
        if(vertice == origem)
            return destino;
        else 
            return origem;
    }
}
