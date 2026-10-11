import java.util.ArrayList;
import java.util.List;

public class Lista 
{
    public ArrayList criarLista(Grafo g)
    {
        ArrayList lista = new ArrayList<>();

        for(int i=0; i < g.getV().length; i++)
        {
            //sublista para cada Vertice
            List<Integer> listaI = new ArrayList<>();
            
            Vertice[] vizinho = g.getVizinhosDe(g.getV()[i]);

            for(int j=0; j < vizinho.length; j++)
            { listaI.add(vizinho[j].getPosicao()); }

            lista.add(listaI);
        }

        return lista;
    }

    public void visualizarLista(ArrayList lista, Grafo g) 
    {
        for(int i=0; i < lista.size(); i++)
        {
            List<Integer> sublista = (List<Integer>) lista.get(i);

            if(!sublista.isEmpty())
            {
                System.out.println(g.getV()[i].getEtiqueta() + ": ");

                for(int j=0; j < sublista.size(); j++)
                {
                    System.out.println("[" + g.getV()[sublista.get(j)].getEtiqueta() + "] ");
                }
                System.out.println();
            }
        }
    }
}
