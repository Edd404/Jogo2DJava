package entidade;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Particulas  extends Entidade{

	
	Entidade gerador; // <-- Será usado para gerar as particulas na tela
	Color cor;
	int tamanho;
	int xd;
	int yd;
	
	public Particulas(GamePanel gp, Entidade gerador, Color cor, int tamanho, int velocidade, int vidaMaxima, int xd, int yd) {
		super(gp);
		
		
		this.gerador = gerador;
		this.cor = cor;
		this.tamanho = tamanho;
		this.velocidade = velocidade;
		this.vidaMaxima = vidaMaxima;
		this.xd = xd;
		this.yd = yd;
		
		vida = vidaMaxima;
		int centralizar = (gp.tileSize/2) - (tamanho/2);
		
		mundox = gerador.mundox + centralizar;
		mundoy = gerador.mundoy + centralizar; //<-- aqui identifica o bloco que as particulas sairá atraves das coordenadas e visao do seu personagem	
	}
	public void update() {
		
		vida--;
		if (vida < vidaMaxima/3) {
			yd++;
			tamanho--;
		}
		mundox += xd + velocidade;
		mundoy += yd + velocidade;
		
		if ( vida == 0) {
			vivo = false;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int telax = mundox - gp.jogador.mundox + gp.jogador.telax;
		int telay = mundoy - gp.jogador.mundoy + gp.jogador.telay;
		
		g2.setColor(cor);
		g2.fillRect(telax, telay, tamanho, tamanho);
	}
}
