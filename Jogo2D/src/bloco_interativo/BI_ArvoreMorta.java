package bloco_interativo;

import java.awt.Color;

import entidade.Entidade;
import main.GamePanel;

public class BI_ArvoreMorta extends BlocosInterativos{

	GamePanel gp;
	
	public BI_ArvoreMorta(GamePanel gp, int coluna, int linha) {
		super(gp, coluna, linha);
		this.gp = gp;
		
		this.mundox = gp.tileSize * coluna;
		this.mundoy = gp.tileSize * linha;
		
		baixo1 = setup("/blocosinterativos/Arvore_Morta01",gp.tileSize, gp.tileSize);
		destrutivel = true;
		vida = 3;
	}
	
	public boolean itemCorretoInterativo(Entidade entidade) {
		
		boolean itemCorretoInterativo = false;
		if ( entidade.armaAtual.tipo == tipo_machado) {
			itemCorretoInterativo = true;
		}
		return itemCorretoInterativo;
	}

	public void playSFXAntes() {
		
		gp.playSFX(10);
		
	}
	public void playSFXDepois() {
		
		gp.playSFX(9);
		
	}
	//Aqui é a substituicao da arvore morta para tronco na mesma posicao do mapa
	public BlocosInterativos getBlocoDestruido() {
		
		BlocosInterativos bloco = new BI_Tronco(gp, mundox/gp.tileSize, mundoy/gp.tileSize);
		
		return bloco;	
	}
	public Color getCordasParticulas() {
		
		Color cor = new Color(65, 50, 30);
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
