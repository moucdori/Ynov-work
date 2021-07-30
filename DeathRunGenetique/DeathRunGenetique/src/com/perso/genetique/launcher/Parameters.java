package com.perso.genetique.launcher;

import java.awt.Color;
import java.util.Random;

import com.perso.genetique.graphic.Frame;
import com.perso.genetique.graphic.PanelRight;

public class Parameters {
	private static int nGen = 0;
	private static int MAX_GEN;
	private static int NB_ALLIES;
	private static int NB_ALLIES_ALIVE = NB_ALLIES;
	
	private static int LIGNE_ARRIVEE = Frame.SCREEN_HEIGHT - 100;
	private static int LEFT_BORDER = 60;
	private static int RIGHT_BORDER = (Frame.SCREEN_WIDTH/2) - 80;
	private static int TOP_BORDER = 20;
	private static int SPAWN_X = (RIGHT_BORDER + LEFT_BORDER)/2;
	private static int SPAWN_Y = 140;
	private static int NB_BOMB;

	private static int bestfitness = 0;
	public static double crossoverRate;
	public static double mutationDeleteRate;
	public static double mutationAddRate;
	public static double mutationsRate;

	public static Random RANDOM = new Random();
	public static boolean FINISH = false;
	public static boolean START = false;
	public static boolean READ_NB_ALLIES = false;
	public static boolean POPULATION_CREATED = false;

	
	public static int getBestFitness() {
		return bestfitness;
	}

	public static void setBestFitness(int i) {
		bestfitness = i;
	}

	public static int getNbAllies() {
		return NB_ALLIES;
	}

	public static int getArrivee() {
		return LIGNE_ARRIVEE;
	}

	public static int getLeftBorder() {
		return LEFT_BORDER;
	}

	public static int getRightBorder() {
		return RIGHT_BORDER;
	}

	public static int getTopBorder() {
		return TOP_BORDER;
	}

	public static int getSpawnX() {
		return SPAWN_X;
	}

	public static int getSpawnY() {
		return SPAWN_Y;
	}

	public static void setGen(int i) {
		nGen = i;
	}

	public static int getGen() {
		return nGen;
	}

	public static int getMaxGen() {
		return MAX_GEN;
	}

	public static void setAlliesAlive(int i) {
		NB_ALLIES_ALIVE = i;
	}

	public static int getAlliesAlive() {
		return NB_ALLIES_ALIVE;
	}

	public static int getNbBomb() {
		return NB_BOMB;
	}

	public static Color randomColor() {
		Random r = new Random();
		int rnd = r.nextInt(11);
		switch(rnd) {
		case 0:
			return Color.BLUE;
		case 1:
			return Color.CYAN;
		case 2:
			return Color.GRAY;
		case 3:
			return Color.GREEN;
		case 4:
			return Color.LIGHT_GRAY;
		case 5:
			return Color.MAGENTA;
		case 6:
			return Color.ORANGE;
		case 7:
			return Color.PINK;
		case 8:
			return Color.RED;
		case 9:
			return Color.WHITE;
		case 10:
			return Color.YELLOW;
		default:
			return Color.BLACK;
		}

	}

	public static void init() { 

		if(!PanelRight.nA.getText().isEmpty())
			NB_ALLIES = Integer.parseInt(PanelRight.nA.getText());
		else
			NB_ALLIES = 10;
		
		if(!PanelRight.nB.getText().isEmpty())
			NB_BOMB = Integer.parseInt(PanelRight.nB.getText());
		else
			NB_BOMB = 20;
	
		if(!PanelRight.nG.getText().isEmpty())
			MAX_GEN = Integer.parseInt(PanelRight.nG.getText());
		else
			MAX_GEN = 100;
		}
}
