package objeto;

import entidade.Entidade;
import main.GamePanel;

public class OBJ_Machado extends Entidade {

	public OBJ_Machado(GamePanel gp) {
		super(gp);
		
		tipo = tipo_machado;
		nome = "Machado de Lenhador";
		baixo1 = setup("/objetos/Machado", gp.tileSize, gp.tileSize);
		valordeATK = 2;
		areadeAtaque.width = 30;
		areadeAtaque.height = 30;
		descricao = "[" + nome + "]\nÓtimo para cortar árvores";
		precos = 100;
		knockBackPoder = 10;
		
	}
}
