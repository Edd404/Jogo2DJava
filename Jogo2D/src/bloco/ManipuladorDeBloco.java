package bloco;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;





import javax.imageio.ImageIO;

import main.GamePanel;
import main.Utilitarios;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class ManipuladorDeBloco {
	
	
	GamePanel gp;
	public Bloco[] bloco;
	public int MapaCoordenadas[][][];
	boolean exibirCaminho = true;
	ArrayList<String> nomeArquivos = new ArrayList<>();
	ArrayList<String> colisaoStatus = new ArrayList<>();
	
	
	//O construtor e o metodo PegarImagem abaixo é onde podemos incluir os blocos do jogo, ele irá fazer a leitura da posicao no mapa e substituirá o numero correspondente pelo item inserido
	//no método PegarImagem
	
	public ManipuladorDeBloco(GamePanel gp) {
		
		this.gp = gp;
		
		//Leitor do arquivo de colisao
		InputStream is = getClass().getResourceAsStream("/mapas/colisao.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		//Localizar o nome dos blocos e o status de colisao
		String line;
		
		try {
			while(( line = br.readLine()) != null ) {
				nomeArquivos.add(line);
				colisaoStatus.add(br.readLine());
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Inicializa o array dos blocos
		bloco = new Bloco[nomeArquivos.size()];
		pegarImagemBloco();
		
		//Localizar os tamanhos maximos de linhas e colunas
		is = getClass().getResourceAsStream("/mapas/MapaMundo.txt");
		br = new BufferedReader(new InputStreamReader(is));
		
		try {
			String line2 = br.readLine();
			String maxBlocos[] = line2.split(" ");
			
			gp.tamanhoMaximoColuna = maxBlocos.length;
			gp.tamanhoMaximoLinha =  maxBlocos.length;
			MapaCoordenadas = new int[gp.maxMapas][gp.tamanhoMaximoColuna][gp.tamanhoMaximoLinha];	
			
			br.close();
			
		}catch(IOException e) {
			System.out.println("Exception!");
		}	
		

		carregarMapa("/mapas/MapaMundo.txt", 0);
		carregarMapa("/mapas/interior_01.txt", 1);
	}
	
	public void pegarImagemBloco() {
		
		
		for ( int i = 0; i < nomeArquivos.size(); i++) {
			
			String nomeArquivo;
			boolean colisao;
			
			//Localizar o nome
			nomeArquivo = nomeArquivos.get(i);
			
			//Localizar o status de colisao
			if ( colisaoStatus.get(i).equals("true")) {
				colisao = true;
			}
			else {
				colisao = false;
			}
			
			setup(i, nomeArquivo, colisao);
		}
	}
	public void setup(int index, String nomeDaImagem, boolean colisao) {
		
		Utilitarios utl = new Utilitarios();
		
		try {
			bloco[index] = new Bloco();
			bloco[index].imagem = ImageIO.read(getClass().getResourceAsStream("/blocos/" + nomeDaImagem));
			bloco[index].imagem = utl.escalaDaImagem(bloco[index].imagem, gp.tileSize, gp.tileSize);
			bloco[index].colisao = colisao;
				
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//Aqui é onde é calculado e carregado o mapa atravéz do metodo carregarmapa, essa parte é bem confusa mas basicamente ele escaneia a area da janela fazendo o desenho com o método Draw abaixo
	
	public void carregarMapa(String Caminho, int mapa) {
		
		try {
			InputStream is = getClass().getResourceAsStream(Caminho);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int coluna = 0;
			int linha = 0;
			
			while(coluna < gp.tamanhoMaximoColuna && linha < gp.tamanhoMaximoLinha) {
				String line = br.readLine();
				while(coluna < gp.tamanhoMaximoColuna) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[coluna]);
					MapaCoordenadas[mapa][coluna][linha] = num;
					coluna++;
				}
				if (coluna == gp.tamanhoMaximoColuna) {
					coluna = 0;
					linha++;
				}
			}
			br.close();
			
		}catch(Exception e) {		 		
		}		
	}
	public void draw(Graphics2D g2) {
		
		int MundoColuna = 0;
		int MundoLinha = 0;
		
		
		while (MundoColuna < gp.tamanhoMaximoColuna && MundoLinha < gp.tamanhoMaximoLinha) {
			
			int blocoNum = MapaCoordenadas[gp.mapaAtual][MundoColuna][MundoLinha];
			
			int mundox = MundoColuna * gp.tileSize;
			int mundoy = MundoLinha * gp.tileSize;
			int telax = mundox - gp.jogador.mundox + gp.jogador.telax;
			int telay = mundoy - gp.jogador.mundoy + gp.jogador.telay;
			
		
			
			if ( mundox + gp.tileSize > gp.jogador.mundox - gp.jogador.telax &&
				 mundox - gp.tileSize < gp.jogador.mundox + gp.jogador.telax &&
				 mundoy + gp.tileSize > gp.jogador.mundoy - gp.jogador.telay &&
				 mundoy - gp.tileSize < gp.jogador.mundoy + gp.jogador.telay) {
		
			g2.drawImage(bloco[blocoNum].imagem, telax, telay, gp.tileSize, gp.tileSize, null);
			}
			
				
		
			MundoColuna++;
			
			if (MundoColuna == gp.tamanhoMaximoColuna) {
				MundoColuna = 0;
			
				MundoLinha++;
				
			}
		}
		if ( exibirCaminho == true) {
			g2.setColor(new Color(255, 0, 0, 70));
			
			for ( int i = 0; i < gp.pFinder.pathList.size(); i++) {
				
				int mundox = gp.pFinder.pathList.get(i).coluna * gp.tileSize;
				int mundoy = gp.pFinder.pathList.get(i).linha * gp.tileSize;
				int telax = mundox - gp.jogador.mundox + gp.jogador.telax;
				int telay = mundoy - gp.jogador.mundoy + gp.jogador.telay;
				
				g2.fillRect(telax, telay, gp.tileSize, gp.tileSize);
			}
		}
	}	
}
