package objeto;

import entidade.Entidade;
import main.GamePanel;

public class OBJ_Espada_Padrao extends Entidade{

	public OBJ_Espada_Padrao(GamePanel gp) {
		super(gp);
		
		tipo = tipo_espada;
		nome = "Espada Básica";
		baixo1 = setup("/objetos/Espada", gp.tileSize, gp.tileSize);
		valordeATK = 1;
		areadeAtaque.width = 36;
		areadeAtaque.height = 36;
		descricao = "[" + nome + "]\nAntiga espada de família.";
		precos = 50;
		knockBackPoder = 2;
		
	}
}
