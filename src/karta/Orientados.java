package karta;

public interface Orientados <Peso> extends Grafos <Peso>
{
	int entradaDigrafo (Vértice vértice);
	int saídaDigrafo (Vértice vértice);
	
	Interador <Vértice> entradaVértice (Vértice vértice);
	Interador <Vértice> saídaVértice (Vértice vértice);
	
	Interador <Arresta <Peso>> entradaArresta (Vértice vértice);
	Interador <Arresta<Peso>> saídaArresta (Vértice vértice);
}