package bloco_interativo;

import java.awt.Graphics2D;

import entidade.Entidade;
import main.GamePanel;

public class BlocosInterativos  extends Entidade{
	
	GamePanel gp;
	public boolean destrutivel = false;
	
	public BlocosInterativos(GamePanel gp, int coluna, int linha) {
		super(gp);
		this.gp = gp;
	}
	
	
	public boolean itemCorretoInterativo(Entidade entidade) {
		
		boolean itemCorretoInterativo = false;
		return itemCorretoInterativo;
	}
	public void playSFXDepois() {
		
	}
public void playSFXAntes() {
		
	}
	
	public BlocosInterativos getBlocoDestruido() {
		
		BlocosInterativos bloco = null;
		
		return bloco;
		
	}
	public void update() {
		
		if ( invencibilidade == true) {
			invencibilidadeContador++;
			if ( invencibilidadeContador > 20) {
				invencibilidade = false;
				invencibilidadeContador = 0;
			}
		}
	}
	public void draw(Graphics2D g2) {
		
		
		int telax = mundox - gp.jogador.mundox + gp.jogador.telax;
		int telay = mundoy - gp.jogador.mundoy + gp.jogador.telay;
		
		if ( mundox + gp.tileSize > gp.jogador.mundox - gp.jogador.telax &&
			 mundox - gp.tileSize < gp.jogador.mundox + gp.jogador.telax &&
			 mundoy + gp.tileSize > gp.jogador.mundoy - gp.jogador.telay &&
			 mundoy - gp.tileSize < gp.jogador.mundoy + gp.jogador.telay) {
			
		
			g2.drawImage(baixo1, telax, telay, null);
			
		}	
	}
}
