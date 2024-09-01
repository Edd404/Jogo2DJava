package objeto;

import entidade.Entidade;
import main.GamePanel;

public class OBJ_Moeda extends Entidade {

	GamePanel gp;
	public int estadoImagem = 0;
	
	public OBJ_Moeda(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		tipo = tipo_pegarApenas;
		nome = "Moeda";
		valor = 1;
		
		baixo1 = setup("/objetos/Moeda01", gp.tileSize, gp.tileSize);
		baixo2 = setup("/objetos/Moeda02", gp.tileSize, gp.tileSize);
		cima1 = setup("/objetos/Moeda03", gp.tileSize, gp.tileSize);
			
	}
	

	public boolean usar(Entidade entidade) {
		
		gp.playSFX(3);
		gp.ui.addMensagem("Moeda +" + valor);
		gp.jogador.moeda += valor;
		
		return true;
	}
	
}

