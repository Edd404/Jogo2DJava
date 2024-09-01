package entidade;



import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;


public class NPC_Peladao extends Entidade {

	public NPC_Peladao(GamePanel gp) {
		
		super(gp);
		direcao = "Baixo";
		velocidade = 2;
		pegarImagem();
		setDialogo();
		areaSolida = new Rectangle(); areaSolida.x = 8; areaSolida.y = 16; areaSolidaPadraoX = areaSolida.x; areaSolidaPadraoY = areaSolida.y; areaSolida.width = 30; areaSolida.height = 30;
	}
	
	public void pegarImagem() {
		
		
		cima1 = setup("/npc/NPC01CIMA01", gp.tileSize, gp.tileSize);
		cima2 = setup("/npc/NPC01CIMA02", gp.tileSize, gp.tileSize);
		cima3 = setup("/npc/NPC01CIMA02", gp.tileSize, gp.tileSize);
		baixo1 = setup("/npc/NPC01BAIXO01", gp.tileSize, gp.tileSize);
		baixo2 = setup("/npc/NPC01BAIXO02", gp.tileSize, gp.tileSize);
		baixo3 = setup("/npc/NPC01BAIXO02", gp.tileSize, gp.tileSize);
		esquerda1 = setup("/npc/NPC01ESQ01", gp.tileSize, gp.tileSize);
		esquerda2 = setup("/npc/NPC01ESQ02", gp.tileSize, gp.tileSize);
		esquerda3 = setup("/npc/NPC01ESQ02", gp.tileSize, gp.tileSize);
		direita1 = setup("/npc/NPC01DIR01", gp.tileSize, gp.tileSize);
		direita2 = setup("/npc/NPC01DIR02", gp.tileSize, gp.tileSize);
		direita3 = setup("/npc/NPC01DIR02", gp.tileSize, gp.tileSize);	
	}
	
	//Aqui será armazenado os arrays com os dialógos
	public void setDialogo() {
		
		dialogos[0] = "SALVE BANDO DE ARROMBADO!!!!!!!!!!";
		dialogos[1] = "VAMO MORRE";
		dialogos[2] = "PQP";
		dialogos[3] = "AAAAAAAAAAAAAAAAAAAAAAAAAAA";
		
		
	}
	public void setAcao() {
		
		if ( noCaminho == true) {
			
			//int goalCol = 10;  // <- Aqui define para onde voce quer que o NPC vá
			//int goalRow = 8;
			int goalCol = (gp.jogador.mundox +  gp.jogador.areaSolida.x)/gp.tileSize; //<- Aqui definie se que que te siga, pode ser usado para algum animal de estimacao ou algo assim
			int goalRow = (gp.jogador.mundoy +  gp.jogador.areaSolida.y)/gp.tileSize;
			
			
			buscarCaminho(goalCol,goalRow);
		}
		else {
			
			contadorParaAcoes++;
			
			if(contadorParaAcoes == 180) {
				
				Random random = new Random();
				int i = random.nextInt(100)+1;
				if(i <= 25) {
					direcao = "Cima";
				}
				if(i > 25 && i <= 50) {
					direcao = "Baixo";
				}
				if(i > 50 && i <= 75) {
					direcao = "Esquerda";
				}
				if(i > 75 && i <= 100) {
					direcao = "Direita";
				}
				
				contadorParaAcoes = 0;
			}			
		}
	}	
	
	//Aqui é o metodo de interacao com a UI, para que o personagem fale coisas diferentes do metodo dentro da UI
	public void dialogosConteudo() {
		
		super.dialogosConteudo();
		noCaminho = true;
		
	}
}
