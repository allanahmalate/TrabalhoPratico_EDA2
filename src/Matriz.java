public class Matriz 
{
    public int[][] criarMatriz(Grafo g)
    { 
        int[][] t = new int[g.getV().length][g.getV().length];
        for (int i=0; i < t.length; i++) //linhas
            for (int j=0; j < t[0].length; j++)//colunas 
            t[i][j]=0;
        
        for (int i=0; i < g.getA().size(); i++) 
        {
            int origem = g.getA().get(i).getOrigem(), 
                destino = g.getA().get(i).getDestino();

                t[origem][destino] = t[destino][origem] = 1;
        }
        
        return t;
    }

    public void visualMatriz(int[][] x) 
    { 
        for (int i=0; i<x.length; i++) //linhas
        { 
        for (int j=0; j<x[0].length; j++) //colunas
        {
                System.out.print(x[i][j]+" ");
        }
        System.out.println();
        }
    }
}
