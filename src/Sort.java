import java.util.*;

public class Sort 
{

    Sort() { }

    //crescente
    public void bubbleSort(ArrayList<Aresta> y)
    {
        for (int i=1; i<= y.size(); i++)
            for (int j=0; j < y.size()-1; j++)
                if(y.get(j).getPeso() > y.get(j+1).getPeso())
                   troca(y, j, j+1);
        
    }

    public void troca(ArrayList<Aresta> y, int primeiro, int segundo)
    {
        Aresta aux = y.get(primeiro);
        y.set(primeiro, y.get(segundo));
        y.set(segundo, aux);

        /*
        int aux = ar[primeiro];
        ar[primeiro] = ar[segundo];
        ar[segundo] = aux;
         */
    }

    int acharMenor(int[] array, boolean[] selecionado) 
    {
        int pos = -1;
        int menor = Integer.MAX_VALUE; //inicializa com o maio valor possivel para garantir a priimeira substituicao

        for(int j=0; j<array.length; j++) 
        {
            if(array[j] < menor  && (!selecionado[j]) && array[j]!= -1)
            {
                menor = array[j];
                pos = j;
            }
        }

        return pos;
    }

}
