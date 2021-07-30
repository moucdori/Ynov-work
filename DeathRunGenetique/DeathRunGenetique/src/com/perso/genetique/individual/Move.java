package com.perso.genetique.individual;

import com.perso.genetique.launcher.Parameters;

public class Move {
	
	private String direction;
	private int depl;

	public String setRandDir() {
		String dir;
		
		int r = Parameters.RANDOM.nextInt(1000);
		if(r<240) 	
			dir = "T";
		else if(r<490) 			
			dir = "L";
		else if(r<740) 
			dir = "R";
		else 
			dir = "B";
		
		return dir;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String s) {
		direction = s;
	}
	
	public int getDeplacement() {
		return depl;
	}
	
	public void setDeplacement(int i) {
		depl = i;
	}
	
}
