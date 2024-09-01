package main;

import bloco_interativo.BI_ArvoreMorta;
import criaturas.CRIATURA_Ogro;
import criaturas.CRIATURA_Slime;
import entidade.NPC_Mercador;
import entidade.NPC_Peladao;
import objeto.OBJ_Barraca;
import objeto.OBJ_Bau;
import objeto.OBJ_Chave;
import objeto.OBJ_Escudo_Metal;
import objeto.OBJ_Flecha_UI;
import objeto.OBJ_Lanterna;
import objeto.OBJ_Machado;
import objeto.OBJ_Moeda;
import objeto.OBJ_Pocao_Vermelha;
import objeto.OBJ_Porta;
import objeto.OBJ_Vida;

public class DefinicaoDosObjetos {
	
	GamePanel gp;
	
	//Aqui é onde colocamos no mapa as entidades, como objetos, npcs e criaturas
	public DefinicaoDosObjetos(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObjetos() {	
		
		//Mapa 0
		int i = 0;
		int numeroMapa = 0;
		
		gp.obj[numeroMapa][i] = new OBJ_Moeda(gp);
		gp.obj[numeroMapa][i].mundox = gp.tileSize*36;
		gp.obj[numeroMapa][i].mundoy = gp.tileSize*21;
		i++;
		
		gp.obj[numeroMapa][i] = new OBJ_Chave(gp);
		gp.obj[numeroMapa][i].mundox = gp.tileSize*37;
		gp.obj[numeroMapa][i].mundoy = gp.tileSize*21;
		i++;	
		
		gp.obj[numeroMapa][i] = new OBJ_Machado(gp);
		gp.obj[numeroMapa][i].mundox = gp.tileSize*38;
		gp.obj[numeroMapa][i].mundoy = gp.tileSize*21;
		i++;
		
		gp.obj[numeroMapa][i] = new OBJ_Escudo_Metal(gp);
		gp.obj[numeroMapa][i].mundox = gp.tileSize*39;
		gp.obj[numeroMapa][i].mundoy = gp.tileSize*21;
		i++;
		
		gp.obj[numeroMapa][i] = new OBJ_Pocao_Vermelha(gp);
		gp.obj[numeroMapa][i].mundox = gp.tileSize*36;
		gp.obj[numeroMapa][i].mundoy = gp.tileSize*22;
		i++;
			
		gp.obj[numeroMapa][i] = new OBJ_Flecha_UI(gp);
		gp.obj[numeroMapa][i].mundox = gp.tileSize*37;
		gp.obj[numeroMapa][i].mundoy = gp.tileSize*22;
		i++;
		
	//	gp.obj[numeroMapa][i] = new OBJ_Porta(gp);
	//	gp.obj[numeroMapa][i].mundox = gp.tileSize*5;
	//	gp.obj[numeroMapa][i].mundoy = gp.tileSize*6;
	//	i++;
		
		//gp.obj[numeroMapa][i] = new OBJ_Bau(gp); ////ARRUMAR sempre que for adicionar um bau com loot diferente
	//	gp.obj[numeroMapa][i].setLoot(new OBJ_Chave(gp));
	//	gp.obj[numeroMapa][i].mundox = gp.tileSize*18;
	//	gp.obj[numeroMapa][i].mundoy = gp.tileSize*5;
	//	i++;
		
		gp.obj[numeroMapa][i] = new OBJ_Lanterna(gp);
		gp.obj[numeroMapa][i].mundox = gp.tileSize*38;
		gp.obj[numeroMapa][i].mundoy = gp.tileSize*22;
		i++;
		
		gp.obj[numeroMapa][i] = new OBJ_Barraca(gp);
		gp.obj[numeroMapa][i].mundox = gp.tileSize*39;
		gp.obj[numeroMapa][i].mundoy = gp.tileSize*22;
		i++;
	}
	
	public void setNPC() {
		
		int numeroMapa = 0;
		int i =0;
		
		//Mapa 0
		gp.npc[numeroMapa][i] = new NPC_Peladao(gp);
		gp.npc[numeroMapa][i].mundox = gp.tileSize*52;
		gp.npc[numeroMapa][i].mundoy = gp.tileSize*22;
		i++;
		
		
		//Mapa 1
		numeroMapa = 1;
		i = 0;
		
		gp.npc[numeroMapa][i] = new NPC_Mercador(gp);
		gp.npc[numeroMapa][i].mundox = gp.tileSize*10;
		gp.npc[numeroMapa][i].mundoy = gp.tileSize*10;
		i++;
	}
	
	public void setCriatura() {
		
		
		//Mapa 0
		int i = 0;
		int numeroMapa = 0;
	
		
		gp.criaturas[numeroMapa][i] = new CRIATURA_Slime(gp);
		gp.criaturas[numeroMapa][i].mundox = gp.tileSize*40;//<- Aqui é a posicao da criatura no mapa
		gp.criaturas[numeroMapa][i].mundoy = gp.tileSize*22;
		i++;
		
		gp.criaturas[numeroMapa][i] = new CRIATURA_Slime(gp);
		gp.criaturas[numeroMapa][i].mundox = gp.tileSize*41;//<- Aqui é a posicao da criatura no mapa
		gp.criaturas[numeroMapa][i].mundoy = gp.tileSize*22;
		i++;
		
		gp.criaturas[numeroMapa][i] = new CRIATURA_Slime(gp);
		gp.criaturas[numeroMapa][i].mundox = gp.tileSize*42;//<- Aqui é a posicao da criatura no mapa
		gp.criaturas[numeroMapa][i].mundoy = gp.tileSize*22;
		i++;
		
		gp.criaturas[numeroMapa][i] = new CRIATURA_Ogro(gp);
		gp.criaturas[numeroMapa][i].mundox = gp.tileSize*30;//<- Aqui é a posicao da criatura no mapa
		gp.criaturas[numeroMapa][i].mundoy = gp.tileSize*30;
		i++;
	}
	
	public void setBlocosInterativos() {
		
		int i = 0;
		int numeroMapa = 0;
		
		gp.blocosInt[numeroMapa][i] =  new BI_ArvoreMorta(gp, 52, 27); i++;
		
	//	gp.blocosInt[numeroMapa][i] =  new BI_ArvoreMorta(gp, 11, 11); i++;
		
	//	gp.blocosInt[numeroMapa][i] =  new BI_ArvoreMorta(gp, 12, 11); i++;
		
	//	gp.blocosInt[numeroMapa][i] =  new BI_ArvoreMorta(gp, 13, 11); i++;
		
	//	gp.blocosInt[numeroMapa][i] =  new BI_ArvoreMorta(gp, 14, 11); i++;
		
	//	gp.blocosInt[numeroMapa][i] =  new BI_ArvoreMorta(gp, 10, 12); i++;
		
	//	gp.blocosInt[numeroMapa][i] =  new BI_ArvoreMorta(gp, 9, 12); i++;
	
		

		
	}
}
