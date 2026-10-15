package karta;

public class NãoExiste extends RuntimeException
{
	public NãoExiste() { super();}
	
	public NãoExiste(String mensagem) { super(mensagem); }
}