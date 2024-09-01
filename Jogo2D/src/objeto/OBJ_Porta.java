package objeto;



import entidade.Entidade;
import main.GamePanel;

public class OBJ_Porta extends Entidade{
	
	GamePanel gp;
	
	public OBJ_Porta(GamePanel gp) {
		
		super(gp);
		this.gp = gp;
		
		tipo = tipo_obstaculo;
		nome = "Porta";
		baixo1 = setup("/objetos/Porta01", gp.tileSize, gp.tileSize);
		colisao = true;
		
		//Area de colisao da porta
		areaSolida.x = 4;
		areaSolida.y = 16;
		areaSolida.width = 48;
		areaSolida.height = 32;
		areaSolidaPadraoX = areaSolida.x;
		areaSolidaPadraoY = areaSolida.y;
	
	}
	
	public void interagir() {
		
		gp.gameState = gp.dialogoState;
		gp.ui.dialogoAtual = "Você precisa de uma chave para abrir!";
	}
}
