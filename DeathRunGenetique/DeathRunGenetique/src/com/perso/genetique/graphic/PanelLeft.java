package com.perso.genetique.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.perso.genetique.individual.Allies;
import com.perso.genetique.individual.Bomb;
import com.perso.genetique.launcher.EvolutionnaryProcess;
import com.perso.genetique.launcher.Parameters;

public class PanelLeft extends JPanel {

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(5));

		drawPanel(g);
		drawBomb(g);
		
		// Population
		for(Allies a : EvolutionnaryProcess.getPopulation()) {
			
			g.drawImage(a.getSkin(), a.getPosX(), a.getPosY(), a.getAlliesSize(), a.getAlliesSize(), null);
			//g.setColor(a.getColor());
			//g.fillRect(a.getPosX(), a.getPosY(), a.getAlliesSize(), a.getAlliesSize());
		}
	}


	private void drawBomb(Graphics g) {

		for(Bomb b : EvolutionnaryProcess.getBomb()) {

			// Si la bombe est activée on la garde rouge pour voir l'animation
			/*if(b.getColor() == Color.RED) {
				b.setColorCountIncr(1);
			}*/
			
			BufferedImage img;
			try {
				img = ImageIO.read(new File("img\\pokeball.png"));
				g.drawImage(img, b.getPosX(), b.getPosY(), b.getBombSize(), b.getBombSize(), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//g.setColor(b.getColor());
			//g.fillRect(b.getPosX(), b.getPosY(), b.getBombSize(), b.getBombSize());
/*
			if(b.getColorCount() == 20) {
				b.setColor(Color.WHITE);
				b.resetColorCount();
			}*/
		}
	}

	private void drawPanel(Graphics g) {
		// Réinitialisation du background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Frame.SCREEN_WIDTH/2, Frame.SCREEN_HEIGHT);

		BufferedImage img;
		try {
			img = ImageIO.read(new File("img\\pelouse.png"));
			g.drawImage(img, Parameters.getLeftBorder(), Parameters.getTopBorder(), Parameters.getRightBorder() - Parameters.getLeftBorder(), Parameters.getArrivee() - Parameters.getTopBorder(), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Aire du jeu
		g.setColor(Color.WHITE);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3));
		g.drawLine(Parameters.getLeftBorder(), Parameters.getTopBorder(), Parameters.getRightBorder(), Parameters.getTopBorder());
		g.drawLine(Parameters.getLeftBorder(), Parameters.getArrivee(), Parameters.getRightBorder(), Parameters.getArrivee());

		g.drawLine(Parameters.getLeftBorder(), Parameters.getTopBorder(), Parameters.getLeftBorder(), Parameters.getArrivee());
		g.drawLine(Parameters.getRightBorder(), Parameters.getTopBorder(), Parameters.getRightBorder(), Parameters.getArrivee());
	
	}

}
