package com.perso.genetique.launcher;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import com.perso.genetique.individual.Allies;
import com.perso.genetique.individual.Bomb;

public class EvolutionnaryProcess {
	private static List<Allies> population = new ArrayList<Allies>();
	private static List<Bomb> bomb = new ArrayList<Bomb>();

	public EvolutionnaryProcess() {

		if(population.size() != 0) {
			for(int i=population.size()-1;i>=0;i--) {
				population.remove(i);
			}
		}
		if(bomb.size() != 0) {
			for(int i=bomb.size()-1;i>=0;i--) {
				bomb.remove(i);
			}
		}
		
		// Création de la génération 0
		for(int i=0;i<Parameters.getNbAllies();i++) {
			Allies a = new Allies(); 
			addAllies(a);
		}
		Parameters.POPULATION_CREATED = true;
		
		// Création des bombes
		for(int i=0; i<Parameters.getNbBomb();i++) {
			Bomb b = new Bomb();
			//b.setColor(Color.WHITE);
			b.setBombSize(new Random().nextInt(30) + 20);
			int px, py;
			// On vérifie que les bombes ne sont pas trop près du spawn
			do {
				px = new Random().nextInt(Parameters.getRightBorder() - Parameters.getLeftBorder() - b.getBombSize()) + Parameters.getLeftBorder();
				py = new Random().nextInt(Parameters.getRightBorder() - Parameters.getLeftBorder() - b.getBombSize()) + Parameters.getLeftBorder();
			} while(px > (Parameters.getSpawnX() - b.getBombSize() - 30) && px < (Parameters.getSpawnX() + 30) && py > (Parameters.getSpawnY() - b.getBombSize() - 30) && py < (Parameters.getSpawnY() + 30));

			b.setPosX(px);
			b.setPosY(py);
			bomb.add(b);
		}
	}

	private synchronized void addAllies(Allies a) {
		population.add(a);		
	}

	public static Allies selection() {
		//Code du prof
		/*int totalRanks = Parameters.getNbAllies()*(Parameters.getNbAllies() + 1)/2;
		int rand = Parameters.RANDOM.nextInt(totalRanks);
		int indIndex = 0;
		int nbParts = Parameters.getNbAllies();
		int totalParts = 0;

		while(totalParts<rand) {
			indIndex++;
			totalParts+=nbParts;
			nbParts--;
		}

		population.sort(Comparator.comparing(Allies::getFitness));
		return population.get(indIndex);*/

		//population.sort(Comparator.comparing(Allies::getFitness));
		//return population.get(Parameters.RANDOM.nextInt(9));	


		int ranInt = Math.round(Parameters.RANDOM.nextInt(10000)/10);
		int indIndex = 0;

		if(ranInt < 300) 
			indIndex = 0;
		else if(ranInt < 500) 
			indIndex = 1;
		else if(ranInt < 650) 
			indIndex = 2;
		else if(ranInt < 700) 
			indIndex = 3;
		else if(ranInt < 825) 
			indIndex = 4;
		else if(ranInt < 891) 
			indIndex = 5;
		else if(ranInt < 941) 
			indIndex = 6;
		else if(ranInt < 974) 
			indIndex = 7;
		else if(ranInt < 999) 
			indIndex = 8;
		else if(ranInt < 1000) 
			indIndex = 9;
			
		return population.get(indIndex); 

	}

	public static void survival(List<Allies> generation) {
		for(int i=population.size() - 1;i>=0;i--) {
			population.remove(i);
		}
		for(Allies a : generation){
			population.add(a);
		}
	}

	public static List<Bomb> getBomb() {
		return bomb;
	}

	public static Bomb getBomb(int i) {
		return bomb.get(i);
	}

	public synchronized static List<Allies> getPopulation(){
		return population;
	}

	public static Allies getPopulation(int i){
		return population.get(i);
	}
}
