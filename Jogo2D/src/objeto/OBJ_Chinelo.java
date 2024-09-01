package objeto;


import entidade.Entidade;
import main.GamePanel;

public class OBJ_Chinelo extends Entidade{
	
	GamePanel gp;
	
	public OBJ_Chinelo(GamePanel gp) {
		
		super(gp);
		nome = "Chinelo";
		baixo1 = setup("/objetos/Chinelo01", gp.tileSize, gp.tileSize);
		
	}
}
