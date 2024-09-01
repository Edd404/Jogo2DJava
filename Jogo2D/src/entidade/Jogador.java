package entidade;


import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import main.Utilitarios;
import objeto.OBJ_Chave;
import objeto.OBJ_Escudo_Madeira;
import objeto.OBJ_Espada_Padrao;
import objeto.OBJ_Flecha;

public class Jogador extends Entidade{

	KeyHandler keyH;
	public final int telax;
	public final int telay;
	public boolean cancelarAtk = false;
	public boolean mudarIluminacao = false;

	public Jogador(GamePanel gp, KeyHandler keyH) {
		super(gp);
		
		this.keyH = keyH;
		
		//Este é o calculo para deixar o personagem centralizado na tela. dividimos por 2 duas vezes pois na primeira, ele reconhece o canto do bloco 16x16, na segunda o centro dele.
		telax = gp.telaLargura/2 - (gp.tileSize/2);
		telay = gp.telaAltura/2 - (gp.tileSize/2);
		
		areaSolida = new Rectangle();
		areaSolida.x = 18;
		areaSolida.y = 18;
		areaSolidaPadraoX = areaSolida.x;
		areaSolidaPadraoY = areaSolida.y;
		areaSolida.width = 32;
		areaSolida.height = 32;
		
		
		//areadeAtaque.width = 36;
		//areadeAtaque.height = 36;
		valoresPadrao();
	
		
	}
	public void setPosicaoPadrao() {
		
		mundox = gp.tileSize * 33; // <-- Onde o personagem spawna no mapa
		mundoy = gp.tileSize * 20;
		direcao = "Baixo";
		
	}
	public void restaurarStatus() {
		
		vida = vidaMaxima;	
		mana = manaMaxima;
		invencibilidade = false;
		transparente = false;
		atacando = false;
		bloqueando = false;
		knockBack = false;
		mudarIluminacao = true;
		velocidade = velocidadePadrao;
		
	}
	public void valoresPadrao () {
		mundox = gp.tileSize* 33; // <-- Onde o personagem spawna no mapa
		mundoy = gp.tileSize* 20;
		velocidadePadrao = 4;
		velocidade = velocidadePadrao;
		direcao = "Baixo";
		
		
		//Status do PERSONAGEM
		level = 1;
		vidaMaxima = 6; // <- Aqui cada 1, seria o equivalente a meia barra, ou seja 2, é uma barra 1/3 da barra e 6 é a barra completa, inicialmente
		vida = vidaMaxima;	
		manaMaxima = 4;
		mana = manaMaxima;
		//municao = 4;
		forca = 1;  //<- Com mais forca, mais dano o personagem da
		agilidade = 1; // <- Com mais agilidadde, menos dano o personagem recebe
		exp = 0;
		proxLevelExp = 5;
		moeda = 500;
		luzAtual = null;
		armaAtual = new OBJ_Espada_Padrao(gp);
		escudoAtual = new OBJ_Escudo_Madeira(gp);
		projetil = new OBJ_Flecha(gp);
		ataque = getAtaque(); // <- Os valores de ataque é a soma da forca com os atributos da arma atual
		defesa = getDefesa(); // <- Os valores de defesa é a soma da agilidade com os atributos do escudo
		
		pegarImagemJogador();
		pegarImagemdeAtaquedoJogador();
		getImagemBloqueio();
		setItens();
	}
	public int getAtaque() {
			
			areadeAtaque = armaAtual.areadeAtaque;
			return ataque = forca * armaAtual.valordeATK;
		}	
	public void setItens() {
		
		inventario.clear();
		inventario.add(armaAtual);
		inventario.add(escudoAtual);
		inventario.add(new OBJ_Chave(gp));
		inventario.add(new OBJ_Chave(gp));
		
	}
	public int getDefesa() {
			
			return defesa = agilidade * escudoAtual.valordeDEF;
		
	}
	public int getArmaAtualSlot() {
		int armaAtualSlot = 0;
		for ( int i = 0; i < inventario.size(); i++) {
			if ( inventario.get(i) == armaAtual) {
				armaAtualSlot = i;
			}
		}
		return armaAtualSlot;
	}
	public int getEscudoAtualSlot() {
		int escudoAtualSlot = 0;
		for ( int i = 0; i < inventario.size(); i++) {
			if ( inventario.get(i) == escudoAtual) {
				escudoAtualSlot = i;
			}
		}
		return escudoAtualSlot;
	}
	public void pegarImagemJogador() {
		
		cima1 = setup("/jogador/Personagem_Cima01", gp.tileSize, gp.tileSize);
		cima2 = setup("/jogador/Personagem_Cima02", gp.tileSize, gp.tileSize);
		cima3 = setup("/jogador/Personagem_Cima03", gp.tileSize, gp.tileSize);
		baixo1 = setup("/jogador/Personagem_Baixo01", gp.tileSize, gp.tileSize);
		baixo2 = setup("/jogador/Personagem_Baixo02", gp.tileSize, gp.tileSize);
		baixo3 = setup("/jogador/Personagem_Baixo03", gp.tileSize, gp.tileSize);
		esquerda1 = setup("/jogador/Personagem_Esquerda01", gp.tileSize, gp.tileSize);
		esquerda2 = setup("/jogador/Personagem_Esquerda02", gp.tileSize, gp.tileSize);
		esquerda3 = setup("/jogador/Personagem_Esquerda03", gp.tileSize, gp.tileSize);
		direita1 = setup("/jogador/Personagem_Direita01", gp.tileSize, gp.tileSize);
		direita2 = setup("/jogador/Personagem_Direita02", gp.tileSize, gp.tileSize);	
		direita3 = setup("/jogador/Personagem_Direita03", gp.tileSize, gp.tileSize);
	
	}
	public void pegarImagemDormindo(BufferedImage imagem) {
		
		cima1 = imagem;
		cima2 = imagem;
		cima3 = imagem;
		baixo1 = imagem;
		baixo2 = imagem;
		baixo3 = imagem;
		esquerda1 = imagem;
		esquerda2 = imagem;
		esquerda3 = imagem;
		direita1 = imagem;
		direita2 = imagem;
		direita3 = imagem;
	}
	public void pegarImagemdeAtaquedoJogador() {
		
		if( armaAtual.tipo == tipo_espada) {
			
			cimaATK1 = setup("/jogador/Personagem_Ataque_Cima01", gp.tileSize, gp.tileSize*2); // <- Nessa sessao foi configurado uma outra escala de imagem já que agora a imagem nao é só 16x16 e sim
			cimaATK2 = setup("/jogador/Personagem_Ataque_Cima02", gp.tileSize, gp.tileSize*2); // 32x16 ou 16x32, entao multiplicamos os valor por 2 da largura ou da altura para bater com o tamanho esperado.
			cimaATK3 = setup("/jogador/Personagem_Ataque_Cima03", gp.tileSize, gp.tileSize*2);
			
			
			baixoATK1 = setup("/jogador/Personagem_Ataque_Baixo01", gp.tileSize, gp.tileSize*2); // <- *2 Algura
			baixoATK2 = setup("/jogador/Personagem_Ataque_Baixo02", gp.tileSize, gp.tileSize*2);
			baixoATK3 = setup("/jogador/Personagem_Ataque_Baixo03", gp.tileSize, gp.tileSize*2);
			
			esquerdaATK1 = setup("/jogador/Personagem_Ataque_Esquerda01", gp.tileSize*2, gp.tileSize); // <- *2 Largura
			esquerdaATK2 = setup("/jogador/Personagem_Ataque_Esquerda02", gp.tileSize*2, gp.tileSize);
			esquerdaATK3 = setup("/jogador/Personagem_Ataque_Esquerda03", gp.tileSize*2, gp.tileSize);
		
			direitaATK1 = setup("/jogador/Personagem_Ataque_Direita01", gp.tileSize*2, gp.tileSize);
			direitaATK2 = setup("/jogador/Personagem_Ataque_Direita02", gp.tileSize*2, gp.tileSize);
			direitaATK3 = setup("/jogador/Personagem_Ataque_Direita03", gp.tileSize*2, gp.tileSize);	
		}	
		if( armaAtual.tipo == tipo_machado) {
			
			cimaATK1 = setup("/jogador/Personagem_Machado_Cima01", gp.tileSize, gp.tileSize*2); // <- Nessa sessao foi configurado uma outra escala de imagem já que agora a imagem nao é só 16x16 e sim
			cimaATK2 = setup("/jogador/Personagem_Machado_Cima02", gp.tileSize, gp.tileSize*2); // 32x16 ou 16x32, entao multiplicamos os valor por 2 da largura ou da altura para bater com o tamanho esperado.
			cimaATK3 = setup("/jogador/Personagem_Machado_Cima03", gp.tileSize, gp.tileSize*2);
			
			
			baixoATK1 = setup("/jogador/Personagem_Machado_Baixo01", gp.tileSize, gp.tileSize*2); // <- *2 Algura
			baixoATK2 = setup("/jogador/Personagem_Machado_Baixo02", gp.tileSize, gp.tileSize*2);
			baixoATK3 = setup("/jogador/Personagem_Machado_Baixo03", gp.tileSize, gp.tileSize*2);
			
			esquerdaATK1 = setup("/jogador/Personagem_Machado_Esquerda01", gp.tileSize*2, gp.tileSize); // <- *2 Largura
			esquerdaATK2 = setup("/jogador/Personagem_Machado_Esquerda02", gp.tileSize*2, gp.tileSize);
			esquerdaATK3 = setup("/jogador/Personagem_Machado_Esquerda03", gp.tileSize*2, gp.tileSize);
		
			direitaATK1 = setup("/jogador/Personagem_Machado_Direita01", gp.tileSize*2, gp.tileSize);
			direitaATK2 = setup("/jogador/Personagem_Machado_Direita02", gp.tileSize*2, gp.tileSize);
			direitaATK3 = setup("/jogador/Personagem_Machado_Direita03", gp.tileSize*2, gp.tileSize);	
		}	
		
		/*
		 * if( armaAtual.tipo == tipo_arco) {
		 * 
		 * cimaATK1 = setup("/jogador/Personagem_Machado_Cima01", gp.tileSize,
		 * gp.tileSize*2); // <- Nessa sessao foi configurado uma outra escala de imagem
		 * já que agora a imagem nao é só 16x16 e sim cimaATK2 =
		 * setup("/jogador/Personagem_Machado_Cima02", gp.tileSize, gp.tileSize*2); //
		 * 32x16 ou 16x32, entao multiplicamos os valor por 2 da largura ou da altura
		 * para bater com o tamanho esperado. cimaATK3 =
		 * setup("/jogador/Personagem_Machado_Cima03", gp.tileSize, gp.tileSize*2);
		 * 
		 * 
		 * baixoATK1 = setup("/jogador/Personagem_Machado_Baixo01", gp.tileSize,
		 * gp.tileSize*2); // <- *2 Algura baixoATK2 =
		 * setup("/jogador/Personagem_Machado_Baixo02", gp.tileSize, gp.tileSize*2);
		 * baixoATK3 = setup("/jogador/Personagem_Machado_Baixo03", gp.tileSize,
		 * gp.tileSize*2);
		 * 
		 * esquerdaATK1 = setup("/jogador/Personagem_Machado_Esquerda01", gp.tileSize*2,
		 * gp.tileSize); // <- *2 Largura esquerdaATK2 =
		 * setup("/jogador/Personagem_Machado_Esquerda02", gp.tileSize*2, gp.tileSize);
		 * esquerdaATK3 = setup("/jogador/Personagem_Machado_Esquerda03", gp.tileSize*2,
		 * gp.tileSize);
		 * 
		 * direitaATK1 = setup("/jogador/Personagem_Machado_Direita01", gp.tileSize*2,
		 * gp.tileSize); direitaATK2 = setup("/jogador/Personagem_Machado_Direita02",
		 * gp.tileSize*2, gp.tileSize); direitaATK3 =
		 * setup("/jogador/Personagem_Machado_Direita03", gp.tileSize*2, gp.tileSize); }
		 */
	}
	public void getImagemBloqueio() {
		
		bloqueioCima = setup("/jogador/Personagem_Cima_Escudo", gp.tileSize, gp.tileSize);
		bloqueioBaixo = setup("/jogador/Personagem_Baixo_Escudo", gp.tileSize, gp.tileSize);
		bloqueioEsquerda = setup("/jogador/Personagem_Esquerda_Escudo", gp.tileSize, gp.tileSize);
		bloqueioDireita = setup("/jogador/Personagem_Direita_Escudo", gp.tileSize, gp.tileSize);
		
	}
	public void update() {
		
		if(knockBack == true) {
			
			
			colisaoOn = false;
			gp.cColisao.checarBloco(this);		
			gp.cColisao.checkObjeto(this, true);
			gp.cColisao.checarEntidade(this, gp.npc);
			gp.cColisao.checarEntidade(this, gp.criaturas);
			gp.cColisao.checarEntidade(this, gp.blocosInt);
			
			if( colisaoOn == true) {
				contadorKnockback = 0;
				knockBack = false;
				velocidade = velocidadePadrao;
			}
			else if ( colisaoOn == false) {
				switch (knockbackDirecao) {
				case "Cima":
					mundoy -= velocidade;
					break;
				case "Baixo":
					mundoy += velocidade;
					break;
				case "Esquerda":
					mundox -= velocidade;
					break;
				case "Direita":
					mundox += velocidade;
					break;
				}
			}	
			
			contadorKnockback++;
			if (contadorKnockback == 10 ) {   //<-- Quanto maior esse numero, mais longe a criatura vai ao ativar o knockback
				contadorKnockback = 0;
				knockBack =  false;
				velocidade = velocidadePadrao;
			}
		}
		else if(atacando == true) {
			atacando();
		}
		else if(keyH.espacoBloquear == true) {
			
			bloqueando = true;
			bloqueioContador++;
		}
		/*Abaixo sao as atualizacoes de sprites para que a animacao ocorra, nesse primeiro bloco do Update sao atualizadas mediantes a tecla pressionada
		e o contador de sprites atualiza para que nao fique num loop infinito*/
		else if(keyH.pCima == true || keyH.pBaixo == true || keyH.pEsquerda == true || keyH.pDireita == true || keyH.enterParaFalar == true) {
			
			if(keyH.pCima == true) {
				direcao = "Cima";	
			}
			else if(keyH.pBaixo == true) {
				direcao = "Baixo";				
			}
			else if(keyH.pEsquerda == true) {
				direcao = "Esquerda";				
			}
			else if(keyH.pDireita == true) {
				direcao = "Direita";			
			}
			
			
			 
			//Aqui chega o bloco para colisao negativa ou positiva
			colisaoOn = false;
			gp.cColisao.checarBloco(this);
			
			//Aqui checamos a interacao de colisao entre objeto e jogador
			int objIndex = gp.cColisao.checkObjeto(this, true);
			pegarObjeto(objIndex);
			
			//Aqui checamos a interacao de colisao entre NPC e jogador.
			int npcIndex = gp.cColisao.checarEntidade(this, gp.npc);
			interagirComNPC(npcIndex);
			
			//Aqui checamos a colisao com as criaturas
			int criaturasIndex = gp.cColisao.checarEntidade(this, gp.criaturas);
			tocarCriatura(criaturasIndex);
			
			//Checar a colisao com os blocos interativos
			gp.cColisao.checarEntidade(this, gp.blocosInt);
			
			//Aqui é onde fazemos as checagens dos eventos
			gp.evManipulador.checarEvento();
			
			
			//Se a colisao for falsa, o personagem pode atravessar o bloco
			if (colisaoOn == false && keyH.enterParaFalar == false) {
				switch(direcao) {
				case "Cima":
					mundoy -= velocidade;
					break;
				case "Baixo":
					mundoy += velocidade;
					break;
				case "Esquerda":
					mundox -= velocidade;
					break;
				case "Direita":
					mundox += velocidade;
					break;
				}
			}
			
			if(keyH.enterParaFalar == true && cancelarAtk == false) {
				gp.playSFX(5);
				atacando = true;
				contadorDeSprites = 0;
			}
			cancelarAtk = false;	
			gp.keyH.enterParaFalar = false;
			bloqueando = false;
			bloqueioContador = 0;
			
			
		contadorDeSprites++;
		if (contadorDeSprites > 3) {
			if (numeroDoSprite == 1) {
				numeroDoSprite = 2;
			} else if (numeroDoSprite == 2) {
				numeroDoSprite = 3;			
			}	
			else {
				numeroDoSprite = 1;
			}
			contadorDeSprites = 0;
			}	
			bloqueando = false; ///////////////VERIFICAR
			bloqueioContador = 0;
		}
		if (gp.keyH.tecladeAtirar == true && projetil.vivo == false && possibilidadeAtirarContador == 30 
				&& projetil.capacidadeRecursos(this) == true) {
			
			//Padrao das coordenadas, direcao e usuários
			projetil.set(mundox, mundoy, direcao, true, this);
			
			//Subtrair a mana, municao e etc
			projetil.diminuirRecursos(this);
					
			for ( int i = 0; i < gp.projeteis[1].length; i++) {
				if ( gp.projeteis[gp.mapaAtual][i] == null) {
					gp.projeteis[gp.mapaAtual][i] = projetil;
					break;
				}	
			}
				
			possibilidadeAtirarContador = 0;
			gp.playSFX(7);
		}
		
		//Esse IF nao pode sair daqui xD
		if( invencibilidade == true) {
			invencibilidadeContador++;
			if(invencibilidadeContador > 60) {
				invencibilidade = false;
				transparente = false;
				invencibilidadeContador = 0;
			}
		}
		if ( possibilidadeAtirarContador < 30) {
			possibilidadeAtirarContador++;
		}
		if ( vida > vidaMaxima) {
			vida = vidaMaxima;
		}
		if ( mana > manaMaxima) {
			mana = manaMaxima;
		}
		if ( vida <= 0) {
			gp.gameState = gp.gameOver; 
			gp.ui.numeroDeComandos = -1;
			gp.pararMusica();
			gp.playSFX(11);
		}
	}

	public void pegarObjeto(int i) {
		
		//Esta instrucao exclui o objto quanto tocarmos nele
		if(i != 999) {	
			
			//Itens acumulativos ou nao cujo nao vao para o inventario
			if ( gp.obj[gp.mapaAtual][i].tipo == tipo_pegarApenas) {
				gp.obj[gp.mapaAtual][i].usar(this);
				gp.obj[gp.mapaAtual][i] = null;
			}
			//Obstaculos
			else if(gp.obj[gp.mapaAtual][i].tipo == tipo_obstaculo ) {	
				if(keyH.enterParaFalar == true) {
					cancelarAtk = true;
					gp.obj[gp.mapaAtual][i].interagir();
				}
			}
			
			
			//Itens que vao para o inventario
			else {
				String texto;
				if ( podeObterItem(gp.obj[gp.mapaAtual][i]) == true) {
					gp.playSFX(3);
					texto = "Você pegou um(a) " + gp.obj[gp.mapaAtual][i].nome + "!";
				}
				else {
					texto = "Inventário cheio!";
				}
				gp.ui.addMensagem(texto);
				gp.obj[gp.mapaAtual][i] = null;
			}
		}			
	}
	public void interagirComNPC(int i) {
		
		if(gp.keyH.enterParaFalar == true) {
			
			if(i != 999) {	
				cancelarAtk = true;
				gp.gameState = gp.dialogoState;
				gp.npc[gp.mapaAtual][i].dialogosConteudo();			
			}	
		}	
	}
	public void tocarCriatura(int i) {
		if(i != 999) {	
			
			if(invencibilidade == false && gp.criaturas[gp.mapaAtual][i].morto == false) {
				gp.playSFX(1); //<- SFX do personagem ao receber dano // !! PRECISA VERIFICAR TAMBEM NA CLASSE ENTIDADE
				
				int dano = gp.criaturas[gp.mapaAtual][i].ataque - defesa;
				if(dano < 1) {
					dano = 1;
				}
				
				vida -= dano;
				invencibilidade = true;
				transparente = true;
			}	
		}	
	}
	
	//Aqui é quanto damos de dano na criatura
	public void danodaCriatura(int i, Entidade atacante, int ataque, int knockBackPoder) {
		
		if (i != 999) {
			
			if(gp.criaturas[gp.mapaAtual][i].invencibilidade == false) {
				
				gp.playSFX(2); // <- SFX do personagem ao dar dano
				
				if ( knockBackPoder > 0) {
					knockBack(gp.criaturas[gp.mapaAtual][i],atacante, knockBackPoder);
				}
				
				if(gp.criaturas[gp.mapaAtual][i].equilibrio == true ) {
					ataque *= 2;
				}
							
				int dano = ataque - gp.criaturas[gp.mapaAtual][i].defesa;
				if(dano <0) {
					dano = 0;
				}
				gp.criaturas[gp.mapaAtual][i].vida -= 1;
				gp.ui.addMensagem(dano + " de dano!");
				gp.criaturas[gp.mapaAtual][i].invencibilidade = true;
				gp.criaturas[gp.mapaAtual][i].aiCriaturas();
				if(gp.criaturas[gp.mapaAtual][i].vida <= 0) {
					gp.criaturas[gp.mapaAtual][i].morto = true;
					gp.ui.addMensagem("Matou " + gp.criaturas[gp.mapaAtual][i].nome + "!");
					gp.ui.addMensagem("Exp +  " + gp.criaturas[gp.mapaAtual][i].exp);
					exp += gp.criaturas[gp.mapaAtual][i].exp;
					checarLevelUP();
				}
			}
		}		
	}
	public void danoBlocoInterativo(int i) {
		
		if (i != 999 && gp.blocosInt[gp.mapaAtual][i].destrutivel == true && gp.blocosInt[gp.mapaAtual][i].itemCorretoInterativo(this) == true && gp.blocosInt[gp.mapaAtual][i].invencibilidade == false) {
			
			gp.blocosInt[gp.mapaAtual][i].playSFXAntes();
			gp.blocosInt[gp.mapaAtual][i].vida--;
			
			gp.blocosInt[gp.mapaAtual][i].invencibilidade = true;
			
			//Gerar particulas
			gerarParticulas(gp.blocosInt[gp.mapaAtual][i], gp.blocosInt[gp.mapaAtual][i]);
			
			if(gp.blocosInt[gp.mapaAtual][i].vida == 0) {
				gp.blocosInt[gp.mapaAtual][i].playSFXDepois();
				gp.blocosInt[gp.mapaAtual][i] = gp.blocosInt[gp.mapaAtual][i].getBlocoDestruido();
			}
			
		}
	}
	
	public void danoProjeteis(int i) {
		//Basicamente significa que, quando a arma do jogador atingir o projetil, ele morre, ou seja, é destruido
		if ( i != 999) {
			Entidade projeteis = gp.projeteis[gp.mapaAtual][i];
			projeteis.vivo = false;
			gerarParticulas(projeteis, projeteis);
		}
		
		
	}
	public void checarLevelUP() {
		
		if ( exp >= proxLevelExp) {
			
			level++;
			proxLevelExp = proxLevelExp*2;
			vidaMaxima += 2;
			forca++;
			//agilidade++;
			ataque = getAtaque();
			defesa = getDefesa();
			
			gp.playSFX(6);
			gp.gameState = gp.dialogoState;
			gp.ui.dialogoAtual ="Você upou para o level " + level + "Você está\n" + "se sentindo muito mais forte!";
			
			
		}
	}
	public void selecionarItem() {
		
		int itemIndex = gp.ui.getIndexdoItemnoSlot(gp.ui.jogadorSlotColuna, gp.ui.jogadorSlotLinha);
		
		if( itemIndex < inventario.size()) {
			Entidade itemSelecionado = inventario.get(itemIndex);
			
			if( itemSelecionado.tipo == tipo_espada || itemSelecionado.tipo == tipo_machado) {
				armaAtual = itemSelecionado;
				ataque = getAtaque();
				pegarImagemdeAtaquedoJogador();
			}
			if ( itemSelecionado.tipo == tipo_escudo) {
				escudoAtual = itemSelecionado;
				defesa = getDefesa();
			}
			if( itemSelecionado.tipo == tipo_luz) {
				if( luzAtual == itemSelecionado ) {
					luzAtual = null;
				}
				else {
					luzAtual = itemSelecionado;
				}
				mudarIluminacao = true;
			}
			if (itemSelecionado.tipo == tipo_consumivel) {
				
				if(itemSelecionado.usar(this) == true) {
					if ( itemSelecionado.quantidade > 1) {
						 itemSelecionado.quantidade--;
					}
					else {
						inventario.remove(itemIndex);
					}			
				}	
			}
		}
	}
	//Esse metodo ele faz uma busca oelo inventario, se já possui o item, ele utiliza o nomeItem para identificar e entende que é um item agrupavel
	public int buscarItemNoInventario( String nomeItem) {
		
		int itemIndex = 999;
		
		for ( int i = 0; i < inventario.size(); i++) {
			if ( inventario.get(i).nome.equals(nomeItem)) {
				itemIndex = i;
				break;
			}
		}
		return itemIndex;
	}
	public boolean podeObterItem(Entidade item) {
		
		boolean podeObter = false;
		
		//Checar se o item é agrupável
		if ( item.agrupavel == true) {
			
			int index = buscarItemNoInventario(item.nome);
			
			if( index != 999) {
				inventario.get(index).quantidade++;
				podeObter = true;
			}
			else { //Um novo item, ou seja, nao tinha no inventario, precisa verificar se tem espaco
				if ( inventario.size() != inventarioTamanhoMaximo) {
					 inventario.add(item);
					 podeObter = true;
				}
			}
		}
		else { //Nao é um item agrupavel, //verificar espaco no inventario
			if ( inventario.size() != inventarioTamanhoMaximo) {
				 inventario.add(item);
				 podeObter = true;
			}
		}
		return podeObter;
	}
	public void draw(Graphics2D g2) {

		BufferedImage imagem = null;
		
		int tempTelax = telax;
		int tempTelay = telay;
		
		//Aqui sao as imagens carregadas para gerar o movimento do personagem, usando o IF para muda-las de acordo com a direcao.
		
		switch(direcao) {
		case "Cima":
			if( atacando == false) {
				
				if (numeroDoSprite == 1) {imagem = cima1;}			
				if (numeroDoSprite == 2) {imagem = cima2;}
				if (numeroDoSprite == 3) {imagem = cima3;}
			}
			if (atacando == true) {
				tempTelay = telay - gp.tileSize;
				if (numeroDoSprite == 1) {imagem = cimaATK1;}			
				if (numeroDoSprite == 2) {imagem = cimaATK2;}
				if (numeroDoSprite == 3) {imagem = cimaATK3;}
				}
			if ( bloqueando == true) {
				imagem = bloqueioCima;
			}
			break;
			
		case "Baixo":
			if ( atacando == false) {
				if (numeroDoSprite == 1) {imagem = baixo1;}
				if (numeroDoSprite == 2) {imagem = baixo2;}
				if (numeroDoSprite == 3) {imagem = baixo3;}
			}
			if ( atacando == true) {
				
				if (numeroDoSprite == 1) {imagem = baixoATK1;}
				if (numeroDoSprite == 2) {imagem = baixoATK2;}
				if (numeroDoSprite == 3) {imagem = baixoATK3;}	
			}	
			if ( bloqueando == true) {
				imagem = bloqueioBaixo;
			}
			break;
			
		case "Esquerda":
			if (atacando == false) {
				if (numeroDoSprite == 1) {imagem = esquerda1;}
				if (numeroDoSprite == 2) {imagem = esquerda2;}
				if (numeroDoSprite == 3) {imagem = esquerda3;}
			}
			if (atacando == true) {
				tempTelax = telax - gp.tileSize;
				if (numeroDoSprite == 1) {imagem = esquerdaATK1;}
				if (numeroDoSprite == 2) {imagem = esquerdaATK2;}
				if (numeroDoSprite == 3) {imagem = esquerdaATK3;}
			}
			if ( bloqueando == true) {
				imagem = bloqueioEsquerda;
			}
			break;
			
		case "Direita":
			if(atacando == false) {
				if (numeroDoSprite == 1) {imagem = direita1;}
				if (numeroDoSprite == 2) {imagem = direita2;}
				if (numeroDoSprite == 3) {imagem = direita3;}
			}
			if (atacando == true) {
				
				if (numeroDoSprite == 1) {imagem = direitaATK1;}
				if (numeroDoSprite == 2) {imagem = direitaATK2;}
				if (numeroDoSprite == 3) {imagem = direitaATK3;}
			}	
			if ( bloqueando == true) {
				imagem = bloqueioDireita;
			}
			break;
		}
		
		
		//Aqui é um pequeno efeito para que no momento que voce o personagem levar dano, fique transparente para representar um breve momento de invencibilidade
		if( transparente == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
		}
		g2.drawImage(imagem, tempTelax, tempTelay, null);	
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); //<- Reseta o sprite para a cor normal
		
	}
}
