package com.perso.genetique.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.perso.genetique.individual.Individual;
import com.perso.genetique.launcher.Parameters;

public class PanelRight extends JPanel {

	public static JButton startButton = new JButton("START");
	public static JFormattedTextField nA = new JFormattedTextField(NumberFormat.getIntegerInstance());
	public static JFormattedTextField nG = new JFormattedTextField(NumberFormat.getIntegerInstance());
	public static JFormattedTextField nB = new JFormattedTextField(NumberFormat.getIntegerInstance());
	
	public void paintComponent(Graphics g) {

		this.setLayout(null);
		//this.setBackground(Color.BLACK);
		// Réinitialisation du background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 960, 1040);
		
		Font police = new Font("Arial", Font.BOLD, 18);

		JLabel labA = new JLabel("Nombre d'alliés");
		labA.setFont(police);
		labA.setForeground(Color.WHITE);
		labA.setBounds(50, 320, 200, 20);
		nA.setBounds(50, 340, 150, 20);
		JLabel labG = new JLabel("Nombre de générations");
		labG.setFont(police);
		labG.setForeground(Color.WHITE);
		labG.setBounds(50, 420, 250, 20);		
		nG.setBounds(50, 440, 150, 20);
		JLabel labB = new JLabel("Nombre de Bombes");
		labB.setFont(police);
		labB.setForeground(Color.WHITE);
		labB.setBounds(350, 320, 200, 20);
		nB.setBounds(350, 340, 150, 20);
		Dimension dStart = startButton.getPreferredSize();
		startButton.setBounds(50, 540, dStart.width + 100, dStart.height + 50);
		
		this.add(nA);
		this.add(labA);
		this.add(nG);
		this.add(labG);
		this.add(nB);
		this.add(labB);
		this.add(startButton);

		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3));
		
		// Infos et Statistiques
		g.setColor(Color.WHITE);
		g.drawRect(30, 30, 300, 200);
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.ROMAN_BASELINE, 30));
		g.drawString("Génération  " + Parameters.getGen(), 110, 90);
		if(Parameters.READ_NB_ALLIES)
			g.drawString("Individus vivant : 0", 60, 160);
		else
			g.drawString("Individus vivant : " + Parameters.getAlliesAlive(), 60, 160);
		
		// MVP
		if(!(Individual.MVP == null)) {
			
			g.drawImage(Individual.MVP.getSkin(), 400, 60, 200, 200, null);
			g.drawRect(400, 60, 200, 200);
			g.drawString("MVP", 470, 40);
			
			if(Parameters.FINISH)
				g.drawString("FINISH !", 450, 300);
		}
	}
}
