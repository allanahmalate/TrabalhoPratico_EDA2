public class Visualizacao
{
    Visualizacao() { }

    void visualizarPercurso(int percurso[])
    {       
        System.out.print("PERCURSO: ");
        for(int vertice : percurso)
        {   System.out.print(vertice + " ");    }
        System.out.println();
    }

    void visualizarPercurso(int[] via, int percurso[], int ultimo)
    {
        System.out.print("PERCURSO: ");

        percurso[0] = ultimo;

        for(int i=1; i < via.length; i++)
        {
            percurso[i] = via[ultimo];
            ultimo = percurso[i];
        }

        for(int vertice : percurso)
        {   System.out.print(vertice + " ");    }
        System.out.println();
    }

    void visualizarCusto(int custo)
    {       
        System.out.println("Custo Total = " + custo);
        System.out.println();
    }

    void visualizarCusto(int[] custo)
    {       
        int total=0;
        System.out.print("CUSTO: ");
        for(int c : custo)
        {   
            System.out.print(c + " ");
            total += c;
        }
        System.out.println();
        visualizarCusto(total);
    }

}
