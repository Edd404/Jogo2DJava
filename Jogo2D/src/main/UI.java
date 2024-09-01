package main;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import objeto.OBJ_Chave;
import objeto.OBJ_Flecha_UI;
import objeto.OBJ_Moeda;
import objeto.OBJ_Vida;
import entidade.Entidade;

public class UI {
	
	
	GamePanel gp;
	Graphics2D g2;
	Font pixelCommodore, pixelComputer;
	BufferedImage BarradeVida, BarraVazia, BarraMetade, BarraCheia, flechaUICheia, flechaUIVazia, moeda;
	public boolean mensagemOn = false;
	//public String mensagem = "";
	//int temporizadorMensagem = 0;
	ArrayList <String> mensagem = new ArrayList<>();
	ArrayList <Integer> contadorMensagem = new ArrayList<>();
	public boolean jogoFinalizado = false;
	public String dialogoAtual = "";
	public int numeroDeComandos = 0;
	public int teladeTitulo = 0; 
	public int jogadorSlotColuna = 0;
	public int jogadorSlotLinha = 0;
	public int npcSlotColuna = 0;
	public int npcSlotLinha = 0;
	int subTelas = 0;
	int contador = 0;
	public Entidade npc;
	
	
	//Aqui sao as alteracoe de estilo de fonte e imagem de objetos na tela, que serao seguidos de algum texto ou nao.
	public UI(GamePanel gp) {
		this.gp = gp;	
			
		try {
			InputStream is = getClass().getResourceAsStream("/font/Commodore Pixeled.ttf");
			pixelCommodore = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/computer_pixel-7.ttf");
			pixelComputer = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	//HUD E INFOS
		
		Entidade vida = new OBJ_Vida(gp);
		
		BarraCheia = vida.imagem;	
		BarraMetade = vida.imagem2;
		BarraVazia = vida.imagem3;
		Entidade flechaUI = new OBJ_Flecha_UI(gp);
		flechaUICheia = flechaUI.imagem;
		flechaUIVazia = flechaUI.imagem2;
		Entidade imoeda = new OBJ_Moeda(gp);
		moeda = imoeda.baixo1;
	}
	
	public void addMensagem(String texto) {
		
		mensagem.add(texto);
		contadorMensagem.add(0);
		
	}
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		g2.setFont(pixelCommodore);
		g2.setColor(Color.WHITE);
		
		
		//Tela de titulo
		if(gp.gameState == gp.tituloState) {
			
			infosTeladeTitulo();		
		}
		
		//Play State
		if(gp.gameState == gp.playState) {
			
			vidaDoJogador();
			exibirMensagem();
		}
		//Pause
		if(gp.gameState == gp.pauseState) {
			vidaDoJogador();
			telaDePause();		
		}
		//Dialogos
		if(gp.gameState == gp.dialogoState) {
			
			telaDeDialogo();
		}
		//Status do Personagem
		if(gp.gameState == gp.statusPersonagem) {
			telaStatusPersonagem();
			exibirIventario(gp.jogador, true);
		}
		//Menu de opcoes
		if(gp.gameState == gp.optionsState) {
			exibirOpcoes();
		}
		//Game Over
		if(gp.gameState == gp.gameOver) {
			exibirGameOver();
		}
		//Efeito de transicao
		if(gp.gameState == gp.efeitoTransicao) {
			exibirTransicao();
		}
		//Tela de vendas do Mercador	
		if(gp.gameState == gp.telaMercador) {
			exibirtelaMercador();
		}
		//Transicao que é exibida ao dormir com a barraca
		if(gp.gameState == gp.dormirState) {
			exibirtelaDormir();
		}
		
	
	}
	public void vidaDoJogador(){
		//Aqui é onde desenhamos os coracoes de vida na tela, basicamente é a posicao dele, e a vida é divida por 2, pois lembrando que cada 2 é 1 coracao de vida.
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		//Vida vazia
		while (i < gp.jogador.vidaMaxima/2) {
			g2.drawImage(BarraVazia, x, y, null);
			i++;
			x += gp.tileSize;
		}
		
		//Resetar
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		//Atualizacao das informacoes de vida
		while( i < gp.jogador.vida) {
			g2.drawImage(BarraMetade, x, y, null);
			i++;
			if (i < gp.jogador.vida) {
				g2.drawImage(BarraCheia, x, y, null);
				i++;
				x += gp.tileSize;
			}		
		}
		// MANA (Flechas UI)
		x = gp.tileSize/2;
		y = gp.tileSize*2-20;
		i = 0;
		while ( i < gp.jogador.manaMaxima) {
			g2.drawImage(flechaUIVazia, x, y, null);
			i++;
			x += 35;
		}
		//Exibir Mana
		x = gp.tileSize/2;
		y = gp.tileSize*2-20;
		i = 0;
		while ( i < gp.jogador.mana) {
			g2.drawImage(flechaUICheia, x, y, null);
			i++;
			x += 35;
		}	
	}
	
	public void exibirMensagem() {
		
		
		int mensagemx = gp.tileSize;
		int mensagemy = gp.tileSize*4;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,14F));
		
		for( int i = 0; i < mensagem.size(); i++) {
			if(mensagem.get(i) != null) {
				
				g2.setColor(Color.BLACK);
				g2.drawString(mensagem.get(i), mensagemx+2, mensagemy+2);
				g2.setColor(Color.WHITE);
				g2.drawString(mensagem.get(i), mensagemx, mensagemy);
				
				int contador = contadorMensagem.get(i) + 1;
				contadorMensagem.set(i, contador);
				mensagemy += 20;
				
				if(contadorMensagem.get(i) > 160) {
					mensagem.remove(i);
					contadorMensagem.remove(i);
				}
			}
		}			
	}
	public void infosTeladeTitulo() {
		
		if(teladeTitulo == 0) {
			
			//Cor de fundo
			g2.setColor(new Color(0,0,0));
			g2.fillRect(0,0, gp.telaLargura, gp.telaAltura);
			
			//Nome do jogo
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
			String texto = "TESTE";
			int x = localDoTextonaTela(texto);
			int y = gp.tileSize*3;
			
			//Estilo da escrita
			
			g2.setColor(Color.GRAY);
			g2.drawString(texto, x+5, y+5);
			g2.setColor(Color.WHITE);
			g2.drawString(texto, x, y);
			
			//Imagem
			x = gp.telaLargura/2 - (gp.tileSize*2)/2;
			y += gp.tileSize*2;
			g2.drawImage(gp.jogador.baixo2, x, y, gp.tileSize*2, gp.tileSize*2, null);
			
			// Menu
			
			// Carrega a imagem da seta
			BufferedImage imagemseta = null;
			try {
			    imagemseta = ImageIO.read(getClass().getResourceAsStream("/objetos/Right-Arrow.png"));
			} catch (IOException e) {
			    e.printStackTrace();
			}
			

			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
			texto = "NOVO JOGO";
			x = localDoTextonaTela(texto);
			y += gp.tileSize*4;
			g2.drawString(texto, x, y);
			if(numeroDeComandos == 0) {
				g2.drawImage(imagemseta, x - gp.tileSize-10, y-40, gp.tileSize-10, gp.tileSize-10, null);
			}
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
			texto = "CARREGAR JOGO";
			x = localDoTextonaTela(texto);
			y += gp.tileSize;
			g2.drawString(texto, x, y);
			if(numeroDeComandos == 1) {
				g2.drawImage(imagemseta, x - gp.tileSize-10, y-40, gp.tileSize-10, gp.tileSize-10, null);
			}
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
			texto = "SAIR";
			x = localDoTextonaTela(texto);
			y += gp.tileSize;
			g2.drawString(texto, x, y);
			if(numeroDeComandos == 2) {
				g2.drawImage(imagemseta, x - gp.tileSize-10, y-40, gp.tileSize-10, gp.tileSize-10, null);
			}		
		}	
	}
	public void telaDePause() {
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
		String texto = "PAUSE";	
		int x = localDoTextonaTela(texto);
		int y = gp.telaAltura/2;
		
		g2.drawString(texto, x, y);	
	}
	
	public void telaDeDialogo() {
		
		
		//janela de dialogo
		int x = gp.tileSize*3;
		int y = gp.tileSize/2;
		int largura = gp.telaLargura - (gp.tileSize*6);
		int altura = gp.tileSize*4;		
		subJanela(x, y, largura, altura);
		
		//Posicao, tamanho e tipo de texto na caixa de dialogo.
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line: dialogoAtual.split("\n")) {
			g2.drawString(line, x-15, y-10);
			y += 30;
		}	
	}
	
	public void telaStatusPersonagem() {
		
		//Janela de Status
		final int frameX = gp.tileSize-46;
		final int frameY = gp.tileSize;
		final int frameLargura = gp.tileSize*7;
		final int frameAltura = gp.tileSize*10;
		subJanela(frameX, frameY, frameLargura, frameAltura );
		
		//Texto da Janela
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(19F));
		
		int textox = frameX + 20;
		int textoy = frameY + gp.tileSize;
		final int linhaAltura = 24;
		
		//Infos
		g2.drawString("Level", textox, textoy);
		textoy += linhaAltura;
		g2.drawString("Vida", textox, textoy);
		textoy += linhaAltura;
		g2.drawString("Flechas Mágicas", textox, textoy);
		textoy += linhaAltura;
		g2.drawString("Força", textox, textoy);
		textoy += linhaAltura;
		g2.drawString("Agilidade", textox, textoy);
		textoy += linhaAltura;
		g2.drawString("Ataque", textox, textoy);
		textoy += linhaAltura;
		g2.drawString("Defesa", textox, textoy);
		textoy += linhaAltura;
		g2.drawString("Experiência", textox, textoy);
		textoy += linhaAltura;
		g2.drawString("Próximo Level", textox, textoy);
		textoy += linhaAltura;
		g2.drawString("Moedas", textox, textoy);
		textoy += linhaAltura+20;
		g2.drawString("Arma em uso", textox, textoy);
		textoy += linhaAltura+25;
		g2.drawString("Escudo em uso", textox, textoy);
		textoy += linhaAltura;
		
		//Valores
		int cantoX = (frameX + frameLargura) - 30;
		textoy = frameY + gp.tileSize;
		String valor;
		
		
		valor = String.valueOf(gp.jogador.level);
		textox = localDoTextonaTelaAlinharDireita(valor, cantoX);
		g2.drawString(valor, textox, textoy);
		textoy += linhaAltura;
		
		valor = String.valueOf(gp.jogador.vida + "/" + gp.jogador.vidaMaxima);
		textox = localDoTextonaTelaAlinharDireita(valor, cantoX);
		g2.drawString(valor, textox, textoy);
		textoy += linhaAltura;
		
		valor = String.valueOf(gp.jogador.mana + "/" + gp.jogador.manaMaxima);
		textox = localDoTextonaTelaAlinharDireita(valor, cantoX);
		g2.drawString(valor, textox, textoy);
		textoy += linhaAltura;
		
		
		valor = String.valueOf(gp.jogador.forca);
		textox = localDoTextonaTelaAlinharDireita(valor, cantoX);
		g2.drawString(valor, textox, textoy);
		textoy += linhaAltura;
		
		valor = String.valueOf(gp.jogador.agilidade);
		textox = localDoTextonaTelaAlinharDireita(valor, cantoX);
		g2.drawString(valor, textox, textoy);
		textoy += linhaAltura;
		
		valor = String.valueOf(gp.jogador.ataque);
		textox = localDoTextonaTelaAlinharDireita(valor, cantoX);
		g2.drawString(valor, textox, textoy);
		textoy += linhaAltura;
		
		valor = String.valueOf(gp.jogador.defesa);
		textox = localDoTextonaTelaAlinharDireita(valor, cantoX);
		g2.drawString(valor, textox, textoy);
		textoy += linhaAltura;
		
		valor = String.valueOf(gp.jogador.exp);
		textox = localDoTextonaTelaAlinharDireita(valor, cantoX);
		g2.drawString(valor, textox, textoy);
		textoy += linhaAltura;
		
		valor = String.valueOf(gp.jogador.proxLevelExp);
		textox = localDoTextonaTelaAlinharDireita(valor, cantoX);
		g2.drawString(valor, textox, textoy);
		textoy += linhaAltura;
		
		valor = String.valueOf(gp.jogador.moeda);
		textox = localDoTextonaTelaAlinharDireita(valor, cantoX);
		g2.drawString(valor, textox, textoy);
		textoy += linhaAltura;
		
		g2.drawImage(gp.jogador.armaAtual.baixo1, cantoX - gp.tileSize, textoy-14, null);
		textoy += gp.tileSize;
		g2.drawImage(gp.jogador.escudoAtual.baixo1, cantoX - gp.tileSize, textoy-10, null);	
	}
	
	public void exibirIventario(Entidade entidade, boolean cursor) {
		
		
		//Janela
		int janelaX = 0;
		int janelaY = 0;
		int janelaLargura = 0;
		int janelaAltura = 0;
		int slotColuna = 0;
		int slotLinha = 0;
		
		if ( entidade == gp.jogador) {
			//Janela
			 janelaX = gp.tileSize*12;
			 janelaY = gp.tileSize;
			 janelaLargura = gp.tileSize*6;
			 janelaAltura = gp.tileSize*5;	
			 slotColuna = jogadorSlotColuna;
			 slotLinha = jogadorSlotLinha;
		}
		else {
			//Janela
			 janelaX = gp.tileSize*2;
			 janelaY = gp.tileSize;
			 janelaLargura = gp.tileSize*6;
			 janelaAltura = gp.tileSize*5;	
			 slotColuna = npcSlotColuna;
			 slotLinha = npcSlotLinha;
		}

		subJanela(janelaX, janelaY, janelaLargura, janelaAltura);
		
		//Espaco do inventario
		final int slotXinicio = janelaX +20;
		final int slotYinicio = janelaY + 20;
		int slotX = slotXinicio;
		int slotY = slotYinicio;
		int slotTamanho = gp.tileSize+3;
		
		// Exibir itens do inventario
		for (int i = 0; i < entidade.inventario.size(); i++) {
			
			//Aqui é um efeito visual para ficar visivel quais itens estao equipados
			if( entidade.inventario.get(i) == entidade.armaAtual || entidade.inventario.get(i) == entidade.escudoAtual ||
				entidade.inventario.get(i) == entidade.luzAtual	) {
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			
			g2.drawImage(entidade.inventario.get(i).baixo1, slotX, slotY, null);
			
			//Exibir quantidade
			if (entidade == gp.jogador && entidade.inventario.get(i).quantidade > 1) {
				
				g2.setFont(g2.getFont().deriveFont(28f));
				
				int quantidadeX;
				int quantidadeY;
				
				String s = "" + entidade.inventario.get(i).quantidade;
				quantidadeX = localDoTextonaTelaAlinharDireita(s, slotX + 44);
				quantidadeY =  slotY + gp.tileSize;
				
				//Sombra do numero
				g2.setColor(new Color(60,60,60));
				g2.drawString(s, quantidadeX, quantidadeY);
				//Numero
				g2.setColor(new Color(143,188,143));
				g2.drawString(s, quantidadeX-2, quantidadeY-2);
				
			}
			
			slotX += slotTamanho;
			
			if( i == 4 || i == 9 || i == 14) {
				slotX = slotXinicio;
				slotY += slotTamanho;
			}	
		}
		
		//Cursor
		if (cursor == true) {
			int cursorX = slotXinicio + (slotTamanho * slotColuna);
			int cursorY = slotYinicio + (slotTamanho * slotLinha);
			int cursorLargura = gp.tileSize;
			int cursorAltura = gp.tileSize;
			
			//Exibir cursor
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorLargura,cursorAltura, 10, 10);
			
			//Janela de descricao dos itens
			int descJanelaX = janelaX;
			int descJanelaY = janelaY + janelaAltura+10;
			int descJanelaLargura = janelaAltura+50;
			int descJanelaAltura = gp.tileSize*3;
			
			
			//Exibir texto da descricao dos itens
			int textox = descJanelaX + 20;
			int textoy = descJanelaY + gp.tileSize;
			g2.setFont(g2.getFont().deriveFont(13F));
			
			int itemIndex = getIndexdoItemnoSlot(slotColuna, slotLinha);
			if( itemIndex < entidade.inventario.size()) {
				
				subJanela(descJanelaX, descJanelaY, descJanelaLargura,descJanelaAltura );
				for( String line: entidade.inventario.get(itemIndex).descricao.split("\n")) {
					g2.drawString(line, textox, textoy);
					textoy += 32;
				}
			}		
		}		
	}
	public void exibirGameOver() {
		
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0,0, gp.telaLargura, gp.telaAltura);
		
		int x;
		int y;
		String texto;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
		
		texto = "Game Over";
		
		//Sombra do game over
		g2.setColor(Color.BLACK);
		x = localDoTextonaTela(texto);
		y = gp.tileSize*4;
		g2.drawString(texto, x, y);
		
		//Palavra game over
		g2.setColor(Color.WHITE);
		g2.drawString(texto, x-4, y-4);
		
		//Continuar
		g2.setFont(g2.getFont().deriveFont(42F));
		texto = "Continuar?";
		x = localDoTextonaTela(texto);
		y += gp.tileSize*4;
		g2.drawString(texto, x, y);
		if(numeroDeComandos == 0) {
			g2.drawString(">", x-40, y);
		}
		
		//Voltar ao titulo
		texto = "Sair";
		x = localDoTextonaTela(texto);
		y +=  55;
		g2.drawString(texto, x, y);
		if(numeroDeComandos == 1) {
			g2.drawString(">", x-40, y);
		}
	
	}
	public void exibirOpcoes() {
		
		//Texto da Janela
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(30F));
		
		final int janelaX = gp.tileSize*6;
		final int janelaY = gp.tileSize;
		final int frameLargura = gp.tileSize*8;
		final int frameAltura = gp.tileSize*10;
		subJanela(janelaX, janelaY, frameLargura, frameAltura );
		
		switch(subTelas) {
			case 0: opcoes_cima(janelaX, janelaY);break;
			case 1: opcoes_notificacaoFullScreen(janelaX, janelaY); break;
			case 2: opcoes_Controles(janelaX, janelaY); break;
			case 3: opcoes_FecharJogo(janelaX, janelaY); break;
		}	
		
		gp.keyH.enterParaFalar = false;
	}
	public void opcoes_cima(int  janelaX, int janelaY) {
		
		int textoX;
		int textoY;
		
		//Titulo
		String texto = "Opções";
		textoX = localDoTextonaTela(texto);
		textoY = janelaX + gp.tileSize-240;
		g2.drawString(texto, textoX, textoY);
		
		//Tela cheia ON/OFF
		g2.setFont(g2.getFont().deriveFont(24F));
		textoX = janelaX + gp.tileSize-10;
		textoY += gp.tileSize;
		g2.drawString("Tela Cheia", textoX, textoY);
		if(numeroDeComandos == 0) {
			g2.drawString(">", textoX-20, textoY);
			if(gp.keyH.enterParaFalar == true) {
				if(gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				}
				else if(gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subTelas = 1;
			}
		}
		//Musica
		textoY += gp.tileSize;
		g2.drawString("Música", textoX, textoY);
		if(numeroDeComandos == 1) {
			g2.drawString(">", textoX-20, textoY);
		}
		//Efeitos sonoros
		textoY += gp.tileSize;
		g2.drawString("Sons", textoX, textoY);
		if(numeroDeComandos == 2) {
			g2.drawString(">", textoX-20, textoY);
		}
		//Controles
		textoY += gp.tileSize;
		g2.drawString("Controles", textoX, textoY);
		if(numeroDeComandos == 3) {
			g2.drawString(">", textoX-20, textoY);
			if(gp.keyH.enterParaFalar == true) {
				subTelas = 2;
				numeroDeComandos = 0;
			}
		}
		//Fechar Jogo
		textoY += gp.tileSize;
		g2.drawString("Fechar Jogo", textoX, textoY);
		if(numeroDeComandos == 4) {
			g2.drawString(">", textoX-20, textoY);
			if(gp.keyH.enterParaFalar == true) {
				subTelas = 3;
				numeroDeComandos = 0;
			}
		}
		//Voltar
		textoY += gp.tileSize*4-20;
		g2.drawString("Voltar", textoX, textoY);
		if(numeroDeComandos == 5) {
			g2.drawString(">", textoX-20, textoY);
			if(gp.keyH.enterParaFalar == true) {
				gp.gameState = gp.playState;
				numeroDeComandos = 0;
			}
		}	
		
		//Check box da opcao de tela cheia
		textoX = janelaX + gp.tileSize*6;
		textoY = janelaY + gp.tileSize+30;
		g2.drawRect(textoX, textoY, 24, 24);
		if(gp.fullScreenOn == true) {
			g2.fillRect(textoX, textoY, 24, 24);
		}
		
		//Volume da música
		textoY += gp.tileSize;
		g2.drawRect(textoX, textoY, 85, 24); // <-- 85/5 = 17 escalas de volume, ou seja 5x cada escala para dar a barra cheia
		int volumeLargura = 17 * gp.musica.escaladeSom;
		g2.fillRect(textoX, textoY, volumeLargura, 24);
		
		
		//Volume do SFX
		textoY += gp.tileSize;
		g2.drawRect(textoX, textoY, 85, 24);
		volumeLargura = 17 * gp.efeitoSonoro.escaladeSom;
		g2.fillRect(textoX, textoY, volumeLargura, 24);
		
		gp.config.salvarConfig();
	}
	
	public void opcoes_notificacaoFullScreen(int janelaX, int janelaY) {
		
		g2.setFont(g2.getFont().deriveFont(24F));
		int textoX = janelaX + gp.tileSize;
		int textoY = janelaY + gp.tileSize*2;
		
		dialogoAtual = "Reinicie o jogo \npara aplicar as \nmudanças!";
		
		for( String line: dialogoAtual.split("\n")) {
			g2.drawString(line, textoX, textoY);
			textoY +=40;
		}
		
		//Voltar
		textoY = janelaY-5 + gp.tileSize*10-20;
		g2.drawString("Voltar", textoX, textoY);
		if(numeroDeComandos == 0) {
			g2.drawString(">", textoX-20, textoY);
			if(gp.keyH.enterParaFalar == true) {
				subTelas = 0;
			}
		}	
	}
	public void opcoes_Controles(int janelaX, int janelaY) {
		
		
		int textoX;
		int textoY;
		
		String texto = "Controles";
		g2.setFont(g2.getFont().deriveFont(18F));
		textoX = localDoTextonaTela(texto);
		textoY = janelaY + gp.tileSize;
		g2.drawString(texto, textoX, textoY);
		
		textoX = janelaX + gp.tileSize;
		textoY += gp.tileSize;
		g2.drawString("Movimento:", textoX, textoY); textoY += gp.tileSize;
		g2.drawString("Confirmar/Atacar:", textoX, textoY); textoY += gp.tileSize;
		g2.drawString("Atirar:", textoX, textoY); textoY += gp.tileSize;
		g2.drawString("Inventário:", textoX, textoY); textoY += gp.tileSize;
		g2.drawString("Pause:", textoX, textoY); textoY += gp.tileSize;
		g2.drawString("Opções:", textoX, textoY); textoY += gp.tileSize;
		
		textoX = janelaX + gp.tileSize*4;
		textoY = janelaY + gp.tileSize*2;
		
		g2.drawString("W A S D", textoX-5, textoY); textoY += gp.tileSize;
		g2.drawString("Enter", textoX+95, textoY); textoY += gp.tileSize;
		g2.drawString("E", textoX-45, textoY); textoY += gp.tileSize;
		g2.drawString("C", textoX+10, textoY); textoY += gp.tileSize;
		g2.drawString("P", textoX-60, textoY); textoY += gp.tileSize;
		g2.drawString("ESC", textoX-45, textoY); textoY += gp.tileSize;
		
		//Voltar
		textoX = janelaX + gp.tileSize;
		textoY = janelaY + gp.tileSize*9;
		g2.drawString("Voltar", textoX, textoY);
		if(numeroDeComandos == 0) {
			g2.drawString(">", textoX-20, textoY);
			if(gp.keyH.enterParaFalar == true) {
				subTelas = 0;
				numeroDeComandos = 3;
			}
		}	
	}
	public void opcoes_FecharJogo(int janelaX, int janelaY) {
		
		int textoX = janelaX + gp.tileSize;
		int textoY = janelaY + gp.tileSize*3;
		g2.setFont(g2.getFont().deriveFont(20F));
		dialogoAtual = "Tem certeza que \ndeseja sair do jogo?";
		
		for(String line: dialogoAtual.split("\n")) {
			g2.drawString(line, textoX, textoY);
			textoY += 40;
		}
		//SIM
		String texto = "Sim";
		textoX = localDoTextonaTela(texto);
		textoY += gp.tileSize*3;
		g2.drawString(texto, textoX, textoY);
		if(numeroDeComandos == 0) {
			g2.drawString(">", textoX-20, textoY);
			if( gp.keyH.enterParaFalar == true) {
				subTelas = 0;
				gp.gameState = gp.tituloState;
				gp.resetGame(true);
			}
		}
			
		//NAO
		texto = "Não";
		textoX = localDoTextonaTela(texto);
		textoY += gp.tileSize;
		g2.drawString(texto, textoX, textoY);
		if(numeroDeComandos == 1) {
			g2.drawString(">", textoX-20, textoY);
			if( gp.keyH.enterParaFalar == true) {
				subTelas = 0;
				numeroDeComandos = 4;
			}
		}	
	}
	
	public void exibirTransicao() {
		
		contador++;
		g2.setColor(new Color(0, 0, 0, contador*5));
		g2.fillRect(0, 0, gp.telaLargura, gp.telaAltura);
		if( contador == 50) {
			contador = 0;
			gp.gameState = gp.playState;
			gp.mapaAtual = gp.evManipulador.tempMapa;
			gp.jogador.mundox = gp.tileSize * gp.evManipulador.tempColuna;
			gp.jogador.mundoy = gp.tileSize * gp.evManipulador.tempLinha;
			gp.evManipulador.eventoPassadoX =  gp.jogador.mundox;
			gp.evManipulador.eventoPassadoY =  gp.jogador.mundoy;
			
		}
	}
	
	public void exibirtelaMercador() {
		
		switch(subTelas) {
		case 0 : mercador_Selecionar(); break;
		case 1 : mercador_Comprar(); break;
		case 2 : mercador_Vender(); break;
		}
		gp.keyH.enterParaFalar = false;			
	}
	
	public void mercador_Selecionar() {
		
		
		telaDeDialogo();
		
		//Exibir mini-janela
		int x = gp.tileSize *15;
		int y = gp.tileSize*4;
		int largura = gp.tileSize*3;
		int altura = (int)(gp.tileSize * 3.5);
		subJanela(x, y, largura, altura);
		
		//Textos
		x += gp.tileSize-20;
		y += gp.tileSize;
		g2.drawString("Comprar", x, y);
		if(numeroDeComandos == 0) {
			g2.drawString(">", x-15, y);
			if( gp.keyH.enterParaFalar == true) {
				subTelas = 1;
			}
		}
		y += gp.tileSize;
		
		g2.drawString("Vender", x, y);
		if(numeroDeComandos == 1) {
			g2.drawString(">", x-15, y);
			if( gp.keyH.enterParaFalar == true) {
				subTelas = 2;
			}
		}
		y += gp.tileSize;
		
		g2.drawString("Sair", x, y);
		if(numeroDeComandos == 2) {
			g2.drawString(">", x-15, y);
			if( gp.keyH.enterParaFalar == true) {
				numeroDeComandos = 0;
				gp.gameState = gp.dialogoState;
				dialogoAtual = "Volte sempre =)";
			}
		}			
	}
	
	public void mercador_Comprar() {
		
		
		//Exibir inventário do jogador
		exibirIventario(gp.jogador, false);
		//Exibir Inventário do NPC
		exibirIventario(npc, true);
		
		//Janela de dica
		int x = gp.tileSize*2;
		int y = gp.tileSize*9+20;
		int largura = gp.tileSize*6;
		int altura = gp.tileSize*2;
		subJanela(x, y, largura, altura);
		g2.drawString("[ESC] Volta", x+24, y+60);
		
		//Janela das moedas do jogador
		x = gp.tileSize*12;
		y = gp.tileSize*9+20;
		largura = gp.tileSize*6;
		altura = gp.tileSize*2;
		subJanela(x, y, largura, altura);
		g2.drawString("Suas moedas: " + gp.jogador.moeda, x+24, y+60);
		
		//Janela de valores dos itens vendidos pelo NPC
		int itemIndex = getIndexdoItemnoSlot(npcSlotColuna, npcSlotLinha);
		if ( itemIndex < npc.inventario.size()) {
			
			x = (int)(gp.tileSize*5.5);
			y = (int)(gp.tileSize*5.5);
			largura = (int)(gp.tileSize*2.5);
			altura = gp.tileSize;
			subJanela(x, y, largura, altura);
			g2.drawImage(moeda, x+10, y+13, 32, 32, null); // <- Desenho da moeda na caixa de venda
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
			int preco = npc.inventario.get(itemIndex).precos;
			String texto = "" + preco;
			x = localDoTextonaTelaAlinharDireita(texto, gp.tileSize*8-20);
			g2.drawString(texto, x, y+35);
			
			//Comprar item
			if ( gp.keyH.enterParaFalar == true) {
				if( npc.inventario.get(itemIndex).precos >  gp.jogador.moeda) {
					subTelas = 0;
					gp.gameState = gp.dialogoState;
					dialogoAtual = "Suas moedas são insuficientes!";
					telaDeDialogo();
				}
				else {
					if ( gp.jogador.podeObterItem(npc.inventario.get(itemIndex)) == true) {
						gp.jogador.moeda -= npc.inventario.get(itemIndex).precos;
					}
					else {
						subTelas = 0;
						gp.gameState = gp.dialogoState;
						dialogoAtual = "Você está com o inventário cheio!";	
					}
				}	
			}
		}				
	}	
	public void exibirtelaDormir() {
		
		contador++;
		
		if( contador < 120) {
			gp.ambManipulador.iluminacao.filtroAlpha += 0.01f;
			if(  gp.ambManipulador.iluminacao.filtroAlpha > 1f) {
				gp.ambManipulador.iluminacao.filtroAlpha = 1f;
			}
		}
		if ( contador >= 120) {
			gp.ambManipulador.iluminacao.filtroAlpha -= 0.01f;
			if ( gp.ambManipulador.iluminacao.filtroAlpha <= 0f) {
				gp.ambManipulador.iluminacao.filtroAlpha = 0f;
				contador = 0;
				gp.ambManipulador.iluminacao.diaState = gp.ambManipulador.iluminacao.dia;
				gp.ambManipulador.iluminacao.diaContador = 0;
				gp.gameState = gp.playState;
				gp.jogador.pegarImagemJogador();
			}
		}
	}
	public void mercador_Vender() {
		
		//Exibir o inventário do jogador
		exibirIventario(gp.jogador, true);
		
		int x;
		int y;
		int largura;
		int altura;
		
		//Janela de dica
		x = gp.tileSize*2;
		y = gp.tileSize*9+20;
		largura = gp.tileSize*6;
		altura = gp.tileSize*2;
		subJanela(x, y, largura, altura);
		g2.drawString("[ESC] Volta", x+24, y+60);
		
		//Janela das moedas do jogador
		x = gp.tileSize*12;
		y = gp.tileSize*9+20;
		largura = gp.tileSize*6;
		altura = gp.tileSize*2;
		subJanela(x, y, largura, altura);
		g2.drawString("Suas moedas: " + gp.jogador.moeda, x+24, y+60);
		
		//Janela de valores dos itens vendidos pelo NPC
		int itemIndex = getIndexdoItemnoSlot(jogadorSlotColuna, jogadorSlotLinha);
		if ( itemIndex < gp.jogador.inventario.size()) {
			
			x = (int)(gp.tileSize*15.5);
			y = (int)(gp.tileSize*5.5);
			largura = (int)(gp.tileSize*2.5);
			altura = gp.tileSize;
			subJanela(x, y, largura, altura);
			g2.drawImage(moeda, x+10, y+13, 32, 32, null); // <- Desenho da moeda na caixa de venda
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
			int preco = gp.jogador.inventario.get(itemIndex).precos/2;
			String texto = "" + preco;
			x = localDoTextonaTelaAlinharDireita(texto, gp.tileSize*18-20);
			g2.drawString(texto, x, y+35);
			
			//Vender item
			if ( gp.keyH.enterParaFalar == true) {
				if ( gp.jogador.inventario.get(itemIndex) == gp.jogador.armaAtual || gp.jogador.inventario.get(itemIndex) == gp.jogador.escudoAtual) {
					numeroDeComandos = 0;
					subTelas = 0;	
					gp.gameState = gp.dialogoState;
					dialogoAtual = "Você não pode vender um item equipado!";
				}
				else {
					if ( gp.jogador.inventario.get(itemIndex).quantidade > 1) {
						 gp.jogador.inventario.get(itemIndex).quantidade--;
					}
					else {
						gp.jogador.inventario.remove(itemIndex);
					}			
					gp.jogador.moeda += preco;
				}
			}
		}	
	}
	public int getIndexdoItemnoSlot(int slotColuna, int slotLinha) {
		
		int itemIndex = slotColuna + (slotLinha*5);
		return itemIndex;
	}
	public void subJanela(int x, int y, int largura, int altura) {
		
		Color c = new Color(0, 0, 0, 190);
		g2.setColor(c);
		g2.fillRoundRect(x, y, largura, altura, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+10, y+10, largura-10, altura-10, 25, 25);
		
	}
	
	public int localDoTextonaTela(String texto) {
		
		//Aqui é para localizar onde será escrito a palavra PAUSE
				int largura = (int)g2.getFontMetrics().getStringBounds(texto, g2).getWidth();
				int x = gp.telaLargura/2 - largura/2;
				return x;
	}
	
	public int localDoTextonaTelaAlinharDireita(String texto, int cantoX) {
		
		//Aqui é para localizar onde será escrito a palavra PAUSE
				int largura = (int)g2.getFontMetrics().getStringBounds(texto, g2).getWidth();
				int x = cantoX - largura;
				return x;
	}
}
