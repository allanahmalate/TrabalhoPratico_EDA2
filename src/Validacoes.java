import java.io.*;

public class Validacoes 
{
    private BufferedReader br;
    public Validacoes() { br = new BufferedReader(new InputStreamReader(System.in)); }

    public int escolherVertice(String msg, Vertice[] labels)
    {
        int min=0, max = labels.length;
        int p;
        for(int i=0; i < labels.length; i++)
        {   System.out.println((i) + ". " + labels[i].getEtiqueta());    }

        p = validarInt(msg, min, max);
        return p;
    }

    public int validarInt(String msg)
    {
        int z=0;
        do
        {
        System.out.println(msg);
        try { z = Integer.parseInt(br.readLine()); }
        catch(NumberFormatException n) { System.out.println(n.getMessage()); }
        catch(IOException e) { System.out.println(e.getMessage()); }
        
        if(z<0)
            System.out.println("Erro!");
        } while(z<0);
        return z;
    }

    public int validarInt(String msg, int max)
    {
        int z=0;
        do
        {
        System.out.println(msg);
        try { z = Integer.parseInt(br.readLine()); }
        catch(NumberFormatException n) { System.out.println(n.getMessage()); }
        catch(IOException e) { System.out.println(e.getMessage()); }
        
        if(z<0 || z>max)
            System.out.println("Erro!");
        } while(z<0 || z>max);
        return z;
    }

    public int validarInt(String msg, int min, int max)
    {
        int z=0;
        do
        {
        System.out.println(msg);
        try { z = Integer.parseInt(br.readLine()); }
        catch(NumberFormatException n) { System.out.println(n.getMessage()); }
        catch(IOException e) { System.out.println(e.getMessage()); }
        
        if(z<min || z>max)
            System.out.println("Erro!");
        } while(z<min || z>max);
        return z;
    }

    public String validarEtiqueta(int i)
    {
        String et="";
        
        do
        {
            System.out.println("Digite a etiqueta do " + (i+1) + " vertice: ");
            try {et = br.readLine();}
            catch(NumberFormatException n) { System.out.println(n.getMessage()); }
            catch(IOException e) { System.out.println(e.getMessage()); }

            if(et.isEmpty())
            {
                System.out.println("Valor Invalido!");
            }
        } while(et.isEmpty());

        return et;
    }

    public String desejaContinuar(String msg)
    {
        String d="";
        boolean valido;

        do
        {
            System.out.println(msg);
            try 
            { d = br.readLine();}
            catch(NumberFormatException n) { System.out.println(n.getMessage()); }
            catch(IOException e) { System.out.println(e.getMessage()); }

            valido = (d.equalsIgnoreCase("Sim") || d.equalsIgnoreCase("S") || 
                d.equalsIgnoreCase("Nao") || d.equalsIgnoreCase("N"));

            if(!valido)
            {   System.out.println("Valor Invalido!");  }
        } while(!valido);

        return d;
    }

    public boolean validarBoolean(String msg)
    {
        String d="";
        boolean valido;

        do
        {
            System.out.println(msg);
            try 
            { d = br.readLine();}
            catch(NumberFormatException n) { System.out.println(n.getMessage()); }
            catch(IOException e) { System.out.println(e.getMessage()); }

            valido = (d.equalsIgnoreCase("Sim") || d.equalsIgnoreCase("S") || 
                d.equalsIgnoreCase("Nao") || d.equalsIgnoreCase("N"));

            if(!valido)
            {   System.out.println("Valor Invalido!");  }
        } while(!valido);

        if(d.equalsIgnoreCase("Nao") || d.equalsIgnoreCase("N"))
            return false;
        return true;
    }
}
