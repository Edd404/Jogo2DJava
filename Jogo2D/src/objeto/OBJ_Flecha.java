package objeto;

import java.awt.Color;

import entidade.Entidade;
import entidade.Particulas;
import entidade.Projeteis;
import main.GamePanel;

public class OBJ_Flecha extends Projeteis{

	GamePanel gp;
	
	public OBJ_Flecha(GamePanel gp) {
		super(gp);
		this.gp = gp;
		velocidade = 8;
		vidaMaxima = 100; //<-- Quanto mais vida o objeto tem, mais longe ele vai, nao significa pontos de vida e sim o tempo em tela
		vida = vidaMaxima;
		ataque = 2;
		knockBackPoder = 1;
		custodeUso = 1;
		vivo = false;
		getImagem();
		
		
	}
	public void getImagem() {
		
		cima1 = setup("/projeteis/Flecha_Cima01", gp.tileSize, gp.tileSize);
		cima2 = setup("/projeteis/Flecha_Cima02", gp.tileSize, gp.tileSize);
		baixo1 = setup("/projeteis/Flecha_Baixo01", gp.tileSize, gp.tileSize);
		baixo2 = setup("/projeteis/Flecha_Baixo02", gp.tileSize, gp.tileSize);
		esquerda1 = setup("/projeteis/Flecha_Esquerda01", gp.tileSize, gp.tileSize);
		esquerda2 = setup("/projeteis/Flecha_Esquerda02", gp.tileSize, gp.tileSize);
		direita1 = setup("/projeteis/Flecha_Direita01", gp.tileSize, gp.tileSize);
		direita2 = setup("/projeteis/Flecha_Direita02", gp.tileSize, gp.tileSize);
	}
	
	public boolean capacidadeRecursos(Entidade usuario) {
		
		
		boolean capacidadeRecursos = false;
		if ( usuario.mana >= custodeUso) {
			capacidadeRecursos = true;
		}
		return capacidadeRecursos;
	}
	public void diminuirRecursos(Entidade usuario) {
		
		usuario.mana -= custodeUso;
	}
	public Color getCordasParticulas() {
		
		Color cor = new Color(50,153,204);
		return cor;
	}
	
	public int getTamanhodasParticulas() {
		
		int tamanho = 6; // <-- 6 pixels
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
