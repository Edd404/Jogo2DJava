package objeto;


import entidade.Entidade;
import main.GamePanel;

public class OBJ_Bau extends Entidade{
	
	GamePanel gp;


	
	public OBJ_Bau(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		
		tipo = tipo_obstaculo;
		nome = "Bau";
		baixo1 = setup("/objetos/Bau_B1", gp.tileSize, gp.tileSize);
		baixo2 = setup("/objetos/Bau_B2", gp.tileSize, gp.tileSize);
		baixo3 = setup("/objetos/Bau_B3", gp.tileSize, gp.tileSize);
		imagem2 = setup("/objetos/Bau_Aberto", gp.tileSize, gp.tileSize);
		
		colisao = true;
		areaSolida.x = -4;
		areaSolida.y = 16;
		areaSolida.width = 48;
		areaSolida.height = 16;
		areaSolidaPadraoX = areaSolida.x;
		areaSolidaPadraoY = areaSolida.y-8;
	  
	}
	public void setLoot(Entidade loot) {
		
		this.loot = loot;
	}
	public void interagir() {
		
		gp.gameState = gp.dialogoState;
		
		if( aberto == false) {
			gp.playSFX(13);
			
			StringBuilder sb = new StringBuilder();
			sb.append("Você abriu o baú e encontrou \num(a) " + loot.nome + "!");
			
			if( gp.jogador.podeObterItem(loot) == false) {
				sb.append("\n...Mas você não consegue carregar \nmais nada... ");
			}
			else {
				//sb.append("\n\nVocê obteve um(a) " + loot.nome + "!");
				
				baixo1 = imagem2;
				aberto = true;
			}
			gp.ui.dialogoAtual = sb.toString();	
		}
		else {
			gp.ui.dialogoAtual = "Está vazio...";
		}
	}
}
