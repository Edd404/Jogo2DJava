package objeto;

import java.awt.Color;

import entidade.Entidade;
import entidade.Projeteis;
import main.GamePanel;

public class OBJ_BolaSlime extends Projeteis{

GamePanel gp;
	
	public OBJ_BolaSlime(GamePanel gp) {
		super(gp);
		this.gp = gp;
		velocidade = 6;
		vidaMaxima = 100;
		vida = vidaMaxima;
		ataque = 2;
		custodeUso = 2;
		vivo = false;
		getImagem();
		
	}
	public void getImagem() {
		
		cima1 = setup("/projeteis/BolaSlime_Cima01", gp.tileSize, gp.tileSize);
		cima2 = setup("/projeteis/BolaSlime_Cima02", gp.tileSize, gp.tileSize);
		baixo1 = setup("/projeteis/BolaSlime_Baixo01", gp.tileSize, gp.tileSize);
		baixo2 = setup("/projeteis/BolaSlime_Baixo02", gp.tileSize, gp.tileSize);
		esquerda1 = setup("/projeteis/BolaSlime_Esquerda01", gp.tileSize, gp.tileSize);
		esquerda2 = setup("/projeteis/BolaSlime_Esquerda02", gp.tileSize, gp.tileSize);
		direita1 = setup("/projeteis/BolaSlime_Direita01", gp.tileSize, gp.tileSize);
		direita2 = setup("/projeteis/BolaSlime_Direita02", gp.tileSize, gp.tileSize);
	}
	
	public boolean capacidadeRecursos(Entidade usuario) {
		
		
		boolean capacidadeRecursos = false;
		if ( usuario.municao >= custodeUso) {
			capacidadeRecursos = true;
		}
		return capacidadeRecursos;
	}
	public void diminuirRecursos(Entidade usuario) {
		
		usuario.municao -= custodeUso;
	}
public Color getCordasParticulas() {
		
		Color cor = new Color(35,142,35);
		return cor;
	}
	
	public int getTamanhodasParticulas() {
		
		int tamanho = 10; // <-- 6 pixels
		return tamanho;
	}
	public int getVelocidadedasParticulas() {
		
		int velocidade = 1;
		return velocidade;
	}
	public int getVidadasParticulas() {
		
		int vidaMaxima = 20; // <--Assim como em outros objetos, a vida representa o tempo dela em tela, nao uma condicao de vivo ou morto.
		return vidaMaxima;
	}
}
