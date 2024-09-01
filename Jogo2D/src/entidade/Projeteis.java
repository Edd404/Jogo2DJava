package entidade;

import main.GamePanel;

public class Projeteis extends Entidade{

	Entidade usuario;
	
	public Projeteis(GamePanel gp) {
		super(gp);
		
		
	}
	public void set(int mundox, int mundoy, String direcao, boolean vivo, Entidade usuario) {
		
		this.mundox = mundox;
		this.mundoy = mundoy;
		this.direcao = direcao;
		this.vivo = vivo;
		this.usuario = usuario;
		this.vida = this.vidaMaxima;
		
	}
	public void update() {
		
		if ( usuario == gp.jogador) {
			int criaturaIndex = gp.cColisao.checarEntidade(this, gp.criaturas);
			if ( criaturaIndex != 999) {
				gp.jogador.danodaCriatura(criaturaIndex,this, ataque, knockBackPoder);
				gerarParticulas(usuario.projetil, gp.criaturas[gp.mapaAtual][criaturaIndex]);
				vivo = false;
			}
		}
		
		if (usuario != gp.jogador) {
			boolean contatoJogador = gp.cColisao.checarJogador(this);
			if ( gp.jogador.invencibilidade == false && contatoJogador == true) {
				danoJogador(ataque);
				gerarParticulas(usuario.projetil, gp.jogador);
				vivo = false;
			}
		}
		
		switch(direcao) {
		case "Cima": mundoy -= velocidade; break;
		case "Baixo": mundoy += velocidade; break;
		case "Esquerda": mundox -= velocidade; break;
		case "Direita": mundox += velocidade; break;
		}
		
		vida--;
		if(vida <= 0) {
			vivo = false;
		}
		contadorDeSprites++;
		if( contadorDeSprites > 12) {
			if(numeroDoSprite == 1) {
				numeroDoSprite =  2;
			}
			else if( numeroDoSprite == 2) {
				numeroDoSprite = 1;
			}
			contadorDeSprites = 0;
		}
	}
	public boolean capacidadeRecursos(Entidade usuario) {
		
		
		boolean capacidadeRecursos = false;
		return capacidadeRecursos;
	}
	public void diminuirRecursos(Entidade usuario) {}
		
}
		
		
		
	

