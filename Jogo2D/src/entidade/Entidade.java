package entidade;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Utilitarios;

public class Entidade {
	
	
	GamePanel gp;
	
	public int mundox, mundoy;
	public BufferedImage cima1, cima2,cima3, baixo1, baixo2, baixo3, esquerda1, esquerda2, esquerda3, direita1, direita2, direita3, setamenu;
	public BufferedImage cimaATK1, cimaATK2, cimaATK3, baixoATK1, baixoATK2, baixoATK3, esquerdaATK1, esquerdaATK2, esquerdaATK3, direitaATK1, direitaATK2, direitaATK3;
	public BufferedImage imagem, imagem2, imagem3, bloqueioCima, bloqueioBaixo, bloqueioEsquerda, bloqueioDireita;
	public String direcao = "Baixo";
	public boolean invencibilidade = false;
	public boolean atacando = false;
	public boolean knockBack = false;
	
	//Contadores
	public int contadorDeSprites = 0;
	public int numeroDoSprite = 1;
	public int contadorParaAcoes = 0;
	public int invencibilidadeContador = 0;
	public int possibilidadeAtirarContador = 0;
	int mortoContador = 0;
	int barraHPContador = 0;
	int contadorKnockback = 0;
	public int bloqueioContador = 0;
	public int equilibrioContador = 0;
	
	String dialogos[] = new String[50];
	int dialogoIndex = 0;
	public boolean colisao = false;
	boolean barraHPOn = false;
	public boolean noCaminho = false;
	public boolean bloqueando = false;
	public boolean transparente = false;
	public boolean equilibrio = false;
	public boolean aberto = false;
	public Entidade atacante;
	public String knockbackDirecao;
	
	//Status das entidadaes
	public int velocidadePadrao;
	public String nome;
	public int vidaMaxima;
	public int vida;
	public int manaMaxima;
	public int mana;
	public int municao;
	public int velocidade;
	public int level;
	public int forca;
	public int agilidade;
	public int ataque;
	public int defesa;
	public int exp;
	public int proxLevelExp;
	public int moeda;
	public int motion1_duracao;
	public int motion2_duracao;
	public Entidade armaAtual;
	public Entidade escudoAtual;
	public Entidade luzAtual;
	public Projeteis projetil;
	
	//Atributos de itens
	public ArrayList<Entidade> inventario = new ArrayList<>();
	public final int inventarioTamanhoMaximo = 20;
	public int valor;
	public int valordeATK;
	public int valordeDEF;
	public String descricao = "";
	public int custodeUso;
	public int precos;
	public int knockBackPoder = 0;
	public boolean agrupavel = false;
	public int quantidade = 1;
	public int tamanhoIluminacao;
	public Entidade loot;
	
	
	//Tipos de atribuicoes
	public int tipo; //<- 0 para NPC, 1 para jogador, 2 para criatura
	public final int tipo_joagdor = 0;
	public final int tipo_npc = 1;
	public final int tipo_criatura = 2;
	public final int tipo_espada = 3;
	public final int tipo_escudo = 4;
	public final int tipo_machado = 5;
	public final int tipo_consumivel = 6;
	public final int tipo_arco = 7;
	public final int tipo_pegarApenas = 8;
	public final int tipo_obstaculo = 9;
	public final int tipo_luz = 10;
	
	
	//Aqui é onde definimos a area de colisao do personagem e NPCS, para que o bloco de 16x16 nao fique ou sem colisao, ou colidindo por completo
	public Rectangle areaSolida = new Rectangle(0, 0, 32, 32);
	public Rectangle areadeAtaque = new Rectangle(0, 0, 0, 0);
	
	public int areaSolidaPadraoX, areaSolidaPadraoY;
	public boolean colisaoOn = false;
	public boolean vivo = true;
	public boolean morto = false;
	
	
	public Entidade(GamePanel gp) {
		this.gp = gp;
	}
	public int getEsquerdaX() {
		return mundox + areaSolida.x;
	}
	public int getDireitaX() {
		return mundox + areaSolida.x + areaSolida.width;
	}
	public int getTopoY() {
		return mundoy + areaSolida.y;
	}
	public int getBaseY() {
		return mundoy + areaSolida.y + areaSolida.height;
	}
	public int getColuna() {
		return (mundox + areaSolida.x)/ gp.tileSize;
	}
	public int getLinha() {
		return (mundoy + areaSolida.y)/ gp.tileSize;
	}
	public int getDistanciaX(Entidade alvo) {
		int distanciaX = Math.abs( mundox - alvo.mundox);
		return distanciaX;
	}
	public int getDistanciaY(Entidade alvo) {
		int distanciaY = Math.abs( mundoy - alvo.mundoy);
		return distanciaY;
	}
	public int distanciaBloco(Entidade alvo) {
		int distanciaBloco = (getDistanciaX(alvo) + getDistanciaY(alvo))/gp.tileSize;
		return distanciaBloco;
	}
	public int getGoalCol(Entidade alvo) {
		int goalCol = (alvo.mundox +  alvo.areaSolida.x)/gp.tileSize;
		return goalCol;
	}
	public int getGoalRow(Entidade alvo) {
		int goalRow = (alvo.mundoy +  alvo.areaSolida.y)/gp.tileSize;
		return goalRow;
	}
	public void setLoot(Entidade loot) {};
	public void setAcao() {}
	
	public void aiCriaturas() {}
	
	public void dialogosConteudo() {
		
		if (dialogos[dialogoIndex] == null) {
			dialogoIndex = 0;
		}
		gp.ui.dialogoAtual = dialogos[dialogoIndex];
		dialogoIndex++;
		
		switch(gp.jogador.direcao) {
		case "Cima":
			direcao = "Baixo";
			break;
		case "Baixo":
			direcao = "Cima";
			break;
		case "Esquerda":
			direcao = "Direita";
			break;
		case "Direita":
			direcao = "Esquerda";
			break;	
		}	
	}
	public void interagir() {
		
		
	}
	public boolean usar(Entidade entidade) {return false;}
	public void checarDrop() {}
	public void dropItem(Entidade itensDropados) {
		
		for( int i = 0; i < gp.obj[1].length; i++) {
			if (gp.obj[gp.mapaAtual][i] == null) {
				gp.obj[gp.mapaAtual][i] = itensDropados;
				gp.obj[gp.mapaAtual][i].mundox = mundox; // <-- Aqui é para identificar a localizacao de onde o monstro morreu, para o item nao dropar aleatoriamente em outra coordenada
				gp.obj[gp.mapaAtual][i].mundoy = mundoy;
				break; // <-- IMPORTANTE, nao esquecer o break caso contrario o item ira ocupar todos os slots vazios do loop
			}
		}
	}
	
	public Color getCordasParticulas() {
		
		Color cor = null;
		return cor;
	}
	
	public int getTamanhodasParticulas() {
		
		int tamanho = 0; // <-- 6 pixels
		return tamanho;
	}
	public int getVelocidadedasParticulas() {
		
		int velocidade = 0;
		return velocidade;
	}
	public int getVidadasParticulas() {
		
		int vidaMaxima = 0; // <--Assim como em outros objetos, a vida representa o tempo dela em tela, nao uma condicao de vivo ou morto.
		return vidaMaxima;
	}
	
	public void gerarParticulas(Entidade gerador, Entidade alvo) {
		
		Color cor = gerador.getCordasParticulas();
		int tamanho = gerador.getTamanhodasParticulas();
		int velocidade = gerador.getVelocidadedasParticulas();
		int vidaMaxima = gerador.getVidadasParticulas();
		
		Particulas p1 = new Particulas(gp, alvo, cor, tamanho, velocidade, vidaMaxima, -2, -1);
		Particulas p2 = new Particulas(gp, alvo, cor, tamanho, velocidade, vidaMaxima, 2, -1);
		Particulas p3 = new Particulas(gp, alvo, cor, tamanho, velocidade, vidaMaxima, -2, 1);
		Particulas p4 = new Particulas(gp, alvo, cor, tamanho, velocidade, vidaMaxima, 2, 1);
		gp.listadeParticulas.add(p1);
		gp.listadeParticulas.add(p2);
		gp.listadeParticulas.add(p3);
		gp.listadeParticulas.add(p4);
	}
	
	public void checarColisao() {
		
		colisaoOn = false;
		gp.cColisao.checarBloco(this);
		gp.cColisao.checkObjeto(this, false);
		gp.cColisao.checarEntidade(this, gp.npc);
		gp.cColisao.checarEntidade(this, gp.criaturas);
		gp.cColisao.checarEntidade(this, gp.blocosInt);
		boolean contatoJogador = gp.cColisao.checarJogador(this);
		
		if (tipo == tipo_criatura && contatoJogador == true) {
		
			danoJogador(ataque);
		}
		
	}
	public void update() {
		
		//O if e else verifica e ativa o knockback na entidade, alem de verificar se é um bloco solido para que a criatura nao atravesse por ele
		if(knockBack == true) {
			
			checarColisao();
			
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
		else if( atacando == true) {
			atacando();
		}
		else {
			
			setAcao();
			checarColisao();
			//Se a colisao for FALSE, é intangivel.
			if (colisaoOn == false) {
				
				switch(direcao) {
				case "Cima":
					mundoy -= velocidade;break;
					
				case "Baixo":
					mundoy += velocidade;break;
					
				case "Esquerda":
					mundox -= velocidade;break;
					
				case "Direita":
					mundox += velocidade;break;			
				}
			}
			contadorDeSprites++;
			if (contadorDeSprites > 12) {  // Ajuste esse valor para controlar a velocidade da animação
			    numeroDoSprite++;
			    if (numeroDoSprite > 3) {  // Agora deve alternar entre 1, 2, e 3
			        numeroDoSprite = 1;  // Reiniciar para o primeiro sprite
			    }
			    contadorDeSprites = 0;  // Redefinir o contador
			}
		}
	
	if( invencibilidade == true) {
		invencibilidadeContador++;
		if(invencibilidadeContador > 40) {
			invencibilidade = false;
			invencibilidadeContador = 0;
			}
		}
	if ( possibilidadeAtirarContador < 30) {
		possibilidadeAtirarContador++;
		}
	if ( equilibrio == true) {
		equilibrioContador++;
		if ( equilibrioContador > 60) {
			equilibrio = false;
			equilibrioContador = 0;
			}
		}
	}
	public void checarseAtacaOuNao(int chance, int direto, int horizontal) {
		
		boolean noAlvo = false;
		int xDis = getDistanciaX(gp.jogador);
		int yDis = getDistanciaY(gp.jogador);
		
		switch(direcao) {
		
		case"Cima": 
			if( gp.jogador.mundoy < mundoy && yDis < direto && xDis < horizontal) {
				noAlvo = true;
			}
			break;
		case"Baixo": 
			if( gp.jogador.mundoy > mundoy && yDis < direto && xDis < horizontal) {
				noAlvo = true;
			}
			break;
		case"Esquerda": 
			if( gp.jogador.mundox < mundox && xDis < direto && yDis < horizontal) {
				noAlvo = true;
			}
			break;
		case"Direita": 
			if( gp.jogador.mundox > mundox && xDis < direto && yDis < horizontal) {
				noAlvo = true;
			}
			break;	
		}
		
		if ( noAlvo == true) {
			int i = new Random().nextInt(chance);
			if( i == 0) {
				atacando = true;
				numeroDoSprite = 1;
				contadorDeSprites = 0;
				possibilidadeAtirarContador = 0;
			}
		}
	}
	public void checarSeParouDeSeguir(Entidade alvo, int distancia, int chance) {
		
		if ( distanciaBloco(alvo) > distancia) {
			int i = new Random().nextInt(chance);
			if( i == 0) {
				noCaminho = false;
			}
		}
	}
	public void checarSeComecouSeguir(Entidade alvo, int distancia, int chance) {
		
		if ( distanciaBloco(alvo) < distancia) {
			int i = new Random().nextInt(chance);
			if( i == 0) {
				noCaminho = true;
			}
		}
	}
	public void checarProjeteis(int chance, int intervalo) {
		
		int i = new Random().nextInt(chance);
		if ( i == 0 && projetil.vivo == false && possibilidadeAtirarContador == intervalo) {
			
			projetil.set(mundox, mundoy, direcao, true, this);
				
			for ( int ii = 0; ii < gp.projeteis[1].length; ii++){
				if ( gp.projeteis[gp.mapaAtual][ii] == null) {
					gp.projeteis[gp.mapaAtual][ii] = projetil;
					break;
				}
			}

			possibilidadeAtirarContador = 0;
		}
	}
	public void getDirecaoAleatoria() {
		
		contadorParaAcoes++;
		
		if(contadorParaAcoes == 180) {
			
			Random random = new Random();
			int i = random.nextInt(100)+1;
			if(i <= 25) {
				direcao = "Cima";
			}
			if(i > 25 && i <= 50) {
				direcao = "Baixo";
			}
			if(i > 50 && i <= 75) {
				direcao = "Esquerda";
			}
			if(i > 75 && i <= 100) {
				direcao = "Direita";
			}
			
			contadorParaAcoes = 0;
		}
	}
	public void knockBack(Entidade alvo, Entidade atacante,  int knockBackPoder) {
		
		this.atacante = atacante;
		alvo.knockbackDirecao = atacante.direcao;
		alvo.velocidade += knockBackPoder;
		alvo.knockBack = true;
		
	}
	public String getDirecaoOposta(String direcao) {
		
		String direcaoOposta = "";
		
		switch(direcao) {
		
		case "Cima": direcaoOposta = "Baixo"; break;
		case "Baixo": direcaoOposta = "Cima"; break;
		case "Esquerda": direcaoOposta = "Direita"; break;
		case "Direita": direcaoOposta = "Esquerda"; break;
		}
		return direcaoOposta;
	}
	public void atacando() {
		
		contadorDeSprites++;
		if(contadorDeSprites <= 2) {
			numeroDoSprite = 1;
		}
		if (contadorDeSprites > 5 && contadorDeSprites <= 10) {
			numeroDoSprite = 2;
			
			if (contadorDeSprites > 2 && contadorDeSprites <= 11) {
				numeroDoSprite = 3;
			}
			
			//Essa parte ficou um pouco confusa, nao sei se há o que melhorar, mas basicamente troca a area de colisao do personagem para a area do ataque
			// e comparada com a area de colisao da criatura, após isso, retorna a area de colisao para o personagem como era antes
			int atualMundoX = mundox;
			int atualMundoY = mundoy;
			int areaSolidaLargura = areaSolida.width;
			int areaSolidaAltura = areaSolida.height;
			
			switch( direcao ) {
			case "Cima": mundoy -= areadeAtaque.height; break;
			case "Baixo": mundoy += areadeAtaque.height; break;
			case "Esquerda": mundox -= areadeAtaque.width; break;
			case "Direita": mundox += areadeAtaque.width; break;
			}
			areaSolida.width = areadeAtaque.width;
			areaSolida.height = areadeAtaque.height;
			
			if( tipo == tipo_criatura) {
				if( gp.cColisao.checarJogador(this) == true) {
					danoJogador(ataque);
				}
			}
			else {
				
				int criaturasIndex = gp.cColisao.checarEntidade(this, gp.criaturas);
				gp.jogador.danodaCriatura(criaturasIndex, this, ataque, armaAtual.knockBackPoder);
				
				int blocosIntIndex = gp.cColisao.checarEntidade(this, gp.blocosInt);
				gp.jogador.danoBlocoInterativo(blocosIntIndex);
				
				int projetilIndex =  gp.cColisao.checarEntidade(this, gp.projeteis);
				gp.jogador.danoProjeteis(projetilIndex);
				
			}		
			//Apos verificar a colisao, volta aos valores padroes
			mundox = atualMundoX;
			mundoy = atualMundoY;
			areaSolida.width = areaSolidaLargura;
			areaSolida.height = areaSolidaAltura;
		}
		if (contadorDeSprites > 20) { //Aqui é a velocidade de ataque, quanto menor o numero, mais rapido o ataque é
			
			numeroDoSprite = 1;
			contadorDeSprites = 0;
			atacando = false;
			
		}			
	}
	public void danoJogador(int ataque) {
		
		if( gp.jogador.invencibilidade == false) {
			
			int dano = ataque - gp.jogador.defesa;
			
			//Verificar direcao oposta do atacante
			String direcaoDeBloqueio = getDirecaoOposta(direcao);
			
			if( gp.jogador.bloqueando == true && gp.jogador.direcao.equals(direcaoDeBloqueio)) {
				
				//Parry
				if( gp.jogador.bloqueioContador < 10) {
					dano = 0;
					gp.playSFX(16);
					knockBack(this, gp.jogador, knockBackPoder);
					equilibrio = true;
					contadorDeSprites =- 60;
				}
				else {
					dano /= 3;
					gp.playSFX(15);
				}		
			}
			else {
				gp.playSFX(5);
				if(dano < 1) {
					dano = 1;
				}		
			}
			
			if ( dano != 0) {
				gp.jogador.transparente = true;
				knockBack(gp.jogador, this, knockBackPoder);
			}
			gp.jogador.vida -= dano;
			gp.jogador.invencibilidade = true;
		}
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage imagem = null;
		int telax = mundox - gp.jogador.mundox + gp.jogador.telax;
		int telay = mundoy - gp.jogador.mundoy + gp.jogador.telay;
		
		if ( mundox + gp.tileSize > gp.jogador.mundox - gp.jogador.telax &&
			 mundox - gp.tileSize < gp.jogador.mundox + gp.jogador.telax &&
			 mundoy + gp.tileSize > gp.jogador.mundoy - gp.jogador.telay &&
			 mundoy - gp.tileSize < gp.jogador.mundoy + gp.jogador.telay) {
			
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
				break;
			}
			
			/*
			 * switch (direcao) { case "Cima": if (numeroDoSprite == 1) { imagem = cima1; }
			 * else if (numeroDoSprite == 2) { imagem = cima2; } else if (numeroDoSprite ==
			 * 3) { imagem = cima3; } break;
			 * 
			 * case "Baixo": if (numeroDoSprite == 1) { imagem = baixo1; } else if
			 * (numeroDoSprite == 2) { imagem = baixo2; } else if (numeroDoSprite == 3) {
			 * imagem = baixo3; } break;
			 * 
			 * case "Esquerda": if (numeroDoSprite == 1) { imagem = esquerda1; } else if
			 * (numeroDoSprite == 2) { imagem = esquerda2; } else if (numeroDoSprite == 3) {
			 * imagem = esquerda3; } break;
			 * 
			 * case "Direita": if (numeroDoSprite == 1) { imagem = direita1; } else if
			 * (numeroDoSprite == 2) { imagem = direita2; } else if (numeroDoSprite == 3) {
			 * imagem = direita3; } break; }
			 */
			
			//Barra de vida das criaturas
			if( tipo == 2 && barraHPOn == true) {
				
				double escala = (double)gp.tileSize/vidaMaxima;
				double valorHPbar = escala * vida; // <- Aqui é para determinar o tamanho da barra de vida de acordo com a vida que o monstro possui, ou quando decai
				
				g2.setColor(new Color(35, 35, 35));
				g2.fillRect(telax-1, telay -11 , gp.tileSize+2, 12);
				
				g2.setColor(new Color(255, 0, 40));
				g2.fillRect(telax, telay -10 , (int)valorHPbar, 10);
				
				barraHPContador++;
				//Aqui significa que se voce nao estiver em batalha em 600 frames (10 segundos) a barra desaparece
				if(barraHPContador > 600) {
					barraHPContador = 0;
					barraHPOn = false;
				}
			}
			
			if( invencibilidade == true) {
				barraHPOn = true;
				barraHPContador = 0;
				mudarTransparencia(g2, 0.6f);		
			}
			
			if(morto == true) {
				mortoAnimacao(g2);
			}
			g2.drawImage(imagem, tempTelax, tempTelay, null);
			mudarTransparencia(g2, 1f);	
		}	
	}
	//Aqui um pequeno efeito visual para fazer a criatura piscar
	public void mortoAnimacao(Graphics2D g2) {
		
		mortoContador++;
		
		int i = 5;
		
		if(mortoContador <= i) {mudarTransparencia(g2, 0f);}
		if(mortoContador > i && mortoContador <= i*2) {mudarTransparencia(g2, 0.6f);}
		if(mortoContador > i*2 && mortoContador <= i*3) {mudarTransparencia(g2, 0f);}
		if(mortoContador > i*3 && mortoContador <= i*4) {mudarTransparencia(g2, 0.6f);}
		if(mortoContador > i*4 && mortoContador <= i*5) {mudarTransparencia(g2, 0f);}
		if(mortoContador > i*5 && mortoContador <= i*6) {mudarTransparencia(g2, 0.6f);}
		if(mortoContador > i*6 && mortoContador <= i*7) {mudarTransparencia(g2, 0f);}
		if(mortoContador > i*7 && mortoContador <= i*8) {mudarTransparencia(g2, 0.6f);}
		if( mortoContador > i*8) {
			
			vivo = false;
		}	
	}
	
	public void mudarTransparencia(Graphics2D g2, float valoresAlpha) {
			
	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, valoresAlpha));
			
	}
	
	
	public BufferedImage setup (String caminhoDaImagem, int largura, int altura) {
		
		Utilitarios utl = new Utilitarios();
		BufferedImage imagem = null;
		
		try {
			
			imagem = ImageIO.read(getClass().getResourceAsStream(caminhoDaImagem + ".png"));
			imagem = utl.escalaDaImagem(imagem, largura, altura);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return imagem;
	}
	
	public void buscarCaminho(int goalCol, int goalRow) {
		
		int comecoColuna = (mundox + areaSolida.x)/gp.tileSize;
		int comecoLinha = (mundoy + areaSolida.y)/gp.tileSize;
		
		gp.pFinder.setNodes(comecoColuna, comecoLinha, goalCol, goalRow, this);
		
		if ( gp.pFinder.busca() == true) {
			
			//Verifica os proximo ponto X e Y nas coordenadas do mapa
			int nextX = gp.pFinder.pathList.get(0).coluna * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).linha * gp.tileSize;
			
			//Verifica a area solida das entidades
			int enEsqX = mundox + areaSolida.x;
			int enDirX = mundox + areaSolida.x + areaSolida.width;
			int enTopY = mundoy + areaSolida.y;
			int enBaseY = mundoy + areaSolida.y + areaSolida.height;
			
			if( enTopY > nextY && enEsqX >= nextX && enDirX < nextX + gp.tileSize) {
				direcao = "Cima";
			}
			else if( enTopY < nextY && enEsqX >= nextX && enDirX < nextX + gp.tileSize) {
				direcao = "Baixo";
			}
			else if ( enTopY >= nextY && enBaseY < nextY + gp.tileSize) {
				
				if( enEsqX > nextX) {
					direcao = "Esquerda";
				}
				if ( enEsqX < nextX) {
					direcao = "Direita";
				}
			}
			else if ( enTopY > nextY && enEsqX > nextX) {
				direcao = "Cima";
				checarColisao();
				if ( colisaoOn == true) {
					direcao = "Esquerda";
				}
			}
			else if( enEsqX > nextY && enEsqX < nextX) {
				direcao = "Cima";
				checarColisao();
				if ( colisaoOn == true) {
					direcao = "Direita";
				}
			}
			else if(enTopY < nextY && enEsqX > nextX ) {
				direcao = "Baixo";
				checarColisao();
				if ( colisaoOn == true) {
					direcao = "Esquerda";
				}
			}
			else if(enTopY < nextY && enEsqX < nextX ) {
				direcao = "Baixo";
				checarColisao();
				if ( colisaoOn == true) {
					direcao = "Direita";
				}
			}
			
			//Caso o npc alcance o destino, finaliza o rastreamento             // IMPORTANTE!!!  CASO QUEIRA QUE ALGUM NPC O SIGA, ELE DEVE ESTAR FORA DESSES PARAMETROS
		//	int proxColuna = gp.pFinder.pathList.get(0).coluna;					// CASO CONTRÁRIO, ASSIM QUE VOCE FALAR COM ELE, ELE JA ENTENDE QUE ATINGIU ONDE DEVERIA CHEGAR
		//	int proxLinha = gp.pFinder.pathList.get(0).linha;					// FICANDO NESSE LOOP E NAO TE SEGUINDO
			
		//	if ( proxColuna == goalCol && proxLinha == goalRow) {
		//		noCaminho = false;
		//	}
		}		
	}
	
	public int getDetectar(Entidade usuario, Entidade alvo[][], String alvoNome) {
		
		int index = 999;
		
		//Checar os objetos em volta do personagem
		int proxMundoX = usuario.getEsquerdaX();
		int proxMundoY = usuario.getTopoY();
		
		switch (usuario.direcao) {
		case "Cima": proxMundoY = usuario.getTopoY() - gp.jogador.velocidade; break;
		case "Baixo": proxMundoY = usuario.getBaseY() + gp.jogador.velocidade; break;
		case "Esquerda": proxMundoX = usuario.getEsquerdaX() - gp.jogador.velocidade; break;
		case "Direita": proxMundoX =  usuario.getDireitaX() + gp.jogador.velocidade; break;
		}
		
		int coluna = proxMundoX/gp.tileSize;
		int linha = proxMundoY/gp.tileSize;
		
		for ( int i = 0; i < alvo[1].length; i++) {
			if ( alvo[gp.mapaAtual][i] != null) {
				if ( alvo[gp.mapaAtual][i].getColuna() == coluna &&
						alvo[gp.mapaAtual][i].getLinha() == linha &&
						alvo[gp.mapaAtual][i].nome.equals(alvoNome)) {
					
					index = i;
					break;
				}
			}
		}
		return index;
	}
}

