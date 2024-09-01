package criaturas;

import java.util.Random;
import java.lang.Math;

import entidade.Entidade;
import main.GamePanel;
import objeto.OBJ_BolaSlime;
import objeto.OBJ_Flecha_UI;
import objeto.OBJ_Moeda;
import objeto.OBJ_Vida;

public class CRIATURA_Slime extends Entidade {
	
	GamePanel gp;
	

	public CRIATURA_Slime(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		tipo = tipo_criatura;
		nome = "Slime";
		velocidadePadrao = 1;
		velocidade = velocidadePadrao;
		vidaMaxima = 4;
		vida = vidaMaxima;
		ataque = 3;
		defesa = 0;
		exp = 1;
		projetil = new OBJ_BolaSlime(gp);
		
		
		//Aqui sao as definicoes do hitbox da criatura, ou seja para cada uma tera valores diferentes de acordo com o tamanho do mesmo
		areaSolida.x = 3;
		areaSolida.y = 18;
		areaSolida.width = 42;
		areaSolida.height = 30;
		areaSolidaPadraoX = areaSolida.x;
		areaSolidaPadraoY = areaSolida.y;
		
		pegarImagem();
	}
	
	
	public void pegarImagem() {
		
		cima1 = setup("/criaturas/CRI_Cima_Slime_01", gp.tileSize, gp.tileSize);
		cima2 = setup("/criaturas/CRI_Cima_Slime_02", gp.tileSize, gp.tileSize);
		cima3 = setup("/criaturas/CRI_Cima_Slime_03", gp.tileSize, gp.tileSize);
		baixo1 = setup("/criaturas/CRI_Baixo_Slime_01", gp.tileSize, gp.tileSize);
		baixo2 = setup("/criaturas/CRI_Baixo_Slime_02", gp.tileSize, gp.tileSize);
		baixo3 = setup("/criaturas/CRI_Baixo_Slime_03", gp.tileSize, gp.tileSize);
		esquerda1 = setup("/criaturas/CRI_Esquerda_Slime_01", gp.tileSize, gp.tileSize);
		esquerda2 = setup("/criaturas/CRI_Esquerda_Slime_02", gp.tileSize, gp.tileSize);
		esquerda3 = setup("/criaturas/CRI_Esquerda_Slime_03", gp.tileSize, gp.tileSize);
		direita1 = setup("/criaturas/CRI_Direita_Slime_01", gp.tileSize, gp.tileSize);
		direita2 = setup("/criaturas/CRI_Direita_Slime_02", gp.tileSize, gp.tileSize);
		direita3 = setup("/criaturas/CRI_Direita_Slime_03", gp.tileSize, gp.tileSize);
	}
	public void setAcao() {
			
		if ( noCaminho == true) {
			
			//verifica se parou de te seguir
			checarSeParouDeSeguir(gp.jogador, 15, 100);
				
			buscarCaminho(getGoalCol(gp.jogador), getGoalRow(gp.jogador));
			
			//Verificar se vai atirar algum projétil
			checarProjeteis(200, 30);
		}
		else {
			
			checarSeComecouSeguir(gp.jogador, 5, 100);
			
			getDirecaoAleatoria();
		}
	}
	public void aiCriaturas() {
		
		contadorParaAcoes = 0;
		//direcao = gp.jogador.direcao;
		
		noCaminho = true;
	}
	
	public void checarDrop() {
		
		int i = new Random().nextInt(200);
		
		//Aqui é onde setamos a chance de drop baseada no Random que criei acima
		if (i >= 50 && i < 75) {
			dropItem(new OBJ_Moeda(gp));
		}
		if (i >= 75 && i < 125) {
			dropItem(new OBJ_Vida(gp));
		}
		if (i >= 125 && i < 200) {
			dropItem(new OBJ_Flecha_UI(gp));
		}
	}
}














