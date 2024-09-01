package objeto;

import entidade.Entidade;
import main.GamePanel;

public class OBJ_Barraca extends Entidade{

	GamePanel gp;
	
	public OBJ_Barraca(GamePanel gp) {
		super(gp);
		this.gp = gp;
		tipo = tipo_consumivel;
		nome = "Barraca";
		baixo1 = setup("/objetos/Barraca", gp.tileSize, gp.tileSize);
		baixo2 = setup("/objetos/Barraca", gp.tileSize*2, gp.tileSize*2);
		descricao = "[Barraca]\nUma barraca quentinha \npara passar a noite.";
		precos = 350;
		agrupavel = true;	
	}
	public boolean usar(Entidade entidade) {
		
		gp.gameState = gp.dormirState;
		gp.playSFX(14);
		gp.jogador.vida = gp.jogador.vidaMaxima;
		gp.jogador.mana = gp.jogador.manaMaxima;
		gp.jogador.pegarImagemDormindo(baixo2);
		return false;
	}
}
