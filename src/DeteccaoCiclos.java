public class DeteccaoCiclos 
{
    private final int[] pai;
    private final int[] rank;

    DeteccaoCiclos(int n)
    {
        pai = new int[n];
        rank = new int[n];
        for(int i=0; i < n; i++)
        {
            pai[i] = i; //inicialmente cada vertice e seu proprio pai
            rank[i] = 0;
        }
    }

    //encontrar o representante raiz de um conjunto 
    int find(int i)
    {
        if(pai[i] != i)
            pai[i] = find(pai[i]);
        return pai[i];
    }

    //Uniao de 2 conjuntos por rank
    boolean union(int x, int y)
    {
        int raizX, raizY;

        raizX = find(x);
        raizY = find(y);
        
        //se a raiz for a mesma, entao ciclo existe
        if(raizX == raizY)
            return false;

        if(rank[raizX] < rank[raizY])
            pai[raizX] = raizY;
        else
        {
            if(rank[raizX] > rank[raizY])
                pai[raizY] = raizX;
            else
            {
                pai[raizY] = raizX;
                rank[raizX]++;
            }
        }

        return true;
    }
}