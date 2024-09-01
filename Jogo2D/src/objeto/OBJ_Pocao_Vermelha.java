package objeto;

import entidade.Entidade;
import main.GamePanel;

public class OBJ_Pocao_Vermelha extends Entidade{

	
	GamePanel gp;
	
	
	public OBJ_Pocao_Vermelha(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		tipo = tipo_consumivel;
		nome = "Poção Vermelha";
		valor = 5;
		baixo1 = setup("/objetos/Pocao_Vermelha", gp.tileSize, gp.tileSize);
		valordeDEF = 1;
		descricao = "[" + nome + "]\nTem gosto horrível, mas \nfaz bem a sáude!";
		precos = 75;
		agrupavel = true;
	}
	
	public boolean usar(Entidade entidade) {
		
		gp.gameState =  gp.dialogoState;
		
		gp.ui.dialogoAtual = "Você usou uma "+nome+"!\n" + "Recuperou "+valor+" de vida!";
		entidade.vida += valor;
		gp.playSFX(4);
		
		return true;
		
	}
}
