package data;

import java.io.Serializable;
import java.util.ArrayList;

public class salvarInformacoes implements Serializable {

	
	//Status do jogador
	int level;
	int vidaMaxima;
	int vida;
	int manaMaxima;
	int mana;
	int forca;
	int agilidade;
	int exp;
	int moeda;
	int proxLevelExp;
	
	//Itens do jogador
	ArrayList <String> itemNomes = new ArrayList<>();
	ArrayList <Integer> itemQuantidade = new ArrayList<>();
	int armaAtualSlot;
	int escudoAtualSlot;
	
	//Salvar as informacoes dos itens que já foram pegos no mapa
	String mapaObjetosNomes [][];
	int mapaObjetosMundoX [][];
	int mapaObjetosMundoY [][];
	String mapaLootNomes [][];
	boolean mapaLootAbertos [][];
	
}
