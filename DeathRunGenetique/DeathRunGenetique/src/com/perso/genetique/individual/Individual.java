package com.perso.genetique.individual;

import java.util.ArrayList;
import java.util.List;


import com.perso.genetique.launcher.Parameters;

public class Individual {

	protected boolean ALIVE = true;
	protected int ALLIES_SIZE = 20;
	protected int BOMB_SIZE;
	protected int POS_X = Parameters.getSpawnX();
	protected int POS_Y = Parameters.getSpawnY();
	//protected Color color = Parameters.randomColor();
	//protected Color color = Color.GREEN;
	
	public static Allies MVP = null;


	public Allies newAllies(Allies father, Allies mother) {

		List<Move> movement = new ArrayList<Move>();
		int r = Parameters.RANDOM.nextInt(100);

		if(r<50) { // Father premier
			int cut = Parameters.RANDOM.nextInt(father.getDepl().size());
			for(int i=0;i<=cut;i++) {
				movement.add(father.getDepl().get(i));
			}
			for(int i=cut;i<mother.getDepl().size();i++) {
				movement.add(mother.getDepl().get(i));
			}
		}
		else { // Mother première
			int cut = Parameters.RANDOM.nextInt(mother.getDepl().size());
			for(int i=0;i<=cut;i++) {
				movement.add(mother.getDepl().get(i));
			}
			for(int i=cut;i<father.getDepl().size();i++) {
				movement.add(father.getDepl().get(i));
			}
		}

		Allies all = new Allies(movement);
		

		all.mutate();

		return all;
	}

	public Allies newAllies(Allies father) {

		List<Move> movement = new ArrayList<Move>();

		//int cut = Parameters.RANDOM.nextInt(father.getDepl().size());
		for(int i=0;i<=father.getDepl().size() - 1;i++) {
			movement.add(father.getDepl().get(i));
		}

		Allies all = new Allies(movement);

		all.mutate();

		return all;
	}

	public int getBombSize() {
		return BOMB_SIZE;
	}

	public int getAlliesSize() {
		return ALLIES_SIZE;
	}

	public boolean getState() {
		return ALIVE;
	}

	public int getPosX() {
		return POS_X;
	}

	public int getPosY() {
		return POS_Y;
	}
/*
	public Color getColor() {
		return color;
	}

	public void setColor(Color c) {
		color = c;
	}*/

}
