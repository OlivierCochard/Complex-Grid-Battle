package entitesJeu.objets;

import entitesJeu.objets.Objet;

import java.util.ArrayList;
import java.util.List;

public class PastilleEnergetique extends Objet {
	private int tourRestantMax;
	private int tourRestant;
	private int energie;

	public PastilleEnergetique(int energie, int tourRestantMax, int tourRestant){
		this.tourRestantMax = tourRestantMax;
		this.tourRestant = tourRestant;
		this.energie = energie;
	}

	public int getTourRestant(){ return tourRestant; }
	public int getEnergie(){ return energie; }
	public boolean estPrenable(){ return tourRestant == 0; }

	public void reductionTourRestant(){
		if (tourRestant == 0){
			return;
		}
		tourRestant--;
	}
	public void reinitialiser(){
		tourRestant = tourRestantMax + 1;
	}

	@Override
    public void explosion(){
    	List<String> tmp = new ArrayList<>();
    	tmp.add("explosion");
        notifierObserveurs(tmp);
    }
    @Override
    public void destruction(){
    	System.out.println("PastilleEnergetique ne peut pas être detruit.");
    }

    @Override
	public String toString(){
		return "PastilleEnergetique: " + energie + " énergie | " + tourRestant + " tour(s) restant";
	}
}
