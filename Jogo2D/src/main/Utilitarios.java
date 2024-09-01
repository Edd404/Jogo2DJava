package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Utilitarios {

	//Aqui é o método para que as imagens entrem em buffer antes de serem desenhadas no draw, isso poupara recursos do PC caso haja escalabilidade do jogo
	public BufferedImage escalaDaImagem (BufferedImage original, int largura, int altura) {
		
		BufferedImage esImagem = new BufferedImage(largura, altura, original.getType());
		Graphics2D g2 = esImagem.createGraphics();
		g2.drawImage(original, 0, 0, largura, altura, null);
		g2.dispose();
		
		return esImagem;		
	}
}
