package main;

import entidade.Entidade;
import java.lang.Math;

public class EventosManipulador {
	
	
	GamePanel gp;
	EventoRetangulo evRect[][][];
	
	int eventoPassadoX, eventoPassadoY;
	boolean toqueEvento = true;
	int tempMapa, tempColuna, tempLinha;
	
	//Nessa classe conseguimos criar eventos pelo mapa
	public EventosManipulador(GamePanel gp) {
		this.gp = gp;
		

		evRect = new EventoRetangulo[gp.maxMapas][gp.tamanhoMaximoColuna][gp.tamanhoMaximoLinha];
		
		int mapa = 0;
		int coluna = 0;
		int linha = 0;
		while(mapa < gp.maxMapas && coluna < gp.tamanhoMaximoColuna && linha < gp.tamanhoMaximoLinha) {
			
			//Aqui definimos o tamanho do retangulo como um pequeno pixel de 2x2 no meio do bloco de 16x16, para que o evento seja acionado quando voce estiver dentro do bloco, nao passando aos arredores dele
			evRect[mapa][coluna][linha] = new EventoRetangulo();
			evRect[mapa][coluna][linha].x = 23;
			evRect[mapa][coluna][linha].y = 23;
			evRect[mapa][coluna][linha].width = 2;
			evRect[mapa][coluna][linha].height = 2;
			evRect[mapa][coluna][linha].evRetanguloPadraoX = evRect[mapa][coluna][linha].x;
			evRect[mapa][coluna][linha].evRetanguloPadraoY = evRect[mapa][coluna][linha].y;
			
			coluna++;
			if (coluna == gp.tamanhoMaximoColuna) {
				coluna = 0;
				linha++;
				
				if( linha == gp.tamanhoMaximoLinha) {
					linha = 0;
					mapa++;
				}
			}
		}	
	}
	
	public void checarEvento() {
		
		//Checa a distancia do jogador do ultimo bloco de evento que passou
		int distanciaX = Math.abs(gp.jogador.mundox - eventoPassadoX);
		int distanciaY = Math.abs(gp.jogador.mundoy - eventoPassadoY);
		int distancia = Math.max(distanciaX, distanciaY);
		if(distancia > gp.tileSize) {
			toqueEvento = true;
		}
		
		if(toqueEvento = true) {
		//Aqui é a definicao do bloco em que voce passar ativara o evento
		if(hit(0,13,13,"any") == true) { blocoDeDano(gp.dialogoState); }
		else if(hit(0,37,17, "Cima") == true) {blocoDeCura(gp.dialogoState); }
		else if(hit(0,8,8,"Cima") == true) {teleportar(1,5,3);}
		else if(hit(1,5,4,"Cima") == true) {teleportar(0,8,8);}	
		else if(hit(1,5,7,"any") == true) { dialogosConteudo(gp.npc[1][0]);} // <-- Aqui é para falar com um bloco a frente do NPC, ajustar o HIT para detectar o bloco
		}
	}
	
	public boolean hit(int mapa, int coluna, int linha, String reqDirecao) {
		
		boolean hit = false;
		
		if ( mapa == gp.mapaAtual) {
			
			//Aqui é para checar a area de colisao do jogador com o retangulo do evento
			gp.jogador.areaSolida.x = gp.jogador.mundox + gp.jogador.areaSolida.x;
			gp.jogador.areaSolida.y = gp.jogador.mundoy + gp.jogador.areaSolida.y;
			evRect[mapa][coluna][linha].x = coluna * gp.tileSize + evRect[mapa][coluna][linha].x;
			evRect[mapa][coluna][linha].y = linha * gp.tileSize + evRect[mapa][coluna][linha].y;
			
			//Aqui verifica a direcao do jogador para acionar o evento, pode ser alterado para só acionar em determinada direcao, nesse caso esta como ANY, ou seja, qualquer direcao.
			if(gp.jogador.areaSolida.intersects(evRect[mapa][coluna][linha]) && evRect[mapa][coluna][linha].eventoCompleto == false) {
				if(gp.jogador.direcao.contentEquals(reqDirecao) || reqDirecao.contentEquals("any")) {
					hit = true;
					
					//Aqui verificamos a possicao do jogador com o ultimo evento
					eventoPassadoX = gp.jogador.mundox;
					eventoPassadoY = gp.jogador.mundoy;
				}
			}
			
			gp.jogador.areaSolida.x = gp.jogador.areaSolidaPadraoX;
			gp.jogador.areaSolida.y = gp.jogador.areaSolidaPadraoY;
			evRect[mapa][coluna][linha].x = evRect[mapa][coluna][linha].evRetanguloPadraoX;
			evRect[mapa][coluna][linha].y = evRect[mapa][coluna][linha].evRetanguloPadraoY;
		}
	
		return hit;
	}
	
	public void blocoDeDano(int gameState) {
		
		gp.gameState = gameState;
		gp.ui.dialogoAtual = "Voce torceu o pe";
		gp.jogador.vida -= 1;
		//evRect[coluna][linha].eventoCompleto = true;
		toqueEvento = false;
	}
	
	public void blocoDeCura(int gameState) {
		
		if (gp.keyH.enterParaFalar == true) {
			gp.gameState = gameState;
			gp.jogador.cancelarAtk = true;
			gp.ui.dialogoAtual = "Voce bebeu agua abencoada.\nSua vida e mana foi recuperada";
			gp.jogador.vida = gp.jogador.vidaMaxima;
			gp.jogador.mana = gp.jogador.manaMaxima;
			
			gp.ddObjetos.setCriatura();
			gp.saveLoad.save();
		}	
	}
	
	public void teleportar(int mapa, int coluna, int linha) {
		
		gp.gameState = gp.efeitoTransicao;
		tempMapa = mapa;
		tempColuna = coluna;
		tempLinha = linha;
		
		toqueEvento = false;
		gp.playSFX(12);
	}
	
	public void dialogosConteudo(Entidade entidade) {
		
		if( gp.keyH.enterParaFalar == true) {
			gp.gameState = gp.dialogoState;
			gp.jogador.cancelarAtk = true;
			entidade.dialogosConteudo();
			
		}
	}
}
