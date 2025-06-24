package entitesJeu.objets;

import entitesJeu.obstacles.Combattant;
import entitesJeu.objets.Objet;

import java.util.ArrayList;
import java.util.List;

//objet invisible pour les combattants non-poseurs
public class Mine extends Objet {
	private int degats;
	private Combattant poseur;

	public Mine(int degats, Combattant poseur){
		this.degats = degats;
		this.poseur = poseur;
	}

	public int getDegats(){ return degats; }
	public Combattant getPoseur() { return poseur; }

    public void explosion(){
    	List<String> tmp = new ArrayList<>();
    	tmp.add("explosion");
        notifierObserveurs(tmp);
    }
    public void destruction(){
    	List<String> tmp = new ArrayList<>();
    	tmp.add("destruction");
    	tmp.add("objet");
        notifierObserveurs(tmp);
    }

    @Override
	public String toString(){
		return "Mine: " + degats + " dégâts";
	}
}
