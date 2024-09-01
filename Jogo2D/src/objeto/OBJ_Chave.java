package objeto;


import entidade.Entidade;
import main.GamePanel;

public class OBJ_Chave extends Entidade{
	
	GamePanel gp;
	
	public OBJ_Chave(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		tipo = tipo_consumivel;
		nome = "Chave";
		baixo1 = setup("/objetos/Chave01", gp.tileSize, gp.tileSize);
		descricao = "[" + nome + "]\n O que será que ela abre?";
		precos = 50;
		agrupavel = true;
	}
	
	public boolean usar(Entidade entidade) {
		
		gp.gameState = gp.dialogoState;
		
		int objIndex = getDetectar(entidade, gp.obj, "Porta");
		
		if ( objIndex != 999) {
			gp.ui.dialogoAtual = "Você usou uma " + nome + " e abriu a porta.";
			gp.playSFX(12);
			gp.obj[gp.mapaAtual][objIndex] = null;
			return true; // < se usar, e quer que suma é true
		}
		else {
			gp.ui.dialogoAtual = "Isso é uma chave, serve para \nabrir uma porta...certo?";
			return false; // <-- Se usar, e nao quer que suma o item, é false
		}
	}
}
