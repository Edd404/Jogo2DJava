package ambiente;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Iluminacao {

	GamePanel gp;
	BufferedImage filtroEscuro;
	public int diaContador;
	public float filtroAlpha = 0f;
	
	//Fases do dia
	public final int dia = 0;
	public final int anoitecer = 1; //DUSK
	public final int noite = 2;
	public final int amanhecer = 3; //DAWN
	public int diaState = dia;
	
	private BufferedImage imagemDia;
	private BufferedImage imagemAnoitecer;
	private BufferedImage imagemNoite;
	private BufferedImage imagemAmanhecer;
	
	public Iluminacao(GamePanel gp) {
		this.gp = gp;
		setFontedeLuz();
		carregarImagens();
		
	}
	public void setFontedeLuz() {
		
		
		//Cria um circulo
		filtroEscuro = new BufferedImage(gp.telaLargura, gp.telaAltura, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)filtroEscuro.getGraphics();
			
		
		if( gp.jogador.luzAtual == null) {
			g2.setColor(new Color(0,0,0.1f,0.98f));
			
			
		}
		else {
			
			//Localizar o centro x e y do circulo
			int centroX = gp.jogador.telax + (gp.tileSize)/2;
			int centroY = gp.jogador.telay + (gp.tileSize)/2;
				
			//Efeito gradiente
			Color cor[] = new Color[12];
			float fracao[] = new float[12];
			
			cor[0] = new Color(0,0,0.1f,0.1f);
			cor[1] = new Color(0,0,0.1f,0.42f);
			cor[2] = new Color(0,0,0.1f,0.52f);
			cor[3] = new Color(0,0,0.1f,0.61f);
			cor[4] = new Color(0,0,0.1f,0.69f);
			cor[5] = new Color(0,0,0.1f,0.76f);
			cor[6] = new Color(0,0,0.1f,0.82f);
			cor[7] = new Color(0,0,0.1f,0.87f);
			cor[8] = new Color(0,0,0.1f,0.91f);
			cor[9] = new Color(0,0,0.1f,0.94f);
			cor[10] = new Color(0,0,0.1f,0.96f);
			cor[11] = new Color(0,0,0.1f,0.98f);
			
			fracao[0] = 0f;
			fracao[1] = 0.4f;
			fracao[2] = 0.5f;
			fracao[3] = 0.6f;
			fracao[4] = 0.65f;
			fracao[5] = 0.7f;
			fracao[6] = 0.75f;
			fracao[7] = 0.8f;
			fracao[8] = 0.85f;
			fracao[9] = 0.9f;
			fracao[10] = 0.95f;
			fracao[11] = 1f;
			
			
			RadialGradientPaint gPaint = new RadialGradientPaint(centroX, centroY, gp.jogador.luzAtual.tamanhoIluminacao, fracao, cor);
			
			
			g2.setPaint(gPaint);
		}

		
		g2.fillRect(0, 0, gp.telaLargura, gp.telaAltura);

		g2.dispose();	
		
	}
	public void resetarDia() {
		
		diaState = dia;
		filtroAlpha = 0f;
	}
	public void update() {
		
		
		if ( gp.jogador.mudarIluminacao == true) {
			setFontedeLuz();
			gp.jogador.mudarIluminacao = false;
		}
		
		//Checar o as informacoes do dia
		//Basicamente o contador ao chegar ao tempo estipulado, decrementa ou incrementa o filtro Alpha, tornando a tela mais escura ou mais clara
		if ( diaState == dia) {
			 diaContador++;
			 
			 if ( diaContador > 36000) {     //Aqui é o tempo para mudar de horario, cada 600 sao 10 segundos
				 diaState = anoitecer;
				 diaContador = 0;
			}
		}
		if ( diaState == anoitecer) {
			
			filtroAlpha += 0.001f;
			
			if( filtroAlpha > 1f) {
				 filtroAlpha = 1f;
				 diaState = noite;
			 }
		}
		if ( diaState == noite) {
			diaContador++;
			
			if ( diaContador > 36000) {
				diaState = amanhecer;
				diaContador = 0;
			}
		}
		if ( diaState == amanhecer) {
			
			filtroAlpha -= 0.001f;
			
			if ( filtroAlpha < 0f) {
				filtroAlpha = 0;
				diaState = dia;
			}
		}
	}
	public void draw(Graphics2D g2) {
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filtroAlpha));
		g2.drawImage(filtroEscuro, 0, 0, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		
		
		switch (diaState) {
		case dia:
			g2.drawImage(imagemDia, 870, 30, gp.tileSize+35, gp.tileSize+35, null);
			break;
		case anoitecer:
			g2.drawImage(imagemAnoitecer, 870, 30, gp.tileSize+35, gp.tileSize+35, null);
			break;
		case noite:
			g2.drawImage(imagemNoite, 870, 30, gp.tileSize+35, gp.tileSize+35, null);
			break;
		case amanhecer:
			g2.drawImage(imagemAmanhecer, 870, 30, gp.tileSize+35, gp.tileSize+35, null);
			break;
		}
		//Informacao do horario na tela em forma de texto, no caso, estou usando imagens, por isso tirei o texto.
		
		/*
		 * String horario = "";
		 * 
		 * switch(diaState) { case dia: horario = "Dia"; break; case anoitecer: horario
		 * = "Anoitecer"; break; case noite: horario = "Noite"; break; case amanhecer:
		 * horario = "Amanhecer"; break; }
		 * 
		 * g2.setColor(Color.BLACK); g2.setFont(g2.getFont().deriveFont(14f));
		 * g2.drawString(horario, 870, 20+2); //Posicao do texto DIA, NOITE, ETC
		 * g2.setColor(Color.WHITE); g2.setFont(g2.getFont().deriveFont(14f));
		 * g2.drawString(horario, 870, 20); //Posicao do texto DIA, NOITE, ETC
		 */		
	}
	private void carregarImagens() {
		try {
			imagemDia = ImageIO.read(getClass().getResourceAsStream("/objetos/UI_Dia.png"));
			imagemAnoitecer = ImageIO.read(getClass().getResourceAsStream("/objetos/UI_Anoitecer.png"));
			imagemNoite = ImageIO.read(getClass().getResourceAsStream("/objetos/UI_Noite.png"));
			imagemAmanhecer = ImageIO.read(getClass().getResourceAsStream("/objetos/UI_Amanhecer.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}















