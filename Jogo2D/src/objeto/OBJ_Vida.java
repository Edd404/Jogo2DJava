package objeto;

import entidade.Entidade;
import main.GamePanel;

public class OBJ_Vida extends Entidade {
	
GamePanel gp;
	
	//Coracoes de vida do personagem no canto esquerdo superior
	public OBJ_Vida(GamePanel gp) {
		
		super(gp);
		this.gp = gp;
		
		tipo = tipo_pegarApenas;
		nome = "Vida";
		valor = 2;
		baixo1 = setup("/objetos/BarraCheia", gp.tileSize-10, gp.tileSize-10);
		imagem = setup("/objetos/BarraCheia", gp.tileSize, gp.tileSize);
		imagem2 = setup("/objetos/BarraMetade", gp.tileSize, gp.tileSize);
		imagem3 = setup("/objetos/BarraVazia", gp.tileSize, gp.tileSize);
	}
	
	public boolean usar(Entidade entidade) {
		
		gp.playSFX(8);
		gp.ui.addMensagem("Recuperou " + valor + " de vida!");
		entidade.vida += valor;
		
		return true;
	}
}


	
	
	
	

