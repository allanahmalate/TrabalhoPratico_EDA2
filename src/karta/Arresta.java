package karta;

public interface Arresta<Peso>
{
	Peso peso();
	
	Vértice[] extremo();
	
	Vértice oposto (Vértice extremo);
}