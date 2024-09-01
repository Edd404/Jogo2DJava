package ai;

public class Node {
	
	Node parent;
	public int coluna;
	public int linha;
	int gCost;
	int hCost;
	int fCost;
	boolean solido;
	boolean aberto;
	boolean verificado;
	
	public Node( int coluna, int linha) {
		this.coluna = coluna;
		this.linha = linha;
	}
	

}
