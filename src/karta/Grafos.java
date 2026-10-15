package karta;


//Qualquer Grafo
public interface Grafos<Peso>
{
	//número de vértices e arrestas
	int vértices();
	int arrestas();
	
	Interador <Vértice> vértice();
	Interador <Arresta<Peso>> arresta();
	
	Vértice aVértice();
	
	Arresta<Peso> inserir(Vértice primeiro, Vértice segundo, Peso peso);
	
	void remover (Arresta<Peso> arresta);
	
	boolean existe(Vértice primeiro, Vértice segundo);
}