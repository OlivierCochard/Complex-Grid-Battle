package entitesJeu;

import outilsJeu.Observable;
import outilsJeu.Observeur;

import java.util.ArrayList;
import java.util.List;

public abstract class Entite implements Observable {
    private List<Observeur> observeurs = new ArrayList<>();

    @Override
    public void ajouterObserveur(Observeur observeur) {
        observeurs.add(observeur);
    }
    @Override
    public void retirerObserveur(Observeur observeur) {
        observeurs.remove(observeur);
    }
    @Override
    public void notifierObserveurs(List<String> evenements) {
        for (Observeur observeur : observeurs) {
            observeur.miseAJour(evenements, this);
        }
    }

    public abstract void destruction();
}
