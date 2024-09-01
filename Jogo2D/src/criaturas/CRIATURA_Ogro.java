package criaturas;

import java.util.Random;

import entidade.Entidade;
import main.GamePanel;

import objeto.OBJ_Flecha_UI;
import objeto.OBJ_Moeda;
import objeto.OBJ_Vida;

public class CRIATURA_Ogro extends Entidade{

	GamePanel gp;
	

	public CRIATURA_Ogro(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		tipo = tipo_criatura;
		nome = "Ogro";
		velocidadePadrao = 1;
		velocidade = velocidadePadrao;
		vidaMaxima = 10;
		vida = vidaMaxima;
		ataque = 8;
		defesa = 2;
		exp = 10;
		knockBackPoder = 5;
		
		
		
		//Aqui sao as definicoes do hitbox da criatura, ou seja para cada uma tera valores diferentes de acordo com o tamanho do mesmo
		areaSolida.x = 14;
		areaSolida.y = 20;
		areaSolida.width = 16;
		areaSolida.height = 16;
		areaSolidaPadraoX = areaSolida.x;
		areaSolidaPadraoY = areaSolida.y;
		areadeAtaque.width = 20;
		areadeAtaque.height = 20;
		
		pegarImagem();
		pegarImagemdeAtaque();
	}
	
	
	public void pegarImagem() {
		
		cima1 = setup("/criaturas/CRI_Cima_Ogro_01", gp.tileSize, gp.tileSize);
		cima2 = setup("/criaturas/CRI_Cima_Ogro_02", gp.tileSize, gp.tileSize);
		cima3 = setup("/criaturas/CRI_Cima_Ogro_03", gp.tileSize, gp.tileSize);
		baixo1 = setup("/criaturas/CRI_Baixo_Ogro_01", gp.tileSize, gp.tileSize);
		baixo2 = setup("/criaturas/CRI_Baixo_Ogro_02", gp.tileSize, gp.tileSize);
		baixo3 = setup("/criaturas/CRI_Baixo_Ogro_03", gp.tileSize, gp.tileSize);
		esquerda1 = setup("/criaturas/CRI_Esquerda_Ogro_01", gp.tileSize, gp.tileSize);
		esquerda2 = setup("/criaturas/CRI_Esquerda_Ogro_02", gp.tileSize, gp.tileSize);
		esquerda3 = setup("/criaturas/CRI_Esquerda_Ogro_03", gp.tileSize, gp.tileSize);
		direita1 = setup("/criaturas/CRI_Direita_Ogro_01", gp.tileSize, gp.tileSize);
		direita2 = setup("/criaturas/CRI_Direita_Ogro_02", gp.tileSize, gp.tileSize);
		direita3 = setup("/criaturas/CRI_Direita_Ogro_03", gp.tileSize, gp.tileSize);
	}
	
	public void pegarImagemdeAtaque() {
		
		
		cimaATK1 = setup("/criaturas/CRI_Cima_Ogro_Ataque_01", gp.tileSize, gp.tileSize*2); // <- Nessa sessao foi configurado uma outra escala de imagem já que agora a imagem nao é só 16x16 e sim
		cimaATK2 = setup("/criaturas/CRI_Cima_Ogro_Ataque_02", gp.tileSize, gp.tileSize*2); // 32x16 ou 16x32, entao multiplicamos os valor por 2 da largura ou da altura para bater com o tamanho esperado.
		cimaATK3 = setup("/criaturas/CRI_Cima_Ogro_Ataque_03", gp.tileSize, gp.tileSize*2);
			
		baixoATK1 = setup("/criaturas/CRI_Baixo_Ogro_Ataque_01", gp.tileSize, gp.tileSize*2); // <- *2 Algura
		baixoATK2 = setup("/criaturas/CRI_Baixo_Ogro_Ataque_02", gp.tileSize, gp.tileSize*2);
		baixoATK3 = setup("/criaturas/CRI_Baixo_Ogro_Ataque_03", gp.tileSize, gp.tileSize*2);
		
		esquerdaATK1 = setup("/criaturas/CRI_Esquerda_Ogro_Ataque_01", gp.tileSize*2, gp.tileSize); // <- *2 Largura
		esquerdaATK2 = setup("/criaturas/CRI_Esquerda_Ogro_Ataque_02", gp.tileSize*2, gp.tileSize);
		esquerdaATK3 = setup("/criaturas/CRI_Esquerda_Ogro_Ataque_03", gp.tileSize*2, gp.tileSize);
	
		direitaATK1 = setup("/criaturas/CRI_Direita_Ogro_Ataque_01", gp.tileSize*2, gp.tileSize);
		direitaATK2 = setup("/criaturas/CRI_Direita_Ogro_Ataque_02", gp.tileSize*2, gp.tileSize);
		direitaATK3 = setup("/criaturas/CRI_Direita_Ogro_Ataque_03", gp.tileSize*2, gp.tileSize);	
	}
	public void setAcao() {
			
		if ( noCaminho == true) {
			
			//verifica se parou de te seguir
			checarSeParouDeSeguir(gp.jogador, 15, 100);
				
			buscarCaminho(getGoalCol(gp.jogador), getGoalRow(gp.jogador));
			
		}
		else {
			
			checarSeComecouSeguir(gp.jogador, 5, 100);
			
			getDirecaoAleatoria();
		}
		
		if (atacando == false) {
			checarseAtacaOuNao(30, gp.tileSize*4, gp.tileSize);
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
