package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entidade.Entidade;
import main.GamePanel;
import objeto.OBJ_Barraca;
import objeto.OBJ_Bau;
import objeto.OBJ_Chave;
import objeto.OBJ_Escudo_Madeira;
import objeto.OBJ_Escudo_Metal;
import objeto.OBJ_Espada_Padrao;
import objeto.OBJ_Lanterna;
import objeto.OBJ_Machado;
import objeto.OBJ_Pocao_Vermelha;
import objeto.OBJ_Porta;

public class SaveLoad {

	GamePanel gp;
	
	public SaveLoad(GamePanel gp) {
		this.gp = gp;
	}
	public Entidade getObjeto(String itemNome) {
		
		Entidade obj = null;
		
		switch(itemNome) {
		case "Barraca" : obj = new OBJ_Barraca(gp); break;
		case "Chave" : obj = new OBJ_Chave(gp); break;
		case "Escudo de Madeira" : obj = new OBJ_Escudo_Madeira(gp); break;
		case "Escudo de Metal" : obj = new OBJ_Escudo_Metal(gp); break;
		case "Espada Básica" : obj = new OBJ_Espada_Padrao(gp); break;
		case "Lanterna" : obj = new OBJ_Lanterna(gp); break;
		case "Machado de Lenhador" : obj = new OBJ_Machado(gp); break;
		case "Poção Vermelha" : obj = new OBJ_Pocao_Vermelha(gp); break;
		case "Porta" : obj = new OBJ_Porta(gp); break;
		case "Bau" : obj = new OBJ_Bau(gp); break;
		
		}
		return obj;
		
	}
	public void save() {
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
			
			salvarInformacoes si = new salvarInformacoes();
			
			//Status do Jogador
			si.level = gp.jogador.level;
			si.vidaMaxima = gp.jogador.vidaMaxima;
			si.vida = gp.jogador.vida;
			si.manaMaxima = gp.jogador.manaMaxima;
			si.mana = gp.jogador.mana;
			si.forca = gp.jogador.forca;
			si.agilidade = gp.jogador.agilidade;
			si.exp = gp.jogador.exp;
			si.moeda = gp.jogador.moeda;
			si.proxLevelExp = gp.jogador.proxLevelExp;
			
			//Itens do jogador
			for ( int i = 0; i < gp.jogador.inventario.size(); i++) {
				si.itemNomes.add(gp.jogador.inventario.get(i).nome);
				si.itemQuantidade.add(gp.jogador.inventario.get(i).quantidade);
			}
			//Equipamentos atuais do jogador
			si.armaAtualSlot = gp.jogador.getArmaAtualSlot();
			si.escudoAtualSlot = gp.jogador.getEscudoAtualSlot();
			
			//Itens que já foram pegos pelo mapa
			si.mapaObjetosNomes = new String[gp.maxMapas][gp.obj[1].length];
			si.mapaObjetosMundoX = new int[gp.maxMapas][gp.obj[1].length];
			si.mapaObjetosMundoY = new int[gp.maxMapas][gp.obj[1].length];
			si.mapaLootNomes = new String[gp.maxMapas][gp.obj[1].length];
			si.mapaLootAbertos = new boolean[gp.maxMapas][gp.obj[1].length];
			
			for ( int numeroMapa = 0; numeroMapa < gp.maxMapas; numeroMapa++) {
				for ( int i = 0; i < gp.obj[1].length; i++) {
					if( gp.obj[numeroMapa][i] == null) {
						si.mapaObjetosNomes[numeroMapa][i] = "NA";
					}
					else {
						si.mapaObjetosNomes[numeroMapa][i] = gp.obj[numeroMapa][i].nome;
						si.mapaObjetosMundoX[numeroMapa][i] = gp.obj[numeroMapa][i].mundox;
						si.mapaObjetosMundoY[numeroMapa][i] = gp.obj[numeroMapa][i].mundoy;
						if ( gp.obj[numeroMapa][i].loot != null) {
							si.mapaLootNomes[numeroMapa][i] = gp.obj[numeroMapa][i].loot.nome;		
						}
						si.mapaLootAbertos[numeroMapa][i] = gp.obj[numeroMapa][i].aberto;				
					}
				}
			}
			
			//Escrevar as informacoes do personagem no arquivo DAT
			oos.writeObject(si);
			
		} catch (Exception e){
			System.out.println("Save Exception!");
		}
	}
	public void load() {
		
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
			
			//Ler as informacoes que foram salvas no SAVE DAT
			salvarInformacoes si = (salvarInformacoes)ois.readObject();
			
			//Status do jogador
			gp.jogador.level = si.level;
			gp.jogador.vidaMaxima = si.vidaMaxima;
			gp.jogador.vida = si.vida;
			gp.jogador.manaMaxima = si.manaMaxima;
			gp.jogador.mana = si.mana;
			gp.jogador.forca = si.forca;
			gp.jogador.agilidade = si.agilidade;
			gp.jogador.exp = si.exp;
			gp.jogador.moeda = si.moeda;
			gp.jogador.proxLevelExp = si.proxLevelExp;
			
			//Inventário do jogador
			gp.jogador.inventario.clear();
			for ( int i = 0; i < si.itemNomes.size(); i++) {
				gp.jogador.inventario.add(getObjeto(si.itemNomes.get(i)));
				gp.jogador.inventario.get(i).quantidade = si.itemQuantidade.get(i);
			}
			//Equipamentos atuais do jogador
			gp.jogador.armaAtual = gp.jogador.inventario.get(si.armaAtualSlot);
			gp.jogador.escudoAtual = gp.jogador.inventario.get(si.escudoAtualSlot);
			gp.jogador.getAtaque();
			gp.jogador.getDefesa();
			gp.jogador.pegarImagemdeAtaquedoJogador();
			
			//Itens que já foram pegos pelo mapa
			for ( int numeroMapa = 0; numeroMapa < gp.maxMapas; numeroMapa++) {
				for ( int i = 0; i < gp.obj[1].length; i++) {
					if ( si.mapaObjetosNomes [numeroMapa][i].equals("NA")) {
						gp.obj[numeroMapa][i] = null;
					}
					else {
						gp.obj[numeroMapa][i] = getObjeto(si.mapaObjetosNomes[numeroMapa][i]);
						gp.obj[numeroMapa][i].mundox = si.mapaObjetosMundoX[numeroMapa][i];
						gp.obj[numeroMapa][i].mundoy = si.mapaObjetosMundoY[numeroMapa][i];
						if ( si.mapaLootNomes[numeroMapa][i] != null) {
							gp.obj[numeroMapa][i].loot = getObjeto(si.mapaLootNomes[numeroMapa][i]);
						}
						gp.obj[numeroMapa][i].aberto = si.mapaLootAbertos[numeroMapa][i];
						if (gp.obj[numeroMapa][i].aberto == true ) {
							gp.obj[numeroMapa][i].baixo1 = gp.obj[numeroMapa][i].imagem2;
						}
					}
				}
			}		
		}
		catch(Exception e){
			System.out.println("Load Exception!");
		}
	}
}
