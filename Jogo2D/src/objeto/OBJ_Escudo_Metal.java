package objeto;

import entidade.Entidade;
import main.GamePanel;

public class OBJ_Escudo_Metal extends Entidade {

	public OBJ_Escudo_Metal(GamePanel gp) {
		super(gp);
		
		tipo = tipo_escudo;
		nome = "Escudo de Metal";
		baixo1 = setup("/objetos/Escudo_Metal", gp.tileSize, gp.tileSize);
		valordeDEF = 2;
		descricao = "[" + nome + "]\nParece ser muito resistente.";
		precos = 150;
	}
}
