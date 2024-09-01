package main;

import javax.swing.JFrame;



public class Main {

	public static JFrame Janela;
	
	public static void main(String[] args) {
		
		
		//Configuracoes da Janela
		Janela = new JFrame();
		Janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Janela.setResizable(false);
		Janela.setTitle("Joguinho 2D");
		
		GamePanel gamePanel = new GamePanel();
		Janela.add(gamePanel);
		
		gamePanel.config.carregarConfigs();
		if(gamePanel.fullScreenOn == true) {
			Janela.setUndecorated(true);
		}
		
		Janela.pack();
		
		Janela.setLocationRelativeTo(null);
		Janela.setVisible(true);
		
		gamePanel.setupDoJogo();
		gamePanel.startGameThread();
		
		
	}

}
