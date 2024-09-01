package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import ambiente.AmbienteManipulador;
import bloco.ManipuladorDeBloco;
//import bloco.Mapa;
import bloco_interativo.BlocosInterativos;
import data.SaveLoad;
import entidade.Entidade;
import entidade.Jogador;


public class GamePanel extends JPanel implements Runnable{
	
	//Configs da tela
	final int originalTileSize = 16; //Aqui é onde determinei o tamanho dos objetos, personagem e tiles, ou seja 16x16 pixels
	final int scale = 3;			// Aqui basicamente pega o tamanho 16x16 e transforma em 48x48 para melhor visualizacao
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	// Aqui transformara a tela jogável em 960x576 pixels
	public final int telaLargura = tileSize * maxScreenCol;
	public final int telaAltura = tileSize * maxScreenRow;
	
	
	//Parametros do mapa global
	public  int tamanhoMaximoColuna; //<-- Tamanho do mapa, ou seja, 100x100
	public  int tamanhoMaximoLinha;
	public final int maxMapas = 50; // <-- numero maximo de mapas, para transicao de mapas
	public int mapaAtual = 0; //<-- O numero atual do mapa
	
	
	
	//Para Full Screen
	int telaLargura2 = telaLargura;
	int telaAltura2 = telaAltura;
	BufferedImage tempTela;
	Graphics2D g2;
	public boolean fullScreenOn = false;
	
	
	//Definir FPS (Frames por segundo)
	int fps = 60;
	
	//Sistema e infos de tela
	public ManipuladorDeBloco ManiBloco = new ManipuladorDeBloco(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sons efeitoSonoro = new Sons();
	Sons musica = new Sons();
	public ChecarColisao cColisao = new ChecarColisao(this);
	public DefinicaoDosObjetos ddObjetos = new DefinicaoDosObjetos(this);
	public UI ui = new UI(this);
	public EventosManipulador evManipulador = new EventosManipulador(this);
	Config config = new Config(this);
	public PathFinder pFinder = new PathFinder(this);
	AmbienteManipulador ambManipulador = new AmbienteManipulador(this);
	SaveLoad saveLoad = new SaveLoad (this);
//	Mapa mapa = new Mapa(this);
	Thread gameThread;
	
	//Entidade e Objetos
	public Jogador jogador = new Jogador(this, keyH);
	public Entidade obj[][] = new Entidade[maxMapas][50];
	public Entidade npc[][] = new Entidade[maxMapas][50];
	public Entidade criaturas[][] = new Entidade[maxMapas][50]; //<- O numero do array nao significa o total de monstrar que podemos criar, mas sim a quantidade maxima que pode ser exibida
	public Entidade projeteis[][] = new Entidade[maxMapas][50];
	//public ArrayList<Entidade> listadeProjeteis = new ArrayList<>();
	public ArrayList<Entidade> listadeParticulas = new ArrayList<>();
	public BlocosInterativos blocosInt[][] = new BlocosInterativos[maxMapas][50];
	ArrayList<Entidade> listadeEntidade = new ArrayList<>();
	
	
	/*Game State <- Aqui é onde controlamos os estado do jogo, ou seja, as interacoes em diferentes tela onde uma tecla pode funcionar para algo durante o jogo, porem em outro estado de jogo
	funcione para outra coisa. como no menu, no pause, e etc*/
	public int gameState;
	public final int tituloState = 0; // <- Tela inicial do jogo
	public final int playState = 1; // <- Tela de jogo, onde controlamos pelo mapa e etc
	public final int pauseState = 2; // <- Quando pausamos o jogo, no meu caso, com a tecla P, jogo fica congelado
	public final int dialogoState = 3; // <- Esse é um estado de tela de quando falamos com npc, ou que aparecem dialogos na tela
	public final int statusPersonagem = 4; // <= Aqui é um state para exibir os atributos do personagem
	public final int optionsState = 5; // <-- Aqui é a tela de opcoes
	public final int gameOver = 6;
	public final int efeitoTransicao = 7; //<--Efeito de quando vamos de um mapa para outro
	public final int telaMercador = 8;
	public final int dormirState = 9;
	


	public GamePanel() {
		
		this.setPreferredSize(new Dimension(telaLargura, telaAltura));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupDoJogo() {
		
		ddObjetos.setObjetos();
		ddObjetos.setNPC();
		ddObjetos.setCriatura();
		ddObjetos.setBlocosInterativos();
		ambManipulador.setup();
		gameState = tituloState;
		
		//Essa parte é um pouco complexa, mas basicamente, para podermos deixar em fullScreen, evitando o efeito esticado do jogo, exibimos primeiramente as informacoes nessa TempTela
		//onde o usuário nao ve, como em um bastidor, apos isso, na tela do monitor, basicamente é uma exibicao em duas etapadas, diferente do modo padrao, que exibe diretamente junto ao processamento
		tempTela = new BufferedImage(telaLargura, telaAltura, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)tempTela.getGraphics();
		
		if(fullScreenOn == true) {
			fullScreen();
		}	
	}
	
	public void resetGame(boolean restart) {
		
		jogador.setPosicaoPadrao();
		jogador.restaurarStatus();
		ddObjetos.setNPC();
		ddObjetos.setCriatura();
		
		if( restart == true) {
			
			jogador.valoresPadrao();
			ddObjetos.setObjetos();
			ddObjetos.setBlocosInterativos();
			ambManipulador.iluminacao.resetarDia();
		}	
	}
	public void fullScreen() {
		
		//Identificar as informacoes de tamanho de tela e resolucao do dispositivo de quem está jogando
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.Janela);
		
		//Identificar a altura e largura da janela em full screen
		telaLargura2 = Main.Janela.getWidth();
		telaAltura2 = Main.Janela.getHeight();
		
		
	}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {	
		
		double intervaloDeTempo = 1000000000/fps; //Aqui é o calculo para fixar que o jogo rode a 60 FPS, ou seja, 1 Bilhao(nanosegundos) / 60.
		double delta = 0;
		long ultimoTempo = System.nanoTime();
		long tempoAtual;
		
		while(gameThread != null) {
			
			tempoAtual = System.nanoTime();
			delta += (tempoAtual - ultimoTempo) / intervaloDeTempo;
			ultimoTempo = tempoAtual;
			if ( delta >= 1) {
				update(); //Update e repaint é o que fará o loop do jogo continuar, ou seja, sempre atualizar informacoes da posicao do personagens e outros componentes do jogo			
				exibirnaTempTela(); //Joga tudo pro buffer de imagem
				exibirNaTela(); // joga tudo pra tela =D
				delta--;
			}	
		}
	}
	
	public void update() {
		
		if(gameState == playState) {
			
			jogador.update();
			
			//NPC
			for(int i = 0; i < npc[1].length; i++) {
				if(npc[mapaAtual][i] != null) {
					npc[mapaAtual][i].update();
				}		
			}
			//Criatura
			for( int i = 0; i < criaturas[1].length; i++) {
				if(criaturas[mapaAtual][i] != null) {
					if(criaturas[mapaAtual][i].vivo == true && criaturas[mapaAtual][i].morto == false) {
						criaturas[mapaAtual][i].update();
					}
					if(criaturas[mapaAtual][i].vivo == false) {
						criaturas[mapaAtual][i].checarDrop();
						criaturas[mapaAtual][i] = null;
					}		
				}
			}
			for( int i = 0; i < projeteis[1].length; i++) {
				if(projeteis[mapaAtual][i] != null) {
					if(projeteis[mapaAtual][i].vivo == true) {
						projeteis[mapaAtual][i].update();
					}
					if(projeteis[mapaAtual][i].vivo == false) {
						projeteis[mapaAtual][i] = null;
					}		
				}
			}
			for( int i = 0; i < listadeParticulas.size(); i++) {
				if(listadeParticulas.get(i) != null) {
					if(listadeParticulas.get(i).vivo == true) {
						listadeParticulas.get(i).update();
					}
					if(listadeParticulas.get(i).vivo == false) {
						listadeParticulas.remove(i);
					}		
				}
			}
			for ( int i = 0; i < blocosInt[1].length; i++) {
				if( blocosInt[mapaAtual][i] != null) {
					blocosInt[mapaAtual][i].update();
				}
			}
			ambManipulador.update();
		}
		if(gameState == pauseState) {
			
			///NADA///
		}	
	}
	public void exibirnaTempTela() {
		
		//DEBUG
		long drawStart = 0;
		if(keyH.mostrarDebug == true) {
			drawStart = System.nanoTime();
		}
				
		//Tela do Titulo
		if(gameState == tituloState) {	
			ui.draw(g2);
		}
		else {
			
		//Blocos (Chao, paredes etc)
		ManiBloco.draw(g2);
					
		//Blocos interativos
		for (int i = 0; i < blocosInt[1].length; i++) {
			if(blocosInt[mapaAtual][i] != null) {
				blocosInt[mapaAtual][i].draw(g2);
			}
		}
							
		//Aqui as entidades, objetos, jogador e etc sao adicionados a uma lista, onde o 0 seria o menor numero do MundoY e o maior numero da lista tambem é o maior do Mundo Y
		listadeEntidade.add(jogador);
					
		for(int i = 0; i < npc[1].length; i++) {
			if(npc[mapaAtual][i] != null) {
				listadeEntidade.add(npc[mapaAtual][i]);
			}
		}
		for(int i = 0; i < obj[1].length; i++) {
			if(obj[mapaAtual][i] != null) {
				listadeEntidade.add(obj[mapaAtual][i]);
			}
		}
					for(int i = 0; i < criaturas[1].length; i++) {
						if(criaturas[mapaAtual][i] != null) {
							listadeEntidade.add(criaturas[mapaAtual][i]);
						}
					}
					for(int i = 0; i < projeteis[1].length; i++) {
						if(projeteis[mapaAtual][i] != null) {
							listadeEntidade.add(projeteis[mapaAtual][i]);
						}
					}
					for(int i = 0; i < listadeParticulas.size(); i++) {
						if(listadeParticulas.get(i) != null) {
							listadeEntidade.add(listadeParticulas.get(i));
						}
					}
					
					Collections.sort(listadeEntidade, new Comparator<Entidade>() {

						@Override
						public int compare(Entidade e1, Entidade e2) {
							
							int resultado = Integer.compare(e1.mundoy, e2.mundoy); // <- Aqui é onde é feita a comparacao das coordenadas Y do mundo
							return resultado;
						}		
					});

			// Aqui chamamos o metodo draw para desenhar as entidade
			for(int i = 0; i < listadeEntidade.size(); i++) {
						listadeEntidade.get(i).draw(g2);
					}
			//Esvaziar a lista
			listadeEntidade.clear();
					
				//Ambiente
			ambManipulador.draw(g2);
			
				//UI
			ui.draw(g2); // <- Aqui é onde desenha a interface do jogo na tela		
		}
				
				//DEBUG
		if(keyH.mostrarDebug == true) {
		long drawEnd = System.nanoTime();
		long passed = drawEnd - drawStart;
	
		g2.setFont(new Font("Arial", Font.PLAIN, 20));
		g2.setColor(Color.WHITE);
		int x = 10;
		int y = 400;
		int lineHeight = 20;
		
		g2.drawString("Mundo X: " + jogador.mundox, x, y); y += lineHeight;
		g2.drawString("Mundo Y: " + jogador.mundoy, x, y); y += lineHeight;
		g2.drawString("Coluna: " + (jogador.mundox + jogador.areaSolida.x)/ tileSize, x, y); y += lineHeight;
		g2.drawString("Linha: " + (jogador.mundoy + jogador.areaSolida.y)/ tileSize, x, y); y += lineHeight;
		g2.drawString("Tempo: " + passed, x, y);
		
		}
	}
	
	public void exibirNaTela() {
		
		Graphics g = getGraphics();
				
		g.drawImage(tempTela, 0, 0, telaLargura2, telaAltura2, null);
		g.dispose();
		
		
	}
	
	public void playMusic(int i) {
		
		musica.setFile(i);
		musica.play();
		musica.loop();	
	}
	
	public void pararMusica() {
		
		musica.stop();	
	}
	
	public void playSFX(int i) {
		
		efeitoSonoro.setFile(i);
		efeitoSonoro.play();
		
	}

}
