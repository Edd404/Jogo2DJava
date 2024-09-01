package objeto;

import entidade.Entidade;
import main.GamePanel;

public class OBJ_Flecha_UI extends Entidade{

	GamePanel gp;
	
	public OBJ_Flecha_UI(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		tipo = tipo_pegarApenas;
		nome = "Flecha Mágica";
		valor = 1;
		baixo1 = setup("/objetos/Flecha_UI1", gp.tileSize, gp.tileSize);
		imagem = setup("/objetos/Flecha_UI1", gp.tileSize, gp.tileSize); // <-- Representa a mana, nesse caso, uma flecha magica, UI01 é cheio UI02 é vazio
		imagem2 = setup("/objetos/Flecha_UI2", gp.tileSize, gp.tileSize);
				
	}

	public boolean usar(Entidade entidade) {
		
		gp.playSFX(8);
		gp.ui.addMensagem("Recuperou " + valor + " flecha mágica!");
		entidade.mana += valor;
		
		return true;
	}
}
