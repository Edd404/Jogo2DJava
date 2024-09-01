package main;

import entidade.Entidade;

public class ChecarColisao {
	
	
	GamePanel gp;
	
	public ChecarColisao(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checarBloco(Entidade entidade) {
		
		//Aqui é a checagem para colisao entre os blocos, basicamente determina a area X e Y de um retangulo pretedertimado ( de acordo com seu perrsonagem ) para verificar se é algo sólido e se colidirá.
		int entidadeEsquerdaMundoX = entidade.mundox + entidade.areaSolida.x;
		int entidadeDireitaMundoX = entidade.mundox + entidade.areaSolida.x +  entidade.areaSolida.width;
		int entidadeTopoMundoY = entidade.mundoy + entidade.areaSolida.y;
		int entidadeBaseMundoY = entidade.mundoy + entidade.areaSolida.y +  entidade.areaSolida.height;
		
		int entidadeColunaEsquerda = entidadeEsquerdaMundoX/gp.tileSize;
		int entidadeColunaDireita = entidadeDireitaMundoX/gp.tileSize;
		int entidadeTopoLinha = entidadeTopoMundoY/gp.tileSize;
		int entidadeBaseLinha = entidadeBaseMundoY/gp.tileSize;
		
		int BlocoNum1, BlocoNum2;
		
		//Direcao temporário para nao bugar o knockback
		String direcao = entidade.direcao;
		if(entidade.knockBack == true) {
			direcao = entidade.knockbackDirecao;
		}
		
		switch(direcao) {
		case "Cima":
			entidadeTopoLinha = (entidadeTopoMundoY - entidade.velocidade)/gp.tileSize;
			BlocoNum1 = gp.ManiBloco.MapaCoordenadas[gp.mapaAtual][entidadeColunaEsquerda][entidadeTopoLinha];
			BlocoNum2 = gp.ManiBloco.MapaCoordenadas[gp.mapaAtual][entidadeColunaDireita][entidadeTopoLinha];
			if (gp.ManiBloco.bloco[BlocoNum1].colisao == true || gp.ManiBloco.bloco[BlocoNum2].colisao == true) {
				entidade.colisaoOn = true;
			}
			break;
		case "Baixo":
			entidadeBaseLinha = (entidadeBaseMundoY + entidade.velocidade)/gp.tileSize;
			BlocoNum1 = gp.ManiBloco.MapaCoordenadas[gp.mapaAtual][entidadeColunaEsquerda][entidadeBaseLinha];
			BlocoNum2 = gp.ManiBloco.MapaCoordenadas[gp.mapaAtual][entidadeColunaDireita][entidadeBaseLinha];
			if (gp.ManiBloco.bloco[BlocoNum1].colisao == true || gp.ManiBloco.bloco[BlocoNum2].colisao == true) {
				entidade.colisaoOn = true;
			}
			break;
		case "Esquerda":
			entidadeColunaEsquerda = (entidadeEsquerdaMundoX - entidade.velocidade)/gp.tileSize;
			BlocoNum1 = gp.ManiBloco.MapaCoordenadas[gp.mapaAtual][entidadeColunaEsquerda][entidadeTopoLinha];
			BlocoNum2 = gp.ManiBloco.MapaCoordenadas[gp.mapaAtual][entidadeColunaEsquerda][entidadeBaseLinha];
			if (gp.ManiBloco.bloco[BlocoNum1].colisao == true || gp.ManiBloco.bloco[BlocoNum2].colisao == true) {
				entidade.colisaoOn = true;
			}
			break;
		case "Direita":
			entidadeColunaDireita = (entidadeDireitaMundoX + entidade.velocidade)/gp.tileSize;
			BlocoNum1 = gp.ManiBloco.MapaCoordenadas[gp.mapaAtual][entidadeColunaDireita][entidadeTopoLinha];
			BlocoNum2 = gp.ManiBloco.MapaCoordenadas[gp.mapaAtual][entidadeColunaDireita][entidadeBaseLinha];
			if (gp.ManiBloco.bloco[BlocoNum1].colisao == true || gp.ManiBloco.bloco[BlocoNum2].colisao == true) {
				entidade.colisaoOn = true;
			}
			break;
		}
		
	}
	// Aqui será checado o estado do objeto para colisao, e se o objeto é um jogador ou nao.
	public int checkObjeto(Entidade entidade, boolean jogador) {
		
		int index = 999;
		
		for(int i = 0; i < gp.obj[1].length; i++) {
			if (gp.obj[gp.mapaAtual][i] != null) {
				
				//Aqui verificamos a area solida e a posicao da entidade
				entidade.areaSolida.x = entidade.mundox + entidade.areaSolida.x;
				entidade.areaSolida.y = entidade.mundoy + entidade.areaSolida.y;
				
				//Aqui verificamos a area solida do objeto e a posicao do objeto
				gp.obj[gp.mapaAtual][i].areaSolida.x = gp.obj[gp.mapaAtual][i].mundox + gp.obj[gp.mapaAtual][i].areaSolida.x;
				gp.obj[gp.mapaAtual][i].areaSolida.y = gp.obj[gp.mapaAtual][i].mundoy + gp.obj[gp.mapaAtual][i].areaSolida.y;
				
				switch(entidade.direcao) {
				case"Cima":
					entidade.areaSolida.y -= entidade.velocidade;	
					break;
				case"Baixo":
					entidade.areaSolida.y += entidade.velocidade;
					break;
				case"Esquerda":
					entidade.areaSolida.x -= entidade.velocidade;
					break;
				case"Direita":
					entidade.areaSolida.x += entidade.velocidade;						
					break;
				}
				if (entidade.areaSolida.intersects(gp.obj[gp.mapaAtual][i].areaSolida)) {	
					if(gp.obj[gp.mapaAtual][i].colisao == true) {
						entidade.colisaoOn = true;
					}
					if (jogador == true) {
						index = i;
					}
				}	
				//Aqui reseta os valores da entidade e do objeto ao valor padrao
				entidade.areaSolida.x = entidade.areaSolidaPadraoX;
				entidade.areaSolida.y = entidade.areaSolidaPadraoY;
				gp.obj[gp.mapaAtual][i].areaSolida.x = gp.obj[gp.mapaAtual][i].areaSolidaPadraoX;
				gp.obj[gp.mapaAtual][i].areaSolida.y = gp.obj[gp.mapaAtual][i].areaSolidaPadraoY;
				
			}
		}
		return index;
		
	}

	public int checarEntidade(Entidade entidade, Entidade[][] alvo) {
		
		int index = 999;
		
		String direcao = entidade.direcao;
		if(entidade.knockBack == true) {
			direcao = entidade.knockbackDirecao;
		}
		
		for(int i = 0; i < alvo[1].length; i++) {
			if (alvo[gp.mapaAtual][i] != null) {
				
				//Aqui verificamos a area solida e a posicao da entidade
				entidade.areaSolida.x = entidade.mundox + entidade.areaSolida.x;
				entidade.areaSolida.y = entidade.mundoy + entidade.areaSolida.y;
				
				//Aqui verificamos a area solida do objeto e a posicao do objeto
				alvo[gp.mapaAtual][i].areaSolida.x = alvo[gp.mapaAtual][i].mundox + alvo[gp.mapaAtual][i].areaSolida.x;
				alvo[gp.mapaAtual][i].areaSolida.y = alvo[gp.mapaAtual][i].mundoy + alvo[gp.mapaAtual][i].areaSolida.y;
				
				switch(direcao) {
				case "Cima":
					entidade.areaSolida.y -= entidade.velocidade;	
					break;
				case "Baixo":
					entidade.areaSolida.y += entidade.velocidade;
					break;
				case"Esquerda":
					entidade.areaSolida.x -= entidade.velocidade;
					break;
				case"Direita":
					entidade.areaSolida.x += entidade.velocidade;				
					break;		
				}		
				if (entidade.areaSolida.intersects(alvo[gp.mapaAtual][i].areaSolida)) {						
					if(alvo[gp.mapaAtual][i] != entidade) {
						entidade.colisaoOn = true;					
						index = i;	
					}
								
			}
				//Aqui reseta os valores da entidade e do objeto ao valor padrao
				entidade.areaSolida.x = entidade.areaSolidaPadraoX;
				entidade.areaSolida.y = entidade.areaSolidaPadraoY;
				alvo[gp.mapaAtual][i].areaSolida.x = alvo[gp.mapaAtual][i].areaSolidaPadraoX;
				alvo[gp.mapaAtual][i].areaSolida.y = alvo[gp.mapaAtual][i].areaSolidaPadraoY;
				
			}
		}
		return index;	
	}
	
	public boolean checarJogador(Entidade entidade) {
		
		boolean contatoJogador = false;
		
		//Aqui verificamos a area solida e a posicao da entidade
		entidade.areaSolida.x = entidade.mundox + entidade.areaSolida.x;
		entidade.areaSolida.y = entidade.mundoy + entidade.areaSolida.y;
		
		//Aqui verificamos a area solida do objeto e a posicao do objeto
		gp.jogador.areaSolida.x = gp.jogador.mundox + gp.jogador.areaSolida.x;
		gp.jogador.areaSolida.y = gp.jogador.mundoy + gp.jogador.areaSolida.y;
		
		switch(entidade.direcao) {
		case "Cima":
			break;
		case "Baixo":
			entidade.areaSolida.y += entidade.velocidade;
			break;
		case"Esquerda":
			entidade.areaSolida.x -= entidade.velocidade;
			break;
		case"Direita":
			entidade.areaSolida.x += entidade.velocidade;	
			break;
			}
		if (entidade.areaSolida.intersects(gp.jogador.areaSolida)) {	
			entidade.colisaoOn = true;	
			contatoJogador = true;
		}
		
		//Aqui reseta os valores da entidade e do objeto ao valor padrao
		entidade.areaSolida.x = entidade.areaSolidaPadraoX;
		entidade.areaSolida.y = entidade.areaSolidaPadraoY;
		gp.jogador.areaSolida.x = gp.jogador.areaSolidaPadraoX;
		gp.jogador.areaSolida.y = gp.jogador.areaSolidaPadraoY;
		
		return contatoJogador;
	}
}
