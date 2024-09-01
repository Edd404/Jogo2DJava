package ai;

import java.util.ArrayList;

import entidade.Entidade;
import main.GamePanel;
import java.lang.Math;

public class PathFinder {
	
	//Aqui é toda mecanica de inteligencia dos NPC, monstros e NPC, é onde eles ganham um minima vida, como seguir, te perseguir e atacar.
	//O PathFinder é um algorismo chamado A* (A-Star) sinceramente, ele é tao complexo que eu nao saberia explicar de uma forma clara, aqui ele foi escrito com diversos tutoriais e leituras, entao
	//muita coisa está misturada, em ingles, e até mesmo de uma maneira nao tao correta assim, entao no PathFinder nao documentarei tantas coisas.
	GamePanel gp;
	Node[][] node;
	ArrayList<Node> listaAberta = new ArrayList<>();
	public ArrayList <Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	boolean goalReached = false;
	int step = 0;
	
	public PathFinder(GamePanel gp) {
		this.gp = gp;
		nodesInstanciados();
	}
	
	public void nodesInstanciados() {
		
		node = new Node[gp.tamanhoMaximoColuna][gp.tamanhoMaximoLinha];
		
		int coluna = 0;
		int linha = 0;
		
		while ( coluna < gp.tamanhoMaximoColuna && linha < gp.tamanhoMaximoLinha) {
			
			node[coluna][linha] = new Node(coluna, linha);
			
			coluna++;
			if( coluna == gp.tamanhoMaximoColuna) {
				coluna = 0;
				linha++;
			}
		}	
	}
	
	public void resetNodes() {
		
		
		int coluna = 0;
		int linha = 0;
		
		while ( coluna < gp.tamanhoMaximoColuna && linha < gp.tamanhoMaximoLinha) {
			
			node[coluna][linha].aberto = false;
			node[coluna][linha].verificado = false;
			node[coluna][linha].solido = false;
			
			coluna++;
			if ( coluna == gp.tamanhoMaximoColuna) {
				coluna = 0;
				linha++;
			}
		}
		
		//Reseta todas as configuracoes
		listaAberta.clear();
		pathList.clear();
		goalReached = false;
		step = 0;	
	}
	
	public void setNodes( int startCol, int startRow, int goalCol, int goalRow, Entidade entidade) {
		
		resetNodes();
		
		//Ajustar o comeco e o fim dos nodes
		startNode = node [startCol][startRow];
		currentNode =  startNode;
		goalNode = node [goalCol][goalRow];
		listaAberta.add(currentNode);
		
		int coluna = 0;
		int linha = 0;
		
		while ( coluna < gp.tamanhoMaximoColuna && linha < gp.tamanhoMaximoLinha) {
			
			
			//Verifica os blocos para saber se sao bloco solidos, npc e etc
			int blocoNum = gp.ManiBloco.MapaCoordenadas[gp.mapaAtual][coluna][linha];   //////VERIFICAR AQUI CASO DE ERRO, ESTOU NA DUVIDA SE É ESSE O CAMINHO
			if( gp.ManiBloco.bloco[blocoNum].colisao == true) {
				node[coluna][linha].solido = true;
			}
			//Checar os blocos interativos, para nao ignoralos após alguma interacao
			for ( int i = 0; i < gp.blocosInt[1].length; i++) {
				if ( gp.blocosInt[gp.mapaAtual] [i]!= null &&  gp.blocosInt[gp.mapaAtual][i].destrutivel == true) {
					int intColuna = gp.blocosInt[gp.mapaAtual][i].mundox/gp.tileSize;
					int intLinha = gp.blocosInt[gp.mapaAtual][i].mundoy/gp.tileSize;
					node[intColuna][intLinha].solido = true;
					
				}
			}
			
			//Ajustar o custo (??)
			getCusto(node[coluna][linha]);
			
			coluna++;
			if ( coluna == gp.tamanhoMaximoColuna) {
				coluna = 0;
				linha++;
			}	
		}	
		
	}
	public void getCusto(Node node) {
		
		
		//Custo G
		int distanciaX = Math.abs(node.coluna - startNode.coluna);
		int distanciaY = Math.abs(node.linha - startNode.linha);
		node.gCost = distanciaX + distanciaY;
		
		//Custo H
		 distanciaX = Math.abs(node.coluna - goalNode.coluna);
		 distanciaY = Math.abs(node.linha - goalNode.linha);
		node.hCost = distanciaX + distanciaY;
		
		//Custo F
		node.fCost = node.gCost + node.hCost;	
	}
	
	public boolean busca() {
		
		while ( goalReached == false && step < 500) {
			
			int coluna = currentNode.coluna;
			int linha = currentNode.linha;
			
			//Checa o node atual
			currentNode.verificado = true;
			listaAberta.remove(currentNode);
			
			//Verifica o node de cima
			if ( linha -1 >= 0) {
				openNode(node[coluna][linha -1]);
			}
			//Verifica o node da esquerda
			if ( coluna -1 >= 0) {
				openNode(node[coluna-1][linha]);
			}
			//Verifica o node de baixo
			if ( linha +1 < gp.tamanhoMaximoLinha) {
				openNode(node[coluna][linha+1]);
			}
			//Verifica o node da direita
			if ( coluna +1 < gp.tamanhoMaximoColuna) {
				openNode(node[coluna+1][linha]);
			}
			
			
			//Verifica o melhor caminho
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for ( int i = 0; i < listaAberta.size(); i++) {
				
				if( listaAberta.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = listaAberta.get(i).fCost;		
				}
				else if ( listaAberta.get(i).fCost == bestNodefCost) {
					if ( listaAberta.get(i).gCost < listaAberta.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}		
			}
			
			//Se nao houver nenhum node na lista aberta, finaliza o loop
			if ( listaAberta.size() == 0) {
				break;
			}
			
			currentNode = listaAberta.get(bestNodeIndex);
			if ( currentNode == goalNode) {
				goalReached = true;
				localizarCaminho();
			}
			step++;
		}
		
		return goalReached;
	}
	
	public void openNode(Node node) {
		
		
		if ( node.aberto == false && node.verificado == false && node.solido == false) {
			node.aberto = true;
			node.parent = currentNode;
			listaAberta.add(node);
		}
	}
	
	public void localizarCaminho() {
		
		Node current = goalNode;
		
		while ( current != startNode) {
			
			pathList.add(0 , current);
			current = current.parent;
		}
	}
}
