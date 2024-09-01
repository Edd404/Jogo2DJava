package bloco_interativo;

import main.GamePanel;

public class BI_Tronco extends BlocosInterativos {

	
	GamePanel gp;
	
	public BI_Tronco(GamePanel gp, int coluna, int linha) {
		super(gp, coluna, linha);
		this.gp = gp;
		
		this.mundox = gp.tileSize * coluna;
		this.mundoy = gp.tileSize * linha;
		
		baixo1 = setup("/blocosinterativos/Arvore_Morta02",gp.tileSize, gp.tileSize);
		
		areaSolida.x = 0;
		areaSolida.y = 0;
		areaSolida.width = 0;
		areaSolida.height = 0;
		areaSolidaPadraoX = areaSolida.x;
		areaSolidaPadraoX = areaSolida.y;
	}
}
