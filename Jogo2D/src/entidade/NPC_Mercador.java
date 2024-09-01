package entidade;

import main.GamePanel;
import objeto.OBJ_Chave;
import objeto.OBJ_Escudo_Madeira;
import objeto.OBJ_Escudo_Metal;
import objeto.OBJ_Espada_Padrao;
import objeto.OBJ_Machado;
import objeto.OBJ_Pocao_Vermelha;

public class NPC_Mercador extends Entidade{

	
	public NPC_Mercador(GamePanel gp) {
		
		super(gp);
		direcao = "Baixo";
		velocidade = 1;
		pegarImagem();
		setDialogo();
		setItens();
		
	}
	
	public void pegarImagem() {
		
		
		cima1 = setup("/npc/NPC_Mercador01", gp.tileSize, gp.tileSize);
		cima2 = setup("/npc/NPC_Mercador02", gp.tileSize, gp.tileSize);
		baixo1 = setup("/npc/NPC_Mercador01", gp.tileSize, gp.tileSize);
		baixo2 = setup("/npc/NPC_Mercador02", gp.tileSize, gp.tileSize);
		esquerda1 = setup("/npc/NPC_Mercador01", gp.tileSize, gp.tileSize);
		esquerda2 = setup("/npc/NPC_Mercador02", gp.tileSize, gp.tileSize);
		direita1 = setup("/npc/NPC_Mercador01", gp.tileSize, gp.tileSize);
		direita2 = setup("/npc/NPC_Mercador02", gp.tileSize, gp.tileSize);	
	}
	
	//Aqui será armazenado os arrays com os dialógos
	public void setDialogo() {
		
		dialogos[0] = "Oh, finalmente alguem cortou aquelas \nárvores, venha ver o que tenho \npara vender!";
	}
	
	public void setItens() {
		
		inventario.add(new OBJ_Chave(gp));
		inventario.add(new OBJ_Escudo_Metal(gp));
		inventario.add(new OBJ_Pocao_Vermelha(gp));
		inventario.add(new OBJ_Machado(gp));
		inventario.add(new OBJ_Espada_Padrao(gp));
		inventario.add(new OBJ_Escudo_Madeira(gp));
	}
	
	public void dialogosConteudo() {
		
		super.dialogosConteudo();
		gp.gameState = gp.telaMercador;
		gp.ui.npc = this;
		
		
	}
}
