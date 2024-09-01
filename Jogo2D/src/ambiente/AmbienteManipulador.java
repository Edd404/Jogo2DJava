package ambiente;

import java.awt.Graphics2D;

import main.GamePanel;

public class AmbienteManipulador {

	GamePanel gp;
	public Iluminacao iluminacao;
	
	
	public AmbienteManipulador(GamePanel gp) {
		this.gp = gp;
	}
	public void setup() {
		
		iluminacao = new Iluminacao(gp);
	}
	public void update() {
		
		iluminacao.update();
	}
	public void draw(Graphics2D g2) {
		
		
		iluminacao.draw(g2);
	}

}
