package com.perso.genetique.graphic;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;

import com.perso.genetique.individual.Allies;
import com.perso.genetique.individual.Bomb;
import com.perso.genetique.individual.Individual;
import com.perso.genetique.launcher.EvolutionnaryProcess;
import com.perso.genetique.launcher.Parameters;

public class Frame extends JFrame implements ActionListener{

	public static int SCREEN_WIDTH = 1366;
	public static int SCREEN_HEIGHT = 728;
	
	private PanelLeft panLeft = null;
	private PanelArene panArene = new PanelArene();
	private PanelRight panRight = new PanelRight();
	
	public void frame() {

		this.setTitle("DeathRun Génétique");
		this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		this.setBackground(Color.BLACK);
		this.setLocation(200, 200);
		//this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(1, 2));
		
		PanelRight.startButton.addActionListener(this);
		
		this.getContentPane().add(panArene);
		this.getContentPane().add(panRight);
		this.setVisible(true);
		
		while(Parameters.START == false) {
			System.out.println("START : false");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Parameters.init();
		EvolutionnaryProcess process = new EvolutionnaryProcess();

		System.out.println("POPULATION_CREATED : " + Parameters.POPULATION_CREATED);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(Parameters.POPULATION_CREATED == false) {
			System.out.println("POPULATION_CREATED : false");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.getContentPane().remove(panArene);
		this.getContentPane().remove(panRight);
		
		this.getContentPane().add(panLeft = new PanelLeft());
		this.getContentPane().add(panRight);
		this.setVisible(true);
		run();
	}


	public void run() {

		Parameters.crossoverRate = 0.6;
		Parameters.mutationAddRate = 0.2;
		Parameters.mutationDeleteRate = 0.1;
		Parameters.mutationsRate = 0.1;

		int count = 0;
		
		aa:
			// Tant que par arrivé en bas ou encore des générations à reproduire on continue
			while(Parameters.FINISH == false || Parameters.getGen() <= Parameters.getMaxGen()) {

				// Initialisation ou réinitialisation
				Parameters.setAlliesAlive(Parameters.getNbAllies());
				if(Parameters.getGen()>0) {
					Allies.spawnAllies(EvolutionnaryProcess.getPopulation());
					for(Allies a : EvolutionnaryProcess.getPopulation()) {
						a.resetMoveIndex();
						if(a.getDepl().size() > 50) {
							for(int i=0;i<50;i++) {
								a.getDepl().remove(a.getDepl().size()-1);
							}
						}
					}
				}

				// Tant que des alliés sont encore en vie, ils bougent
				while(Parameters.getAlliesAlive()>0) {
					
					for(Allies a : EvolutionnaryProcess.getPopulation()) {

						// On vérifie que l'allié ne touche pas les bombes
						for(Bomb b : EvolutionnaryProcess.getBomb()) {
							Rectangle rectA = new Rectangle(a.getPosX(), a.getPosY(), a.getAlliesSize(), a.getAlliesSize());
							Rectangle rectB = new Rectangle(b.getPosX(), b.getPosY(), b.getBombSize(), b.getBombSize());
							if(rectA.intersects(rectB) && a.getState() != false) {
								Parameters.setAlliesAlive(Parameters.getAlliesAlive() - 1);
								a.setState(false);

								// Animation en rouge pour voir quelle bombe s'est activée
								//b.setColor(Color.RED);
								continue;
							}
						}

						if(a.getHeritage() == true && a.getState() == true && a.getDepl().size() > a.getMoveIndex()) {
							a.deplace();
						}
						else if(a.getState() == true){

							int x = a.getPosX();
							int y = a.getPosY();
							int depl= 0;
							String dir;

							// Déplacement aléatoire
							int r = Parameters.RANDOM.nextInt(1000);
							if(r<240) {
								depl = 3;
								//depl = Parameters.RANDOM.nextInt(4);
								y -= depl; // Déplacement vers le haut
								dir = "T";
							}
							else if(r<490) {
								depl = 3;
								//depl = Parameters.RANDOM.nextInt(4);
								x -= depl; // Déplacement vers la gauche
								dir = "L";
							}
							else if(r<740) {
								depl = 3;
								//depl = Parameters.RANDOM.nextInt(4);
								x += depl; // Déplacement vers la droite
								dir = "R";
							}
							else {
								depl = 3;
								//depl = Parameters.RANDOM.nextInt(4);
								y += depl; // Déplacement vers le bas
								dir = "B";
							}

							a.addDeplacement(dir, depl);

							// Mise à jour des nouvelles positions
							a.setPosX(x);
							a.setPosY(y);
						}

						// On vérifie que l'allié ne touche pas les bords du cadre
						if((a.getPosX()<60 || a.getPosX()>860 - 15 || a.getPosY()<20) && a.getState() == true) {
							Parameters.setAlliesAlive(Parameters.getAlliesAlive() - 1);
							a.setState(false);
							continue;
						} // On vérifie que l'allié est vivant
						else if(a.getState() == false) {
							continue;
						} // On vérifie si un allié est arrivé
						else if(a.getPosY() + 15 > Parameters.getArrivee()) {
							Parameters.FINISH = true;
							Individual.MVP = a;
							panLeft.repaint();
							panRight.repaint();
							break aa;
						} 
					}
					
					panLeft.repaint();
					panRight.repaint();

					try {
						if(count%2 == 0)
							Thread.sleep(3);
						
						if(count == 30000)
							count = 0;
						
						count++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}


				// On évalue toute la génération
				for(int i=0;i<EvolutionnaryProcess.getPopulation().size();i++) {
					Allies a = EvolutionnaryProcess.getPopulation().get(i);
					a.evaluate();
					//System.out.println("Individu " + i + " : " + a.getFitness() + ", liste de déplacement : " + a.getDepl().size());
				}

				// On récupère le meilleur de la population
				EvolutionnaryProcess.getPopulation().sort(Comparator.comparing(Allies::getFitness));
				//System.out.println("Le meilleur de cette génération a une fitness de " + EvolutionnaryProcess.getPopulation(0).getFitness());

				Individual.MVP = EvolutionnaryProcess.getPopulation(0);
				
				Allies bestA = new Allies();
				bestA.cloneDepl(EvolutionnaryProcess.getPopulation(0).getDepl());
				bestA.setPosX(EvolutionnaryProcess.getPopulation(0).getPosX());
				bestA.setPosY(EvolutionnaryProcess.getPopulation(0).getPosY());
				bestA.setFitness();
				//System.out.println("BEST : " + bestA.getFitness() + ", la taille de sa liste de mouvement est " + bestA.getDepl().size());
				//bestA.setColor(Color.RED);
				bestA.setHeritage(true);
				bestA.setBest(true);
				
				List<Allies> la = new ArrayList<Allies>();
				la.add(bestA);
				

				for(int i=0;i<Parameters.getNbAllies()-1;i++) {
					// 1 ou 2 parents ?
					if(Parameters.RANDOM.nextDouble()<Parameters.crossoverRate) {

						Allies father = EvolutionnaryProcess.selection();
						Allies mother = EvolutionnaryProcess.selection();
						Allies newAllie = new Allies().newAllies(father, mother);
						//newAllie.setColor(Color.GREEN);

						la.add(newAllie);
					}
					else {
						Allies father = EvolutionnaryProcess.selection();
						Allies mother = EvolutionnaryProcess.selection();
						Allies newAllie = new Allies().newAllies(father, mother);
						//newAllie.setColor(Color.GREEN);

						la.add(newAllie);	
					}
				}			



				for(Allies al : la) {
					al.setHeritage(true);
					/*if(al.getBest() == true)
						al.setColor(Color.RED);
					else
						al.setColor(Color.GREEN);*/
				}

				// On transmet les caractéristiques à la nouvelles génération
				EvolutionnaryProcess.survival(la);
				Parameters.setGen(Parameters.getGen() + 1);
			}

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == PanelRight.startButton) {
			Parameters.START = true;
			EvolutionnaryProcess process = new EvolutionnaryProcess();
			PanelRight.startButton.setEnabled(false);
		}
		
	}
}
