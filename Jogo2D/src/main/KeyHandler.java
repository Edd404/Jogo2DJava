package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	public boolean pCima, pBaixo, pEsquerda, pDireita, enterParaFalar, tecladeAtirar, espacoBloquear;
	boolean mostrarDebug = false;

	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		//Na tela de titulo
		if(gp.gameState == gp.tituloState) {
			tituloState(code);
			}
				
		//Play State
		else if(gp.gameState == gp.playState) {
			playState(code);
					
		}
		//Jogo em PAUSE
		else if( gp.gameState == gp.pauseState) {
			
			pauseState(code);
		}
		//Jogo em cena de dialogos
		else if(gp.gameState == gp.dialogoState) {
			dialogoState(code);	
		}
		//Jogo em cena de Status do Personagem(Onde mostra os atributos, como agilidade, vida etc
		else if(gp.gameState == gp.statusPersonagem) {
			statusState(code);
		}	
		//Menu de opcoes
		else if(gp.gameState == gp.optionsState) {
			optionsState(code);
		}
		//Game Over
		else if(gp.gameState == gp.gameOver) {
			gameOverState(code);
		}
		//Mercador
		else if(gp.gameState == gp.telaMercador) {
			telaMercador(code);
		}
		//Mapa
	//	else if(gp.gameState == gp.mapaState) {
	//		mapaState(code);
	//	}
		//DEBUG
		if (code == KeyEvent.VK_T) {
			if(mostrarDebug == false) {
				mostrarDebug = true;
			}
			else if(mostrarDebug == true) {
				mostrarDebug = false;
			}
		}	
	}
	public void telaMercador(int code) {
		
		if ( code == KeyEvent.VK_ENTER) {
			enterParaFalar = true;
		}
		if (gp.ui.subTelas == 0) {
			if(code == KeyEvent.VK_W) {
				gp.ui.numeroDeComandos--;
				if(gp.ui.numeroDeComandos < 0) {
					gp.ui.numeroDeComandos = 2;
				}
				gp.playSFX(1);	
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.numeroDeComandos++;
				if(gp.ui.numeroDeComandos > 2) {
					gp.ui.numeroDeComandos = 0;
				}
				gp.playSFX(1);	
			}
		}
		if ( gp.ui.subTelas == 1) {
			inventarioNpc(code);
			if( code == KeyEvent.VK_ESCAPE) {
				gp.ui.subTelas = 0;
			}
		}
		if ( gp.ui.subTelas == 2) {
			inventarioJogador(code);
			if( code == KeyEvent.VK_ESCAPE) {
				gp.ui.subTelas = 0;
			}
		}
	}
	public void tituloState(int code) {
		
		
			if (code == KeyEvent.VK_W) {
				gp.ui.numeroDeComandos--;
				if(gp.ui.numeroDeComandos < 0) {
					gp.ui.numeroDeComandos = 2;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.numeroDeComandos++;
				if(gp.ui.numeroDeComandos > 2) {
					gp.ui.numeroDeComandos = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.numeroDeComandos == 0) {
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if (gp.ui.numeroDeComandos == 1) {
					gp.saveLoad.load();
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if (gp.ui.numeroDeComandos == 2) {
					System.exit(0);
				}
				
			}
	}
	public void playState(int code) {
		
		if (code == KeyEvent.VK_W) {
			pCima = true;
		}
		if (code == KeyEvent.VK_S) {
			pBaixo = true;
		}
		if (code == KeyEvent.VK_A) {
			pEsquerda = true;
		}
		if (code == KeyEvent.VK_D) {
			pDireita = true;
		}
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;						
		}	
		if ( code == KeyEvent.VK_C) {
			gp.gameState = gp.statusPersonagem;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterParaFalar = true;					
		}
		if (code == KeyEvent.VK_E) {
			tecladeAtirar = true;					
		}
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionsState;				
		}
		if (code == KeyEvent.VK_SPACE) {
			espacoBloquear = true;			
		}
		//if (code == KeyEvent.VK_M) {
		//	gp.gameState = gp.mapaState;				
		//}
	}
	public void pauseState(int code) {
	
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;						
		}
	}
	public void dialogoState(int code) {
	
		if (code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;						
		}
	}
	public void statusState(int code) {
		
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
	
		if(code == KeyEvent.VK_ENTER) {
			gp.jogador.selecionarItem();
		}
		inventarioJogador(code);
	}
	
	public void optionsState(int code) {
		
		if( code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
			if (code == KeyEvent.VK_ENTER) {
				enterParaFalar = true;
			}
			
		int maximoComandos = 0;
		switch(gp.ui.subTelas) {
		case 0: maximoComandos = 5; break;
		case 3: maximoComandos = 1; break;
		}
		
		if( code == KeyEvent.VK_W) {
			gp.ui.numeroDeComandos--;
			gp.playSFX(1);
			if ( gp.ui.numeroDeComandos < 0) {
				gp.ui.numeroDeComandos = maximoComandos;
			}
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.numeroDeComandos++;
			gp.playSFX(2);
			if (gp.ui.numeroDeComandos > maximoComandos) {
				gp.ui.numeroDeComandos = 0;
				}
			}
		if (code == KeyEvent.VK_A) {
			if(gp.ui.subTelas == 0) {
				if(gp.ui.numeroDeComandos == 1 && gp.musica.escaladeSom > 0) {
					gp.musica.escaladeSom--;
					gp.musica.checarVolume();
					gp.playSFX(1);
				}
				if(gp.ui.numeroDeComandos == 2 && gp.efeitoSonoro.escaladeSom > 0) {
						gp.efeitoSonoro.escaladeSom--;
						gp.playSFX(1);
				}
			}
		}
		if (code == KeyEvent.VK_D) {
				if(gp.ui.subTelas == 0) {
				if(gp.ui.numeroDeComandos == 1 && gp.musica.escaladeSom < 5) {
					gp.musica.escaladeSom++;
					gp.musica.checarVolume();
					gp.playSFX(1);
				}
				if(gp.ui.numeroDeComandos == 2 && gp.efeitoSonoro.escaladeSom < 5) {
					gp.efeitoSonoro.escaladeSom++;
					gp.playSFX(1);
				}
			}
		}		
	}
	
	public void gameOverState(int code) {
		
		if(code == KeyEvent.VK_W) {
			gp.ui.numeroDeComandos--;
			if(gp.ui.numeroDeComandos < 0) {
				gp.ui.numeroDeComandos = 1;
			}
			gp.playSFX(1);
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.numeroDeComandos++;
			if(gp.ui.numeroDeComandos > 1) {
				gp.ui.numeroDeComandos = 0;
			}
			gp.playSFX(1);
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.numeroDeComandos == 0) {
				gp.gameState = gp.playState;
				gp.resetGame(false);
				gp.playMusic(0);
			}
			else if(gp.ui.numeroDeComandos == 1) {
				gp.gameState = gp.tituloState;
				gp.resetGame(true);
			}
		}	
	}
	//public void mapaState(int code) {
		
	//	if( code == KeyEvent.VK_M) {
		//	gp.gameState = gp.playState;
		//}
	//}
	public void inventarioJogador(int code) {
		
		if(code == KeyEvent.VK_W) {
			if (gp.ui.jogadorSlotLinha != 0) {
				gp.ui.jogadorSlotLinha--;
				gp.playSFX(1);
			}
		}
		if(code == KeyEvent.VK_A) {
			if ( gp.ui.jogadorSlotColuna != 0) {
				gp.ui.jogadorSlotColuna--;
				gp.playSFX(2);
			}		
		}
		if(code == KeyEvent.VK_S) {
			if (gp.ui.jogadorSlotLinha != 3) {
				gp.ui.jogadorSlotLinha++;
				gp.playSFX(1);
			}		
		}
		if(code == KeyEvent.VK_D) {
			if (gp.ui.jogadorSlotColuna != 4) {
				gp.ui.jogadorSlotColuna++;
				gp.playSFX(2);
			}			
		}	
		
	}
	
	public void inventarioNpc(int code) {
		
		if(code == KeyEvent.VK_W) {
			if (gp.ui.npcSlotLinha != 0) {
				gp.ui.npcSlotLinha--;
				gp.playSFX(1);
			}
		}
		if(code == KeyEvent.VK_A) {
			if ( gp.ui.npcSlotColuna != 0) {
				gp.ui.npcSlotColuna--;
				gp.playSFX(2);
			}		
		}
		if(code == KeyEvent.VK_S) {
			if (gp.ui.npcSlotLinha != 3) {
				gp.ui.npcSlotLinha++;
				gp.playSFX(1);
			}		
		}
		if(code == KeyEvent.VK_D) {
			if (gp.ui.npcSlotColuna != 4) {
				gp.ui.npcSlotColuna++;
				gp.playSFX(2);
			}			
		}	
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			pCima = false;
		}
		if (code == KeyEvent.VK_S) {
			pBaixo = false;
		}
		if (code == KeyEvent.VK_A) {
			pEsquerda = false;
		}
		if (code == KeyEvent.VK_D) {
			pDireita = false;
		}
		if (code == KeyEvent.VK_E) {
			tecladeAtirar = false;					
		}	
		if (code == KeyEvent.VK_SPACE) {
			espacoBloquear = false;					
		}
		if (code == KeyEvent.VK_ENTER) {
			enterParaFalar = false;					
		}
	} 
}
