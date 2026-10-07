public class Vertice 
{
    private int posicao;
    private String etiqueta;

    public Vertice(int posicao, String etiqueta)
    {
        this.posicao = posicao;
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() { return etiqueta; }
    public int getPosicao() { return posicao; }
}
