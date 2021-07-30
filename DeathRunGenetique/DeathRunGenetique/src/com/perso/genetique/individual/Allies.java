package com.perso.genetique.individual;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.perso.genetique.launcher.Parameters;

public class Allies extends Individual {

	private List<Move> deplacement = new ArrayList<Move>();
	private int MOVE_INDEX = 0;
	private int fitness = 1000;
	private boolean HERITAGE = false;
	private boolean BEST = false;
	private BufferedImage skin = this.randomSkin();
	private Color color = Color.red;

	public void setBest(boolean b) {
		BEST = b;
	}

	public BufferedImage getSkin() {
		return skin;
	}
	
	private BufferedImage randomSkin() {

		try {
			Random r = new Random();
			int rnd = r.nextInt(16);
			switch(rnd) {
			case 0:
				skin = ImageIO.read(new File("img\\aquali.gif"));
				return skin;
			case 1:
				skin = ImageIO.read(new File("img\\arcko.gif"));
				return skin;
			case 2:
				skin = ImageIO.read(new File("img\\feunard.gif"));
				return skin;
			case 3:
				skin = ImageIO.read(new File("img\\gobou.gif"));
				return skin;
			case 4:
				skin = ImageIO.read(new File("img\\jirachi.png"));
				return skin;
			case 5:
				skin = ImageIO.read(new File("img\\killer-metamorph.png"));
				return skin;
			case 6:
				skin = ImageIO.read(new File("img\\miaouss.gif"));
				return skin;
			case 7:
				skin = ImageIO.read(new File("img\\abra.png"));
				return skin;
			case 8:
				skin = ImageIO.read(new File("img\\carapuce.png"));
				return skin;
			case 9:
				skin = ImageIO.read(new File("img\\hericendre.png"));
				return skin;
			case 10:
				skin = ImageIO.read(new File("img\\aspicot.png"));
				return skin;
			case 11:
				skin = ImageIO.read(new File("img\\salameche.png"));
				return skin;
			case 12:
				skin = ImageIO.read(new File("img\\machoc.png"));
				return skin;
			case 13:
				skin = ImageIO.read(new File("img\\mini-draco.png"));
				return skin;
			case 14:
				skin = ImageIO.read(new File("img\\pikatoad.png"));
				return skin;
			case 15:
				skin = ImageIO.read(new File("img\\osselait.png"));
				return skin;
			default:
				return skin;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return skin;
	}

	public boolean getBest() {
		return BEST;
	}

	public Allies(Allies allies) {
		for(Move mv : allies.getDepl()){
			deplacement.add(mv);
		}
		MOVE_INDEX = allies.getMoveIndex();
		fitness = allies.getFitness();
		HERITAGE = allies.getHeritage();
		POS_X = allies.getPosX();
		POS_Y = allies.getPosY();
	}

	public Allies(List<Move> m) {
		for(Move mv : m){
			deplacement.add(mv);
		}
	}

	public Allies() {

	}

	public void mutate() {

		//MAJ
		//Suppression ?
		if(Parameters.RANDOM.nextDouble()<Parameters.mutationDeleteRate) {
			int moveIndex = Math.round(Parameters.RANDOM.nextInt(deplacement.size()*10)/10);
			for (int i =0; i < Math.round(deplacement.size()/5); i++) {
				if(moveIndex < deplacement.size())
					deplacement.remove(moveIndex);
			}
		}
		//Ajout ?
		if(Parameters.RANDOM.nextDouble()<Parameters.mutationAddRate) {
			int addIndex = Math.round(Parameters.RANDOM.nextInt(deplacement.size()*10)/10);
			List<Move> tmpArray = new ArrayList<Move>();
			for (int cpy = addIndex; cpy < deplacement.size(); cpy ++) {
				tmpArray.add(deplacement.get(cpy));
			}
			for (int i = 0; i < Math.round(deplacement.size()/5); i++) {
				Move newMove = new Move();
				newMove.setDirection(newMove.setRandDir());
				newMove.setDeplacement(Parameters.RANDOM.nextInt(4));
				if(addIndex < deplacement.size()) 
					deplacement.set(addIndex, newMove);
				else 
					deplacement.add(newMove);
				addIndex++;
			}
			for (int j = 0; j < tmpArray.size(); j++) {
				if(addIndex < deplacement.size()) {
					deplacement.set(addIndex, tmpArray.get(j));
				}
				else {
					deplacement.add(tmpArray.get(j));
				}
				addIndex++;
			}
		}
		//Remplacement d'une section ?
		if(Parameters.RANDOM.nextDouble()<Parameters.mutationsRate) {
			int putIndex = Math.round(Parameters.RANDOM.nextInt(deplacement.size()*10)/10);
			for (int i = 0; i < Math.round(deplacement.size()/5); i++) {
				Move newMove = new Move();
				newMove.setDirection(newMove.setRandDir());
				newMove.setDeplacement(Parameters.RANDOM.nextInt(4));
				if(putIndex + Math.round(deplacement.size()/5) > deplacement.size()) {
					deplacement.add(newMove);
				}
				else {
					deplacement.set(putIndex, newMove);
					putIndex++;
				}
			}
		}
	}

	public boolean getHeritage() {
		return HERITAGE;
	}

	public void setHeritage(boolean b) {
		HERITAGE = b;
	}

	public void setFitness() {
		fitness = Parameters.getArrivee() - getPosY();
	}

	public void resetFitness() {
		fitness = 1000;
	}

	public int getFitness() {
		return fitness;
	}

	public void incrMoveIndex() {
		MOVE_INDEX++;
	}

	public int getMoveIndex() {
		return MOVE_INDEX;
	}

	public void resetMoveIndex() {
		MOVE_INDEX = 0;
	}

	public void deplace() {
		Move m = deplacement.get(MOVE_INDEX);
		String dir = m.getDirection();
		int depl = m.getDeplacement();
		if(dir == "T" || dir == "B")
			setPositionY(dir, depl);
		else {
			setPositionX(dir, depl);
		}
		MOVE_INDEX++;
	}

	public void addDeplacement(String s, int d) {
		Move m = new Move();
		m.setDirection(s);
		m.setDeplacement(d);
		deplacement.add(m);
	}

	public Move getDeplacement() {
		try {
			if(deplacement.get(MOVE_INDEX) != null)
				return deplacement.get(MOVE_INDEX);
			else
				return null;
		}catch(Exception e) {
			return null;
		}
	}

	public List<Move> getDepl() {
		return deplacement;
	}

	public void cloneDepl(List<Move> l) {
		for(Move mv : l){
			deplacement.add(mv);
		}
	}

	public static void spawnAllies(List<Allies> pop) {
		for(Allies a : pop) {
			a.setPosX(Parameters.getSpawnX());
			a.setPosY(Parameters.getSpawnY());
			a.setState(true);
		}
	}
	
	public Color getColor() {
		return color;
	}
	 
	public int getPosX() {
		return POS_X;
	}

	public int getPosY() {
		return POS_Y;
	}

	public void setPosX(int x) {
		POS_X = x;
	}

	public void setPosY(int y) {
		POS_Y = y;
	}

	public void setPositionX(String s, int i) {
		if(s == "R")
			setPosX(getPosX() + i);
		else if(s == "L")
			setPosX(getPosX() - i);
	}

	public void setPositionY(String s, int i) {
		if(s == "B")
			setPosY(getPosY() + i);
		else if(s == "T")
			setPosY(getPosY() - i);
	}

	public boolean getState() {
		return ALIVE;
	}

	public void setState(boolean b) {
		ALIVE = b;
	}

	public void evaluate() {
		setFitness();
	}

}
