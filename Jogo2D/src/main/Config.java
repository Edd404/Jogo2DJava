package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

	GamePanel gp;
	
	public Config(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void salvarConfig() {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
			
			//Tella cheia
			if(gp.fullScreenOn == true) {
				bw.write("On");
			}
			if(gp.fullScreenOn == false) {
				bw.write("Off");
			}
			bw.newLine();
			
			//Volume da musica
			bw.write(String.valueOf(gp.musica.escaladeSom));
			bw.newLine();
			
			//Volume dos efeitos sonoros
			bw.write(String.valueOf(gp.efeitoSonoro.escaladeSom));
			bw.newLine();
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void carregarConfigs() {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("config.txt"));
			
			String s = br.readLine();
			
			//Tela Cheia
			if(s.equals("On")){
				gp.fullScreenOn = true;
			}
			if(s.equals("Off")){
				gp.fullScreenOn = false;
			}
			
			//Volume da musica
			s = br.readLine();
			gp.musica.escaladeSom = Integer.parseInt(s);
			
			//Volume dos efeitos sonoros
			s = br.readLine();
			gp.efeitoSonoro.escaladeSom = Integer.parseInt(s);
			
			br.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
