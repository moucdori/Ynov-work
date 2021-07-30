package com.perso.genetique.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.perso.genetique.launcher.Parameters;

public class PanelArene extends JPanel {

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// Réinitialisation du background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Frame.SCREEN_WIDTH/2, Frame.SCREEN_HEIGHT);

		// Aire du jeu
		g.setColor(Color.WHITE);

		g2d.setStroke(new BasicStroke(3));
		g.drawLine(Parameters.getLeftBorder(), Parameters.getTopBorder(), Parameters.getRightBorder(), Parameters.getTopBorder());
		g.drawLine(Parameters.getLeftBorder(), Parameters.getArrivee(), Parameters.getRightBorder(), Parameters.getArrivee());

		g.drawLine(Parameters.getLeftBorder(), Parameters.getTopBorder(), Parameters.getLeftBorder(), Parameters.getArrivee());
		g.drawLine(Parameters.getRightBorder(), Parameters.getTopBorder(), Parameters.getRightBorder(), Parameters.getArrivee());
	}
}
