package karta;

public interface Interador<Elemento>
{
	boolean hashpróximo();
	Elemento próximo() throws NãoExiste;
	
	void reeniciar();
}