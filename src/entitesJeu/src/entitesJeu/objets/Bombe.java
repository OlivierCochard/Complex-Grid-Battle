package entitesJeu.objets;

import entitesJeu.obstacles.Combattant;
import entitesJeu.objets.Mine;

//objet visible pour tous les combattants
public class Bombe extends Mine {
	private int tourRestant;

	public Bombe(int degats, Combattant poseur, int tourRestant){
		super(degats, poseur);
		this.tourRestant = tourRestant;
	}

	public int getTourRestant(){ return tourRestant; }

	public void reductionTourRestant(){
		tourRestant--;
		if (tourRestant == 0){
			explosion();
		}
	}
	
	@Override
	public String toString(){
		return "Bombe: " + getDegats() + " dégâts | " + tourRestant + " tour(s) restant(s)";
	}
}
