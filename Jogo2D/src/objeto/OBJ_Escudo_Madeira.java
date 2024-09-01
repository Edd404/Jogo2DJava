package objeto;

import entidade.Entidade;
import main.GamePanel;

public class OBJ_Escudo_Madeira extends Entidade {

	public OBJ_Escudo_Madeira(GamePanel gp) {
		super(gp);
		
		tipo = tipo_escudo;
		nome = "Escudo de Madeira";
		baixo1 = setup("/objetos/Escudo", gp.tileSize, gp.tileSize);
		valordeDEF = 1;
		descricao = "[" + nome + "]\nUm velho escudo de madeira.";
		precos = 75;
	}
}
