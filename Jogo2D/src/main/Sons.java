package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sons {
	
	Clip clip;
	URL urlDosSons[] = new URL[50];
	FloatControl fc;
	int escaladeSom = 3;
	float volume;
	
	public Sons() {
		
		urlDosSons[0] = getClass().getResource("/som/TemaPrincipal.wav");
		urlDosSons[1] = getClass().getResource("/som/Som_Inventario_Linha.wav");
		urlDosSons[2] = getClass().getResource("/som/Som_Inventario_Coluna.wav");
		urlDosSons[3] = getClass().getResource("/som/Coin01.wav");
		urlDosSons[4] = getClass().getResource("/som/SFX_Beber.wav");
		urlDosSons[5] = getClass().getResource("/som/SFX_Ataque_Espada.wav");
		urlDosSons[6] = getClass().getResource("/som/SFX_LevelUP.wav");
		urlDosSons[7] = getClass().getResource("/som/SFX_Flecha.wav");
		urlDosSons[8] = getClass().getResource("/som/SFX_Recuperar_Vida.wav");
		urlDosSons[9] = getClass().getResource("/som/SFX_CortarArvore.wav");
		urlDosSons[10] = getClass().getResource("/som/SFX_HitArvore.wav");
		urlDosSons[11] = getClass().getResource("/som/SFX_GameOver.wav");
		urlDosSons[12] = getClass().getResource("/som/SFX_AbrirPorta.wav");
		urlDosSons[13] = getClass().getResource("/som/SFX_Abrir_Bau.wav");
		urlDosSons[14] = getClass().getResource("/som/sleep.wav");
		urlDosSons[15] = getClass().getResource("/som/SFX_Bloqueio.wav");
		urlDosSons[16] = getClass().getResource("/som/SFX_Parry.wav");
		
		
	}
	
	public void setFile(int i) {
		
		try {		
			AudioInputStream ais = AudioSystem.getAudioInputStream(urlDosSons[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			checarVolume();
			
		}catch(Exception e) {		
		}	
	}
	
	public void play() {
		
		clip.start();
	}

	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		
		clip.stop();
	}
	public void checarVolume() {
		
		switch(escaladeSom) {
		case 0: volume = -80f;break;
		case 1: volume = -20f;break;
		case 2: volume = -12f;break;
		case 3: volume = -5f;break;
		case 4: volume = 1f;break;
		case 5: volume = 6f;break;
		}
		fc.setValue(volume);
	}
}
