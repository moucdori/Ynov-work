package com.perso.genetique.individual;

import java.awt.Color;

public class Bomb extends Individual {
	
	private static int colorCount = 0;
	private static Color color =Color.WHITE;
	
	
	public int getColorCount() {
		return colorCount;
	}
	
	public void setColorCountIncr(int i) {
		colorCount+=i;
	}
	
	public void resetColorCount() {
		colorCount = 0;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getBombSize() {
		return BOMB_SIZE;
	}
	
	public void setBombSize(int i) {
		BOMB_SIZE = i;
	}
	
	/*public Color getColor() {
		return color;
	}*/

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
	
}
