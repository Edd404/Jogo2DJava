package objeto;

import entidade.Entidade;
import main.GamePanel;

public class OBJ_Lanterna extends Entidade{

	public OBJ_Lanterna(GamePanel gp) {
		super(gp);
		
		tipo = tipo_luz;
		nome = "Lanterna";
		baixo1 = setup("/objetos/Lanterna", gp.tileSize, gp.tileSize);
		descricao = "[Lanterna]\nUma boa fonte de luz.";
		precos = 200;
		tamanhoIluminacao = 250;
	}

}
